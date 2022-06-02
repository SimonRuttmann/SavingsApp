package service;

import model.AtomicIntegerModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicInteger;
import org.springframework.stereotype.Service;

@Service
public class RedisDatabaseService implements IRedisDatabaseService{

    private final RedisAtomicInteger atomicSendMessages;
    private final RedisAtomicInteger atomicRegistItems;
    private final RedisAtomicInteger atomicCountUser;

    @Autowired
    public RedisDatabaseService(RedisTemplate redisTemplate) {
        RedisConnectionFactory connectionFactory = redisTemplate.getConnectionFactory();

        atomicSendMessages = new RedisAtomicInteger("sendMessages",connectionFactory ,0);
        atomicRegistItems = new RedisAtomicInteger("registItems", connectionFactory,0);
        atomicCountUser = new RedisAtomicInteger("countUser", connectionFactory ,0);
    }

    public void incrementValue(AtomicIntegerModel key){
        switch(key){
            case SENDMESSAGES -> atomicSendMessages.incrementAndGet();
            case REGISTEREDITEMS -> atomicRegistItems.incrementAndGet();
            case COUNTUSERS -> atomicCountUser.incrementAndGet();
        }
    }

    public Integer getSingleSetValue(AtomicIntegerModel key){
        return switch (key) {
            case SENDMESSAGES -> atomicSendMessages.get();
            case REGISTEREDITEMS -> atomicRegistItems.get();
            case COUNTUSERS -> atomicCountUser.get();
        };
    }

    private String removeBracket(String string){
        string = string.replace("[","");
        string = string.replace("]","");

        return string;
    }
}
