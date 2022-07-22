package vn.savvycom.slacksdk.service.client.impl;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.savvycom.slacksdk.domain.entity.User;
import vn.savvycom.slacksdk.domain.model.sendMessage.MessageInput;
import vn.savvycom.slacksdk.service.IUserService;
import vn.savvycom.slacksdk.service.client.IMessageService;

import java.io.IOException;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageService implements IMessageService {
    private final IUserService userService;

    /**
     * Send message
     */
    @Override
    public void send(MessageInput messageInput) {
        if (Objects.nonNull(messageInput.getChannelId())) {
            sendSingleMessage(messageInput);
            return;
        }
        sendMessageToAll(messageInput);
    }

    /**
     * Post a message to a channel your app is in using ID and message text
     */
    private void sendSingleMessage(MessageInput messageInput) {
        final String botToken = userService.findByUserId(messageInput.getChannelId()).getBotToken();
        sendSingleMessage(messageInput, botToken);
    }

    /**
     * Post a message to a channel your app is in using ID, message text and a bot token
     */
    private void sendSingleMessage(MessageInput messageInput, String botToken) {
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

    /**
     * Send message to all users
     */
    private void sendMessageToAll(MessageInput messageInput) {
        for (User user : userService.findAll()) {
            messageInput.setChannelId(user.getUserId());
            sendSingleMessage(messageInput, user.getBotToken());
        }
    }
}