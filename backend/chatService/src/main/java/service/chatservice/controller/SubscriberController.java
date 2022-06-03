package service.chatservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.chatservice.service.ChatService;
import service.chatservice.service.RedisDBService;

/**
 * Defines endpoints for subscribing
 */
@Slf4j
@RestController
@RequestMapping(path = "/chat")
public class SubscriberController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private RedisDBService redisDBService;

    @GetMapping(path = "/rooms/{topic}/messages")
    public ResponseEntity<?> getChatMessages(@PathVariable("topic") String topic) {
        try {
            if (topic != null) {
                return new ResponseEntity<>(redisDBService.getMessagesByTopic(topic), HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>("Error: "+e,HttpStatus.NOT_FOUND);
        }
        log.error("Error message - topic not found with id: {}", topic);
        return new ResponseEntity<>("Error message - chat room not found", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/sub/{topic}")
    public void subChannel(@PathVariable("topic") String topic){
        chatService.subscribeToChannel(new ChannelTopic(topic));
    }

    @GetMapping("/unsub/{topic}")
    public void unsubChannel(@PathVariable("topic") String topic){
        chatService.unsubscribeChannel(new ChannelTopic(topic));
    }
}
