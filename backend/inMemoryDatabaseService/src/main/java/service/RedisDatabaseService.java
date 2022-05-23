package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class RedisDatabaseService implements IRedisDatabaseService{
    @Autowired
    RedisTemplate<String,String> redisTemplate;

    @Resource(name="redisTemplate")
    private SetOperations<String,String> setOperations;

    /*
    private RedisAtomicInteger atomicSendMessages = new RedisAtomicInteger("sendMessages", redisTemplate.getConnectionFactory(),0);
    private RedisAtomicInteger atomicRegistItems = new RedisAtomicInteger("registItems", redisTemplate.getConnectionFactory(),0);
    private RedisAtomicInteger atomicCountUser = new RedisAtomicInteger("countUser", redisTemplate.getConnectionFactory(),0);

    public void incrementValue(AtomicIntegerModel key){
        switch(key){
            case SENDMESSAGES -> atomicSendMessages.incrementAndGet();
            case REGISTEREDITEMS -> atomicRegistItems.incrementAndGet();
            case COUNTUSERS -> atomicCountUser.incrementAndGet();
        }
    }

*/
    public String getSingleSetValue(String key){
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
}
