package service.chatservice.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.listener.ChannelTopic;

import java.io.Serial;
import java.io.Serializable;


@Getter
@NoArgsConstructor
/**
 * Data persistence object generated from ChatMessagePayload objects
 */
public class ChatMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 34789214329287934L;

    @Setter(value = AccessLevel.PRIVATE)
    private String content;

    @Setter(value = AccessLevel.PRIVATE)
    private boolean isRead = false;

    @Setter(value = AccessLevel.PRIVATE)
    private String sender;

    @Setter(value = AccessLevel.PRIVATE)
    private String topic;

    public static ChatMessageBuilder getBuilder() {
        return new ChatMessageBuilder();
    }

    public static class ChatMessageBuilder {
        private String content;
        private String sender;
        private ChannelTopic topic;

        public ChatMessageBuilder withContent(String content) {
            this.content = content;
            return this;
        }

        public ChatMessageBuilder withSender(String sender) {
            this.sender = sender;
            return this;
        }

        public ChatMessageBuilder withTopic(ChannelTopic topic) {
            this.topic = topic;
            return this;
        }

        public ChatMessage build() {
            ChatMessage message = new ChatMessage();
            message.setContent(content);
            message.setSender(sender);
            message.setTopic(topic.toString());
            return message;
        }
    }
}
