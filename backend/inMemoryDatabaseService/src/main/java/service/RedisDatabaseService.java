package service;

import model.AtomicIntegerModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.support.atomic.RedisAtomicInteger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class RedisDatabaseService implements IRedisDatabaseService{
    @Autowired
    RedisTemplate<String,String> redisTemplate;

    @Resource(name="redisTemplate")
    private SetOperations<String,String> setOperations;

    private RedisAtomicInteger atomicSendMessages;
    private RedisAtomicInteger atomicRegistItems;
    private RedisAtomicInteger atomicCountUser;

    public void incrementValue(AtomicIntegerModel key){
        if(atomicCountUser == null || atomicSendMessages == null || atomicRegistItems == null){
            RedisConnectionFactory connectionFactory = redisTemplate.getConnectionFactory();

            atomicSendMessages = new RedisAtomicInteger("sendMessages",connectionFactory ,0);
            atomicRegistItems = new RedisAtomicInteger("registItems", connectionFactory,0);
            atomicCountUser = new RedisAtomicInteger("countUser", connectionFactory ,0);
        }
        switch(key){
            case SENDMESSAGES -> atomicSendMessages.incrementAndGet();
            case REGISTEREDITEMS -> atomicRegistItems.incrementAndGet();
            case COUNTUSERS -> atomicCountUser.incrementAndGet();
        }
    }

    public String getSingleSetValue(String key){
        try{
            String string = setOperations.members(key).toString();

            return removeBracket(string);
        }catch (NullPointerException e){
            return "null";
        }
    }

    private String removeBracket(String string){
        string = string.replace("[","");
        string = string.replace("]","");

        return string;
    }
}
