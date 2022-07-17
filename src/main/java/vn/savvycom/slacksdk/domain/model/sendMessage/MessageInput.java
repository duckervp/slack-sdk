package vn.savvycom.slacksdk.domain.model.sendMessage;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class MessageInput {
    @NotBlank(message = "channelId must not be blank")
    private String channelId;
    private String content;
}
