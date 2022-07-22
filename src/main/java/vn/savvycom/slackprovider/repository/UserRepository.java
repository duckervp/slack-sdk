package vn.savvycom.slackprovider.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.savvycom.slackprovider.domain.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUserId(String userId);
    List<User> findByTeamId(String teamId);
    List<User> findByEnterpriseId(String enterpriseId);
}
