package service.inflationservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InflationService {
    @Autowired DataJobs dataJobs;

    @GetMapping("/inflationrate")
    public String getInflationRate(){
        return dataJobs.getData();
    }
}
