package vn.savvycom.slackprovider.service;

import vn.savvycom.slackprovider.domain.entity.Recipient;
import vn.savvycom.slackprovider.domain.model.addRecipient.RecipientInput;

import java.util.List;

public interface IRecipientService {
    void save(Recipient recipient);

    void save(RecipientInput recipientInput);

    Recipient findById(String id);

    List<Recipient> findByWorkspaceId(String workspaceId);

    List<Recipient> findAll();

    void delete(String id);
}
