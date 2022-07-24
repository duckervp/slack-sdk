package vn.savvycom.slackprovider.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.slack.api.bolt.model.Bot;
import com.slack.api.bolt.model.Installer;
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
@Table(name = "installer")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomInstaller implements Installer {
    @Id
    private String botId;
    private String botUserId;
    private String botAccessToken;
    private String botRefreshToken;
    private Long botTokenExpiresAt;
    @Deprecated
    private String scope;
    private String botScope;

    private String appId;
    private String enterpriseId;
    private String enterpriseName;
    private String teamId;
    private String teamName;

    private Boolean isEnterpriseInstall;
    private String enterpriseUrl;
    private String tokenType;

    private String installerUserId;
    private String installerUserScope;
    private String installerUserAccessToken;
    private String installerUserRefreshToken;
    private Long installerUserTokenExpiresAt;

    private String incomingWebhookUrl;
    private String incomingWebhookChannelId;
    private String incomingWebhookConfigurationUrl;

    private Long installedAt;

    @Override
    public Bot toBot() {
        return null;
    }
}
