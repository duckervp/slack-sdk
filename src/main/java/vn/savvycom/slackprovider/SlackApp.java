package vn.savvycom.slackprovider;

import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.service.InstallationService;
import com.slack.api.bolt.service.builtin.FileInstallationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import vn.savvycom.slackprovider.service.IUserService;
import vn.savvycom.slackprovider.service.auth.CustomOAuthSuccessHandler;
import vn.savvycom.slackprovider.service.auth.CustomOAuthV2SuccessHandler;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class SlackApp {
    private final IUserService userService;

    private final Environment env;

    @Bean
    public AppConfig loadOAuthConfig() {
        return AppConfig.builder()
                .singleTeamBotToken(null)
                .clientId(env.getProperty("slack.clientId"))
                .clientSecret(env.getProperty("slack.clientSecret"))
                .signingSecret(env.getProperty("slack.signingSecret"))
                .scope("chat:write,im:write")
                .oauthInstallPath("/slack/install")
                .oauthRedirectUriPath("/slack/oauth_redirect")
                .build();
    }

    @Bean
    public InstallationService installationService(AppConfig config) {
        return new FileInstallationService(config);
    }

    @Bean
    public App initSlackApp(AppConfig config, InstallationService installationService) {
        App app = new App(config);
        if (config.getClientId() != null) {
            app.asOAuthApp(true);
            if (config.isClassicAppPermissionsEnabled()) {
                app.oauthCallback(new CustomOAuthSuccessHandler(config, installationService, userService));
            } else {
                app.oauthCallback(new CustomOAuthV2SuccessHandler(config, installationService, userService));
            }
        }
        return app;
    }

}