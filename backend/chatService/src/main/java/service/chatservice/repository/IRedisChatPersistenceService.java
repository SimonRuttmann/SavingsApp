package service.chatservice.repository;

import service.chatservice.model.ChatMessage;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface IRedisChatPersistenceService {
    void addMessageToGroup(String groupid, ChatMessage message);
    List<ChatMessage> getMessagesByTopic(String groupId, int count);
    List<ChatMessage> getMessagesByTopic(String groupId);
}
