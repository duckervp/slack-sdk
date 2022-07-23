package vn.savvycom.slackprovider.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.savvycom.slackprovider.domain.model.addRecipient.RecipientInput;
import vn.savvycom.slackprovider.service.IRecipientService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/slack/recipient")
public class RecipientController extends BaseController {
    private final IRecipientService recipientService;

    @PostMapping()
    public ResponseEntity<?> addRecipient(@RequestBody @Valid RecipientInput recipientInput) {
        recipientService.save(recipientInput);
        return successResponse();
    }

    @DeleteMapping("/{recipientId}")
    public ResponseEntity<?> deleteRecipient(@PathVariable String recipientId) {
        recipientService.delete(recipientId);
        return successResponse();
    }
}
