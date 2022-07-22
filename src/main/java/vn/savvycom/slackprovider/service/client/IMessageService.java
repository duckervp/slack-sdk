package vn.savvycom.slackprovider.service.client;

import vn.savvycom.slackprovider.domain.model.sendMessage.MessageInput;

public interface IMessageService {
    void send(MessageInput messageInput);
}
