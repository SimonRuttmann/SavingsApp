package service.chatservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;
import service.RedisDatabaseService;
import service.chatservice.model.ChatMessage;
import service.chatservice.model.ChatMessagePayload;
import service.chatservice.repository.IRedisChatPersistenceService;

import static model.AtomicIntegerModel.SENDMESSAGES;

/**
 * Class for everything related to redis publishing
 */
@Service
public class RedisPublisher {
    @Autowired
    private RedisDatabaseService redisDatabaseService;

    @Autowired
    private RedisTemplate<String, ChatMessage> redisTemplate;

    @Autowired
    private IRedisChatPersistenceService redisChatPersistenceService;

    /**
     * Publishes a given message on a given topic in redis.
     * Messages is persisted for the given topic.
     * Integer SendMessages is incemented.
     * @param topic to be published on
     * @param message
     */
    public void publish(ChannelTopic topic, ChatMessagePayload message) {
        ChatMessage publishedMessage = ChatMessage.getBuilder()
                .withTopic(topic)
                .withContent(message.getContent())
                .withSender(message.getSender())
                .build();

        redisChatPersistenceService.addMessageToGroup(publishedMessage.getTopic(),publishedMessage);
        redisTemplate.convertAndSend(topic.getTopic(), publishedMessage);
        redisDatabaseService.incrementValue(SENDMESSAGES);
    }
}
