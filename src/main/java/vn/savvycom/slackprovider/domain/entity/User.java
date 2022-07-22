package vn.savvycom.slackprovider.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "user_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    private String userId;
    private String teamId;
    private String enterpriseId;
    private String botUserId;
    @NotBlank
    private String botToken;
    private String userToken;
    private String scope;

}
