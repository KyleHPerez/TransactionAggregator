package aggregator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class AgCacheService {

    private final AgService agService;
    private final CacheManager cacheManager;

    @Autowired
    public AgCacheService(AgService agService, CacheManager cacheManager) {
        this.agService = agService;
        this.cacheManager = cacheManager;
    }

    @Cacheable(value = "transactions", key = "#account")
    public List<Account> callAsyncMethod(String account) {
        String[] ports = {"8888", "8889"};
        URI[] uris = {createUri(ports[0], account), createUri(ports[1], account)};
        List<Account> accounts = new ArrayList<>();
        List<CompletableFuture<ResponseEntity<List<Account>>>> completableFutures = new ArrayList<>();
        for (URI uri : uris) {
            completableFutures.add(agService.queryTransactions(uri));
        }
        for (CompletableFuture<ResponseEntity<List<Account>>> completableFuture : completableFutures) {
            try {
                accounts.addAll(completableFuture.get().getBody());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        return accounts;
    }

    URI createUri(String port, String account) {
        UriComponentsBuilder builder = UriComponentsBuilder.newInstance();
        builder.scheme("http");
        builder.host("localhost");
        builder.port(port);
        builder.path("/transactions");
        builder.queryParam("account", account);
        String finalUrl = builder.toUriString();
        URI uri = null;
        try {
            uri = new URI(finalUrl);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return uri;
    }
}
