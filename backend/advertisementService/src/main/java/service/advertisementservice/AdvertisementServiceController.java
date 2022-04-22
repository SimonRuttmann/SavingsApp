package service.advertisementservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class AdvertisementServiceController {
    @Autowired AdvertisementServiceService advertisementServiceService;

    //Returns index.html
    @GetMapping("/*")
    public ModelAndView index(){
       ModelAndView modelAndView= new ModelAndView();
       modelAndView.setViewName("index.html");
       return modelAndView;
    }

    //Returns data for three diagramms
    @GetMapping("/global")
    public String diagrammData(){
        return advertisementServiceService.fetchData();
    }
}
