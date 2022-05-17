package inMemoryDatabaseService.service;

import inMemoryDatabaseService.model.AtomicIntegerModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.support.atomic.RedisAtomicInteger;


@Service
public class RedisDatabaseService implements IRedisDatabaseService{
    @Autowired
    RedisTemplate<String,String> template;

    private RedisAtomicInteger atomicSendMessages = new RedisAtomicInteger("sendMessages", template.getConnectionFactory(),0);
    private RedisAtomicInteger atomicRegistItems = new RedisAtomicInteger("registItems", template.getConnectionFactory(),0);
    private RedisAtomicInteger atomicCountUser = new RedisAtomicInteger("countUser", template.getConnectionFactory(),0);

    public void incrementValue(AtomicIntegerModel key){
        switch(key){
            case SENDMESSAGES -> atomicSendMessages.incrementAndGet();
            case REGISTEREDITEMS -> atomicRegistItems.incrementAndGet();
            case COUNTUSERS -> atomicCountUser.incrementAndGet();
        }
    }
}
