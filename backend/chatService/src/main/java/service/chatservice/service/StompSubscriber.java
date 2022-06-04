package service.chatservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class StompSubscriber {
    private final RedisSubscriber redisSubscriber;
    private final RedisMessageListenerContainer redisMessageListener;

    @Autowired
    public StompSubscriber(RedisSubscriber redisSubscriber, RedisMessageListenerContainer redisMessageListener) {
        this.redisSubscriber = redisSubscriber;
        this.redisMessageListener = redisMessageListener;
    }

    public ResponseEntity<String> subscribeToChannel(ChannelTopic topic){
        try {
            redisMessageListener.addMessageListener(redisSubscriber::onMessage, topic);
        }catch (Exception e){
            return new ResponseEntity<>(e.toString(),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public void unsubscribeChannel(ChannelTopic topic){
        redisMessageListener.removeMessageListener(redisSubscriber,topic);
    }
}
