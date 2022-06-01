package service.inflationservice.controller;

import dtoAndValidation.dto.inflation.InflationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import service.inflationservice.service.InflationServiceService;

@RestController
public class InflationServiceController {

    final InflationServiceService inflationServiceService;

    @Autowired
    public InflationServiceController(InflationServiceService inflationServiceService) {
        this.inflationServiceService = inflationServiceService;
    }

    /**
     * @return json with inflationdata for the latest published month
     */
    @GetMapping("/inflationrate")
    public ResponseEntity<InflationDto> getLastInflationRate() {
        return inflationServiceService.getLatestInflationData();
    }
}
