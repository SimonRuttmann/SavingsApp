package service.advertisementservice.service;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.support.atomic.RedisAtomicInteger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

//Service returns nothing in string if an error occured
@Service
public class AdvertisementServiceService {
    @Autowired
    RedisTemplate<String,String> template;

    @Resource(name="redisTemplate")
    private SetOperations<String,String> setOperations;

    public String fetchData(){
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("Diagramm1",getSingleSetValue("sendMessages"));
        hashMap.put("Diagramm2",getSingleSetValue("registItems"));
        hashMap.put("Diagramm3",getSingleSetValue("countUser"));

        JSONObject jsonObject = new JSONObject(hashMap);

        return jsonObject.toJSONString();
    }

    private String getSingleSetValue(String key){
        try{
            String string = setOperations.members(key).toString();

            return removeBracket(string);
        }catch (NullPointerException e){
            return "";
        }
    }

    private String removeBracket(String string){
        string = string.replace("[","");
        string = string.replace("]","");

        return string;
    }


    RedisAtomicInteger atomicSendMessages = new RedisAtomicInteger("sendMessages", template.getConnectionFactory(),0);
    RedisAtomicInteger atomicRegistItems = new RedisAtomicInteger("registItems", template.getConnectionFactory(),0);
    RedisAtomicInteger atomicCountUser = new RedisAtomicInteger("countUser", template.getConnectionFactory(),0);

    private void incrementValue(AtomicIntegerModel key){
        switch(key){
            case AtomicIntegerModel. -> atomicSendMessages.incrementAndGet();
            case "registItems" -> atomicRegistItems.incrementAndGet();
            case "countUser" -> atomicCountUser.incrementAndGet();
        }
    }
}
