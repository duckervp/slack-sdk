package vn.savvycom.slackprovider.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.savvycom.slackprovider.domain.entity.Recipient;
import vn.savvycom.slackprovider.domain.model.addRecipient.RecipientInput;
import vn.savvycom.slackprovider.repository.RecipientRepository;
import vn.savvycom.slackprovider.service.IRecipientService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public Recipient findActiveRecipientById(String id) {
        Optional<Recipient> recipient = recipientRepository.findById(id);
        if (recipient.isEmpty() || !recipient.get().isActive()) {
            throw new IllegalArgumentException("Not found any recipient with id " + id);
        }
        return recipient.get();
    }

    @Override
    public List<Recipient> findActiveRecipientByTeamId(String teamId) {
        return recipientRepository.findByTeamId(teamId)
                .stream().filter(Recipient::isActive)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(String id) {
        Recipient recipient = findActiveRecipientById(id);
        recipient.setActive(false);
        save(recipient);
    }

    @Override
    public void deleteRecipients(List<Recipient> recipients) {
        for (Recipient recipient : recipients) {
            recipient.setActive(false);
        }
        recipientRepository.saveAll(recipients);
    }
}
