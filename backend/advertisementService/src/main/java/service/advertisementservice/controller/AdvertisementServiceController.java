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
    @GetMapping("/")
    public ModelAndView index(){
        try {
            ModelAndView modelAndView= new ModelAndView();
            modelAndView.setViewName("index");
            return modelAndView;
        } catch (Exception e) {
            System.out.println("execption"+ e.getMessage());
        }
        return null;
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
