package service.chatservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
/**
 * Data transfer object representing chatMessages
 */
public class ChatMessagePayload {
    @NotBlank
    private String content;

    @NotBlank
    private String sender;

    @NotNull
    @JsonProperty(value = "topic")
    private String topic;
}
