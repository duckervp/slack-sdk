package vn.savvycom.slacksdk;

import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.event.AppMentionEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class SlackApp {
    private final Environment env;

    // If you would like to run this app for a single workspace,
    // enabling this Bean factory should work for you.
//     @Bean
    public AppConfig loadSingleWorkspaceAppConfig() {
        return AppConfig.builder()
                .singleTeamBotToken(env.getProperty("slack.botToken"))
                .signingSecret(env.getProperty("slack.signingSecret"))
                .build();
    }

    // If you would like to run this app for multiple workspaces,
    // enabling this Bean factory should work for you.
    @Bean
    public AppConfig loadOAuthConfig() {
        return AppConfig.builder()
                .singleTeamBotToken(null)
                .clientId(env.getProperty("slack.clientId"))
                .clientSecret(env.getProperty("slack.clientSecret"))
                .signingSecret(env.getProperty("slack.signingSecret"))
                .scope("app_mentions:read,channels:history,channels:read,chat:write")
                .oauthInstallPath("/slack/install")
                .oauthRedirectUriPath("/slack/oauth_redirect")
                .build();
    }

    @Bean
    public App initSlackApp(AppConfig config) {
        log.info(config.toString());
        log.info(config.getSingleTeamBotToken().toString());
        System.out.println(config);
        System.out.println(config.getSingleTeamBotToken());
        App app = new App(config);
        if (config.getClientId() != null) {
            app.asOAuthApp(true);
        }
        app.command("/hello", (req, ctx) -> ctx.ack(r -> r.text("Thanks!")));
        Pattern greeting = Pattern.compile("hi |hello", Pattern.CASE_INSENSITIVE);
        app.message(greeting, (payload, ctx) -> {
            ctx.say("Hello, <@" + payload.getEvent().getUser() + ">");
            return ctx.ack();
        });
        app.event(AppMentionEvent.class, (payload, ctx) -> {
            Pattern howAreYou = Pattern.compile("how are you", Pattern.CASE_INSENSITIVE);
            String text = payload.getEvent().getText();
            String[] parts = text.split("<@\\w+>");
            String responseText = "What's up guy?";
            if (parts.length > 1) {
                if (howAreYou.matcher(parts[1].trim()).matches()) {
                    responseText = "Great.";
                } else if (greeting.matcher(parts[1].trim()).matches()) {
                    responseText = "Hello, <@" + payload.getEvent().getUser() + ">";
                }
            }
            ctx.say(responseText);
            return ctx.ack();
        });
        return app;
    }

}
