package aggregator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AgController {

    private final AgCacheService agCacheService;

    @Autowired
    public AgController(AgCacheService agCacheService) {
        this.agCacheService = agCacheService;
    }

    @GetMapping("/aggregate")
    public List<Account> query(@RequestParam(name = "account") String account) {
        List<Account> response = agCacheService.callAsyncMethod(account);
        response.sort((a1, a2) -> a2.getTimestamp().compareTo(a1.getTimestamp()));
        return response;
    }
}
