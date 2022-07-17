package vn.savvycom.slacksdk.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vn.savvycom.slacksdk.domain.model.sendMessage.MessageInput;
import vn.savvycom.slacksdk.service.MessageService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/slack")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PostMapping("/sendMessage")
    public void sendMessage(@RequestBody @Valid MessageInput messageInput) {
        messageService.publishMessage(
                messageInput.getChannelId(),
                messageInput.getContent());
    }

    @GetMapping("/channel")
    public Map<String, Object> findChannelId(@RequestParam String name) {
        var result = new HashMap<String, Object>();
        String channelId = messageService.findConversation(name);
        if (Objects.nonNull(channelId)) {
            result.put("channelId", channelId);
        }
        return result;
    }
}
