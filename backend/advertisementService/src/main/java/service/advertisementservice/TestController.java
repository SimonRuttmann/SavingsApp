package service.advertisementservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/")
    public String index(){
            return "Moin moin meine Freunde der Nacht";
    }

}
