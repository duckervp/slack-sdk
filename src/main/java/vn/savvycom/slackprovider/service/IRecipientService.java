package vn.savvycom.slackprovider.service;

import vn.savvycom.slackprovider.domain.entity.Recipient;
import vn.savvycom.slackprovider.domain.model.addRecipient.RecipientInput;

import java.util.List;

public interface IRecipientService {
    void save(Recipient recipient);

    void save(RecipientInput recipientInput);

    Recipient findActiveRecipientById(String id);

    List<Recipient> findActiveRecipientByTeamId(String teamId);

    void delete(String id);

    void deleteRecipients(List<Recipient> recipients);
}
