package service.inflationservice;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InflationServiceController {
    @Autowired DataJobs dataJobs;

    JSONParser parser = new JSONParser();
    @GetMapping("/inflationrate")
    public String getLastInflationRate(){
        Object obj = null;
        try {
            obj = parser.parse(dataJobs.getData());
            JSONArray json = (JSONArray)obj;
            return json.get(0).toString();

        }catch (ParseException e){
            return "Json could not be parsed";
        }catch (Exception e){
            return "Unknown error";
        }

    }
}
