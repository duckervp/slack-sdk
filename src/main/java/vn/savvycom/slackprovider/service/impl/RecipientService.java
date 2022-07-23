package vn.savvycom.slackprovider.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.savvycom.slackprovider.domain.entity.Recipient;
import vn.savvycom.slackprovider.domain.model.addRecipient.RecipientInput;
import vn.savvycom.slackprovider.repository.RecipientRepository;
import vn.savvycom.slackprovider.service.IRecipientService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipientService implements IRecipientService {
    private final RecipientRepository recipientRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void save(Recipient recipient) {
        recipientRepository.save(recipient);
    }

    @Override
    public void save(RecipientInput recipientInput) {
        Recipient recipient = objectMapper.convertValue(recipientInput, Recipient.class);
        recipient.setActive(true);
        save(recipient);
    }

    @Override
    public Recipient findById(String id) {
        return recipientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Not found any recipient with id " + id));
    }

    @Override
    public List<Recipient> findByWorkspaceId(String workspaceId) {
        return recipientRepository.findByWorkspaceId(workspaceId);
    }


    @Override
    public List<Recipient> findAll() {
        return recipientRepository.findAll();
    }

    @Override
    public void delete(String id) {
        Recipient recipient = findById(id);
        recipient.setActive(false);
        save(recipient);
    }
}
