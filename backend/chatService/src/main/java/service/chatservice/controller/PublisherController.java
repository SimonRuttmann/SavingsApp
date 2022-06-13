package service.chatservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import service.chatservice.model.ChatMessagePayload;
import service.chatservice.service.RedisPublisher;

/**
 * Defines communication endpoints for publishing over api or websocket.
 */
@RestController
public class PublisherController {
    @Autowired
    private RedisPublisher redisPublisher;

    @PostMapping(path = "chat/message", consumes = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = {"http://localhost:3000"})
    public void sendMessageByApi(@RequestBody ChatMessagePayload message){
        String topic = message.getTopic();
        redisPublisher.publish(ChannelTopic.of(topic), message);
    }

    @MessageMapping(value = "/chat/message")
    public void sendMessage(ChatMessagePayload message) {
        String topic = message.getTopic();
        redisPublisher.publish(ChannelTopic.of(topic), message);
    }
}
