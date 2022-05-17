package service.chatservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import service.chatservice.model.ChatMessage;
import service.chatservice.repository.RedisRepository;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class RedisDBService implements RedisRepository {
    @Autowired
    RedisTemplate<String, ChatMessage> template;

    @Resource(name="redisTemplate")
    private ListOperations<String,ChatMessage> listOperations;

    public void addMessageToGroup(@NotNull String groupid,@NotNull ChatMessage message){
        listOperations.leftPush(groupid, message);
    }

    /**
     * Fetches last message for a given groupId from redis
     * @param groupId topic on which the messages were published
     * @param count amount of messages fetched
     * @return list of the last  30 saved messages
     */
    public List<ChatMessage> getMessagesByTopic(@NotNull String groupId, int count){

        return listOperations.range(groupId,0,count);
    }

    /**
     * Fetches last 30 messages for a given groupId from redis
     * @param groupId topic on which the messages were published
     * @return list of the last  30 saved messages
     */
    public List<ChatMessage> getMessagesByTopic(@NotNull String groupId){

        return getMessagesByTopic(groupId,29);
    }
}
