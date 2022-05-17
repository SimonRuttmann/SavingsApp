package service.chatservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import service.chatservice.model.ChatMessage;
import service.chatservice.model.ChatMessagePayload;

@Slf4j
@Service
public class RedisSubscriber implements MessageListener {
    private final ObjectMapper objectMapper;
    private final RedisTemplate<String, ChatMessage> redisTemplate;
    private final SimpMessageSendingOperations messagingTemplate;

    @Autowired
    public RedisSubscriber(ObjectMapper objectMapper, RedisTemplate<String, ChatMessage> redisTemplate, SimpMessageSendingOperations messagingTemplate) {
        this.objectMapper = objectMapper;
        this.redisTemplate = redisTemplate;
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            // Redis Deserialize
            String publishedMessage = redisTemplate.getStringSerializer().deserialize(message.getBody());

            // ChatMessage
            ChatMessagePayload chatMessage = objectMapper.readValue(publishedMessage, ChatMessagePayload.class);

            // WebSocket
            messagingTemplate.convertAndSend("/sub/chat/rooms/" + chatMessage.getTopic(), chatMessage);
        }
        catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }
}
