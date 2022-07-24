package vn.savvycom.slackprovider.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.savvycom.slackprovider.domain.entity.CustomBot;

import java.util.Optional;

@Repository
public interface CustomBotRepository extends JpaRepository<CustomBot, Long> {
    void deleteByAppIdAndTeamId(String appId, String teamId);
    void deleteByAppIdAndEnterpriseId(String appId, String enterpriseId);

    Optional<CustomBot> findByEnterpriseId(String enterpriseId);

    Optional<CustomBot> findByTeamId(String teamId);

    void deleteByEnterpriseId(String enterpriseId);
    void deleteByTeamId(String teamId);
}
