package service.chatservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;
import service.RedisDatabaseService;
import service.chatservice.model.ChatMessage;
import service.chatservice.model.ChatMessagePayload;

import static model.AtomicIntegerModel.SENDMESSAGES;

@Service
public class RedisPublisher {
    @Autowired
    private RedisDatabaseService redisDatabaseService;

    @Autowired
    private RedisTemplate<String, ChatMessage> redisTemplate;

    @Autowired
    private RedisDBService redisDBService;

    /**
     * Pubishes a given message on a given topic in redis
     * @param topic to be published on
     * @param message
     */
    public void publish(ChannelTopic topic, ChatMessagePayload message) {
        ChatMessage publishedMessage = ChatMessage.getBuilder()
                .withTopic(topic)
                .withContent(message.getContent())
                .withSender(message.getSender())
                .build();

        redisDBService.addMessageToGroup(publishedMessage.getTopic(),publishedMessage);
        redisTemplate.convertAndSend(topic.getTopic(), publishedMessage);
        redisDatabaseService.incrementValue(SENDMESSAGES);
    }
}
