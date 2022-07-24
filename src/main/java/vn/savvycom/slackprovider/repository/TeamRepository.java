package vn.savvycom.slackprovider.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.savvycom.slackprovider.domain.entity.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, String> {
}
