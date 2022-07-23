package vn.savvycom.slackprovider.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.savvycom.slackprovider.domain.entity.Recipient;

import java.util.List;

@Repository
public interface RecipientRepository extends JpaRepository<Recipient, String> {
    List<Recipient> findByWorkspaceId(String workspaceId);
}
