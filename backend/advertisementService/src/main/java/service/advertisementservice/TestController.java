package service.advertisementservice;

import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/advertisement")
public class TestController {
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
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Diagramm1",getData1());
        jsonObject.put("Diagramm2",getData2());
        jsonObject.put("Diagramm3",getData3());

        return jsonObject.toJSONString();
    }

    private String getData1(){
        String value = null;
        return value;
    }
    private String getData2(){
        String value = null;
        return value;
    }
    private String getData3(){
        String value = null;
        return value;
    }
}
