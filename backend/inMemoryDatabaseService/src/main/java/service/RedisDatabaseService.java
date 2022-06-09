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

        atomicSendMessages = new RedisAtomicInteger("sendMessages",connectionFactory);
        atomicRegistItems = new RedisAtomicInteger("registItems", connectionFactory);
        atomicCountUser = new RedisAtomicInteger("countUser", connectionFactory);
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
}
