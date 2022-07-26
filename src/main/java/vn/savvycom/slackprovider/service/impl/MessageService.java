package vn.savvycom.slackprovider.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.savvycom.slackprovider.domain.entity.Message;
import vn.savvycom.slackprovider.domain.entity.Recipient;
import vn.savvycom.slackprovider.domain.entity.Team;
import vn.savvycom.slackprovider.domain.model.sendMessage.MessageInput;
import vn.savvycom.slackprovider.exception.SendMessageFailedException;
import vn.savvycom.slackprovider.exception.SlackException;
import vn.savvycom.slackprovider.repository.MessageRepository;
import vn.savvycom.slackprovider.service.IRecipientService;
import vn.savvycom.slackprovider.service.IMessageService;
import vn.savvycom.slackprovider.service.ITeamService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageService implements IMessageService {
    private final ITeamService teamService;
    private final IRecipientService recipientService;
    private final MessageRepository messageRepository;
    private final ObjectMapper objectMapper;

    /**
     * Send message
     */
    @Override
    public void send(MessageInput messageInput) {
        // if message input specify the recipient then send message to that recipient
        // otherwise send the message to all
        if (Objects.nonNull(messageInput.getRecipientId())) {
            sendSingleMessage(messageInput);
            return;
        }
        sendMessageToAll(messageInput);
    }

    /**
     * Post a message to a channel your app is in using ID and message text
     */
    private void sendSingleMessage(MessageInput messageInput) {
        // find the bot token for the team that the recipient is in and send message
        Recipient recipient = recipientService.findActiveRecipientById(messageInput.getRecipientId());
        Team team = teamService.findActiveTeamById(recipient.getTeamId());
        sendMessage(messageInput, team.getBotToken());
    }

    /**
     * Post a message to a channel your app is in using ID, message text and a bot token
     */
    private void sendMessage(MessageInput messageInput, String botToken) {
        // you can get this instance via ctx.client() in a Bolt app
        MethodsClient client = Slack.getInstance().methods();
        try {
            // Call the chat.postMessage method using the built-in WebClient
            ChatPostMessageResponse result = client.chatPostMessage(r -> r
                            // The token you used to initialize your app
                            .token(botToken)
                            .channel(messageInput.getRecipientId())
                            .text(messageInput.getContent())
            );
            // Print result, which includes information about the message (like TS)
            log.info("result {}", result);
            if (!result.isOk()) {
                throw new SendMessageFailedException(result.getError());
            }
            // save message
            Message message = objectMapper.convertValue(messageInput, Message.class);
            message.setCreatedAt(LocalDateTime.now());
            messageRepository.save(message);
        } catch (IOException | SlackApiException e) {
            log.error("error: {}", e.getMessage(), e);
            throw new SlackException(e.getMessage());
        }
    }

    /**
     * Send message to all users in each team
     */
    private void sendMessageToAll(MessageInput messageInput) {
        List<Team> teams = teamService.findAllActiveTeam();
        for (Team team : teams) {
            for (Recipient recipient : recipientService.findActiveRecipientByTeamId(team.getId())) {
                messageInput.setRecipientId(recipient.getId());
                sendMessage(messageInput, team.getBotToken());
            }
        }
    }
}