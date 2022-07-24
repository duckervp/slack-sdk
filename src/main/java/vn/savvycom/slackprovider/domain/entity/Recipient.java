package vn.savvycom.slackprovider.domain.entity;

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
public class Recipient {
    @Id
    private String id;
    @NotBlank(message = "`workspaceId` must not be null or blank")
    private String workspaceId;
    @Column(columnDefinition = "boolean default false")
    private boolean installUser = false;
    @Column(columnDefinition = "boolean default true")
    private boolean active = true;
}
