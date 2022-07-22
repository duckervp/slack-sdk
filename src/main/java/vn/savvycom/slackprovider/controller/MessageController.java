package vn.savvycom.slackprovider.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.savvycom.slackprovider.domain.model.sendMessage.MessageInput;
import vn.savvycom.slackprovider.service.client.IMessageService;

import javax.validation.Valid;

@RestController
@RequestMapping("/slack")
@RequiredArgsConstructor
public class MessageController {
    private final IMessageService messageService;

    @PostMapping("/sendMessage")
    public ResponseEntity<?> sendMessage(@RequestBody @Valid MessageInput messageInput) {
        messageService.send(messageInput);
        return new ResponseEntity<>("Send message succeed.", HttpStatus.OK);
    }
}
