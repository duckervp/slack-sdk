package vn.savvycom.slacksdk.service;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.model.Conversation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import vn.savvycom.slacksdk.domain.model.sendMessage.TeamMessageInput;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageService {
    private final Environment env;

    /**
     * Find conversation ID using the conversations.list method
     */
    public String findConversation(String name) {
        // you can get this instance via ctx.client() in a Bolt app
        var client = Slack.getInstance().methods();
        try {
            // Call the conversations.list method using the built-in WebClient
            var result = client.conversationsList(r -> r
                    // The token you used to initialize your app
                    .token(env.getProperty("slack.botToken"))
            );
            for (Conversation channel : result.getChannels()) {
                if (channel.getName().equals(name)) {
                    var conversationId = channel.getId();
                    // Print result
                    log.info("Found conversation ID: {}", conversationId);
                    // Break from for loop
                    return conversationId;
                }
            }
        } catch (IOException | SlackApiException e) {
            log.error("error: {}", e.getMessage(), e);
        }
        return null;
    }

    /**
     * Reply to a message with the channel ID and message TS
     */
    public void replyMessage(String id, String ts, String text) {
        // you can get this instance via ctx.client() in a Bolt app
        var client = Slack.getInstance().methods();
        try {
            // Call the chat.postMessage method using the built-in WebClient
            var result = client.chatPostMessage(r -> r
                            // The token you used to initialize your app
                            .token(env.getProperty("slack.botToken"))
                            .channel(id)
                            .threadTs(ts)
                            .text(text)
                    // You could also use a blocks[] array to send richer content
            );
            // Print result
            log.info("result {}", result);
        } catch (IOException | SlackApiException e) {
            log.error("error: {}", e.getMessage(), e);
        }
    }

    /**
     * Post a message to a channel your app is in using ID and message text
     */
    public void publishMessage(String id, String text) {
        // you can get this instance via ctx.client() in a Bolt app
        var client = Slack.getInstance().methods();
        try {
            // Call the chat.postMessage method using the built-in WebClient
            var result = client.chatPostMessage(r -> r
                            // The token you used to initialize your app
                            .token(env.getProperty("slack.botToken"))
                            .channel(id)
                            .text(text)
                    // You could also use a blocks[] array to send richer content
            );
            // Print result, which includes information about the message (like TS)
            log.info("result {}", result);
        } catch (IOException | SlackApiException e) {
            log.error("error: {}", e.getMessage(), e);
        }
    }
}