package vn.savvycom.slacksdk.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.savvycom.slacksdk.domain.model.sendMessage.MessageInput;
import vn.savvycom.slacksdk.service.client.MessageService;

import javax.validation.Valid;

@RestController
@RequestMapping("/slack")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PostMapping("/sendMessage")
    public void sendMessage(@RequestBody @Valid MessageInput messageInput) {
        messageService.send(messageInput);
    }
}
