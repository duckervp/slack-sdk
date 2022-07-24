package vn.savvycom.slackprovider.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "recipient")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Recipient {
    @Id
    private String id;
    @NotBlank(message = "recipient `channelId` must not be null or blank")
    private String channelId;
    @NotBlank(message = "recipient `teamId` must not be null or blank")
    private String teamId;
    @Column(columnDefinition = "boolean default false")
    private boolean installUser = false;
    @Column(columnDefinition = "boolean default true")
    private boolean active = true;
}
