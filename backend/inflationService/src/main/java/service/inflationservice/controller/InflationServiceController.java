package service.inflationservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import service.inflationservice.service.InflationServiceService;

@RestController
public class InflationServiceController {
    @Autowired
    InflationServiceService inflationServiceService;

    /**
     * @return json with inflationdata for the latest published month
     */
    @GetMapping("/inflationrate")
    public String getLastInflationRate(){
        return inflationServiceService.getLatestInflationData();
    }
}
