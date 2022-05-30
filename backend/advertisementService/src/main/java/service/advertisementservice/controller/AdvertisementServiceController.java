package service.advertisementservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import service.advertisementservice.model.AdvertisementDto;
import service.advertisementservice.service.AdvertisementServiceService;

@RestController
public class AdvertisementServiceController {
    @Autowired
    AdvertisementServiceService advertisementServiceService;

    /**
     * @return returns the guest homepage index.html
     */
    @GetMapping("/*")
    public ModelAndView index(){
       ModelAndView modelAndView= new ModelAndView();
       modelAndView.setViewName("index.html");
       return modelAndView;
    }

    /**
     * @return a json with three datapoints
     * {
     * "Diagramm1": send Messages,
     * "Diagramm2": registered Items,
     * "Diagramm3": amount of users
     * }
     */
    @GetMapping("/global")
    public AdvertisementDto diagrammData(){
        return advertisementServiceService.fetchData();
    }
}
