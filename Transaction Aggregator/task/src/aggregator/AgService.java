package aggregator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class AgService {

    private final RestTemplate restTemplate;

    @Autowired
    public AgService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Async
    public CompletableFuture<ResponseEntity<List<Account>>> queryTransactions(URI uri) {
        CompletableFuture<ResponseEntity<List<Account>>> response = null;
        HttpStatusCode statusCode = null;
        int counter = 6;
            do {
                try {
                    response = CompletableFuture.supplyAsync(() -> restTemplate.exchange(
                            uri,
                            HttpMethod.GET,
                            null,
                            new ParameterizedTypeReference<List<Account>>() {
                            })).handle((r, e) -> {
                                if (e == null) {
                                    return r;
                                } else {
                                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                                }
                    });
                } catch (HttpServerErrorException e) {
                    e.printStackTrace();
                }
                try {
                     statusCode = response.get().getStatusCode();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
                 counter--;
            } while (!statusCode.is2xxSuccessful() && counter > 0);
        return response;
    }
}
