package vn.savvycom.slacksdk.domain.model.sendMessage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageInput {
    @NotBlank(message = "channelId must not be blank")
    private String channelId;
    private String content;
}
