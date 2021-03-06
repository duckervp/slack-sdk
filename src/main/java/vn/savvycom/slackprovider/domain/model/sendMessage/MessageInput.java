package vn.savvycom.slackprovider.domain.model.sendMessage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageInput {
    private String recipientId;

    @NotBlank(message = "message `content` must not be blank")
    private String content;

    private String rule;
}
