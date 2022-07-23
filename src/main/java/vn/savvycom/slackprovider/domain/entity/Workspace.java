package vn.savvycom.slackprovider.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "workspace")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Workspace {
    @Id
    private String id;
    private String name;
    @NotBlank(message = "workspace `botToken` must not be null or blank")
    private String botToken;
    @Column(columnDefinition = "boolean default true")
    private boolean active = true;
}
