package service.chatservice.repository;

import service.chatservice.model.ChatMessage;

import java.util.List;

public interface RedisRepository {

    void addMessageToGroup(String groupId, ChatMessage message);

    List<ChatMessage> getMessagesByTopic(String groupId);
}
