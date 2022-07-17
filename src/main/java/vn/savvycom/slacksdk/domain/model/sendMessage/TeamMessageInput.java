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
public class TeamMessageInput {
    @NotBlank(message = "teamId must not be blank")
    private String teamId;

    @NotBlank(message = "channelId must not be blank")
    private String channelId;
    private String content;
}
