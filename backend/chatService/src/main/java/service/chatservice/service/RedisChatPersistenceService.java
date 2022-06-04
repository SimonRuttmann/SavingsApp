package service.chatservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import service.chatservice.model.ChatMessage;
import service.chatservice.repository.IRedisChatPersistenceService;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@Service
public class RedisChatPersistenceService implements IRedisChatPersistenceService {
    @Autowired
    RedisTemplate<String, ChatMessage> template;

    @Resource(name="redisTemplate")
    private ListOperations<String,ChatMessage> listOperations;

    /**
     * Adds a message to the group message list
     * @param groupid that the message should be added to
     * @param message to be added
     */
    public void addMessageToGroup(@NotNull String groupid,@NotNull ChatMessage message){
        try {
            listOperations.leftPush(groupid, message);
        }catch (Exception e){
            log.error("An error has occured while pushing message to database.Exception: "+e);
        }
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
