package service.chatservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.chatservice.repository.IRedisChatPersistenceService;
import service.chatservice.service.RedisChatPersistenceService;
import service.chatservice.service.SubscriptionService;

/**
 * Defines endpoints for subscribing
 */
@Slf4j
@RestController
@RequestMapping(path = "/chat")
public class SubscriberController {

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private IRedisChatPersistenceService redisChatPersistenceService;

    @GetMapping(path = "/rooms/{topic}/messages")
    @CrossOrigin(origins = {"http://localhost:8080"})
    public ResponseEntity<?> getChatMessages(@PathVariable("topic") String topic) {
        try {
            if (topic != null) {
                return new ResponseEntity<>(redisChatPersistenceService.getMessagesByTopic(topic), HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>("Error: "+e,HttpStatus.NOT_FOUND);
        }
        log.error("Error message - topic not found with id: {}", topic);
        return new ResponseEntity<>("Error message - chat room not found", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/sub/{topic}")
    @CrossOrigin(origins = {"http://localhost:8080"})
    public ResponseEntity<?> subChannel(@PathVariable("topic") String topic){
        return subscriptionService.subscribeToChannel(new ChannelTopic(topic));
    }

    @GetMapping("/unsub/{topic}")
    public void unsubChannel(@PathVariable("topic") String topic){
        subscriptionService.unsubscribeChannel(new ChannelTopic(topic));
    }
}
