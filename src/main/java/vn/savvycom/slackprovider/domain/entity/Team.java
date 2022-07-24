package vn.savvycom.slackprovider.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "team")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Team {
    @Id
    private String id;
    private String name;
    @NotBlank(message = "workspace `botToken` must not be null or blank")
    private String botToken;
    @Column(columnDefinition = "boolean default true")
    private boolean active = true;
}
