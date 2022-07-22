package vn.savvycom.slacksdk.service.client;

import vn.savvycom.slacksdk.domain.model.sendMessage.MessageInput;

public interface IMessageService {
    void send(MessageInput messageInput);
}
