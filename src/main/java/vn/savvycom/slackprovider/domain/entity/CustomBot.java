package vn.savvycom.slackprovider.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.slack.api.bolt.model.Bot;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "bot")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomBot implements Bot {
    @Id
    private String botId;//B03P67D9NSK
    private String botUserId;
    private String botScope;
    private String botAccessToken;
    private String botRefreshToken;
    private Long botTokenExpiresAt;

    private String appId;
    private String enterpriseId;
    private String enterpriseName;
    private String teamId;
    private String teamName;

    private Boolean isEnterpriseInstall;
    private String enterpriseUrl;
    private String tokenType;
    private String scope;

    private Long installedAt;
}
