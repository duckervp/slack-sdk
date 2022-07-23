package vn.savvycom.slackprovider.domain.model.addRecipient;

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
public class RecipientInput {
    @NotBlank(message = "recipient `id` must not be null or blank")
    private String id;
    @NotBlank(message = "recipient `workspaceId` must not be null or blank")
    private String workspaceId;
}
