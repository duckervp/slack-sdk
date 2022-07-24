package vn.savvycom.slackprovider.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.savvycom.slackprovider.domain.entity.CustomInstaller;

import java.util.Optional;

@Repository
public interface CustomInstallerRepository extends JpaRepository<CustomInstaller, Long> {
    void deleteByTeamIdAndInstallerUserId(String teamId, String installerUserId);
    void deleteByEnterpriseIdAndInstallerUserId(String enterpriseId, String installerUserId);
    Optional<CustomInstaller> findByEnterpriseIdAndInstallerUserId(String enterpriseId, String installerUserId);
    Optional<CustomInstaller> findByTeamIdAndInstallerUserId(String teamId, String installerUserId);

    void deleteByEnterpriseId(String enterpriseId);
    void deleteByTeamId(String teamId);
}
