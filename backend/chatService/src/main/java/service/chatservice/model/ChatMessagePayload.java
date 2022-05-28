package service.chatservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Data transfer object representing chatMessages
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessagePayload {
    @NotBlank
    private String content;

    @NotBlank
    private String sender;

    @NotNull
    @JsonProperty(value = "topic")
    private String topic;
}
