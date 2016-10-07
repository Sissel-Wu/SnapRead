package businesslogic;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by nians on 2016/10/7.
 */

@RestController
@RequestMapping("/api")
public class webService {

    @RequestMapping("/test")
    public String test(@RequestParam("param") String param) {
        return param;
    }
}
