package service.advertisementservice.service;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.RedisDatabaseService;

import java.util.HashMap;

//Service returns nothing in string if an error occured
@Service
public class AdvertisementServiceService {

    @Autowired
    RedisDatabaseService redisDatabaseService;

    public String fetchData(){
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("Diagramm1",redisDatabaseService.getSingleSetValue("sendMessages"));
        hashMap.put("Diagramm2",redisDatabaseService.getSingleSetValue("registItems"));
        hashMap.put("Diagramm3",redisDatabaseService.getSingleSetValue("countUser"));

        JSONObject jsonObject = new JSONObject(hashMap);

        return jsonObject.toJSONString();
    }

}
