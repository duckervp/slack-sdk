package vn.savvycom.slackprovider.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.savvycom.slackprovider.domain.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}
