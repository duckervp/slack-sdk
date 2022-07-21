package vn.savvycom.slacksdk.service.client;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.savvycom.slacksdk.domain.model.sendMessage.MessageInput;
import vn.savvycom.slacksdk.service.IUserService;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageService {
    private final IUserService userService;

    /**
     * Post a message to a channel your app is in using ID and message text
     */
    public void send(MessageInput messageInput) {
        final String botToken = userService.findByUserId(messageInput.getChannelId()).getBotToken();
        // you can get this instance via ctx.client() in a Bolt app
        var client = Slack.getInstance().methods();
        try {
            // Call the chat.postMessage method using the built-in WebClient
            var result = client.chatPostMessage(r -> r
                            // The token you used to initialize your app
                            .token(botToken)
                            .channel(messageInput.getChannelId())
                            .text(messageInput.getContent())
                    // You could also use a blocks[] array to send richer content
            );
            // Print result, which includes information about the message (like TS)
            log.info("result {}", result);
        } catch (IOException | SlackApiException e) {
            log.error("error: {}", e.getMessage(), e);
        }
    }
}