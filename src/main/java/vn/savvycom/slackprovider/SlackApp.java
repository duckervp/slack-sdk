package vn.savvycom.slackprovider;

import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.service.InstallationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class SlackApp {
    public final InstallationService jpaInstallationService;
    private final Environment env;

    @Bean
    public AppConfig loadOAuthConfig() {
        return AppConfig.builder()
                .singleTeamBotToken(null)
                .clientId(env.getProperty("slack.clientId"))
                .clientSecret(env.getProperty("slack.clientSecret"))
                .signingSecret(env.getProperty("slack.signingSecret"))
                .scope("chat:write,chat:write.public,im:write")
                .oauthInstallPath("/slack/install")
                .oauthRedirectUriPath("/slack/oauth_redirect")
                .build();
    }

    @Bean
    public App initSlackApp(AppConfig config, InstallationService installationService) {
        App app = new App(config);
        if (config.getClientId() != null) {
            app.asOAuthApp(true);
            app.service(installationService);
        }
        return app;
    }

}