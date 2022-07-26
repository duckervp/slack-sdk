package vn.savvycom.slackprovider.service.custom;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.slack.api.bolt.model.Bot;
import com.slack.api.bolt.model.Installer;
import com.slack.api.bolt.service.InstallationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import vn.savvycom.slackprovider.domain.entity.CustomBot;
import vn.savvycom.slackprovider.domain.entity.CustomInstaller;
import vn.savvycom.slackprovider.domain.entity.Recipient;
import vn.savvycom.slackprovider.domain.entity.Team;
import vn.savvycom.slackprovider.repository.CustomBotRepository;
import vn.savvycom.slackprovider.repository.CustomInstallerRepository;
import vn.savvycom.slackprovider.service.IRecipientService;
import vn.savvycom.slackprovider.service.ITeamService;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class JpaInstallationService implements InstallationService {
    private final CustomInstallerRepository installerRepository;
    private final CustomBotRepository botRepository;
    private final ITeamService teamService;
    private final IRecipientService recipientService;
    private final ObjectMapper objectMapper;
    private boolean historicalDataEnabled;
    @Override
    public boolean isHistoricalDataEnabled() {
        return historicalDataEnabled;
    }

    @Override
    public void setHistoricalDataEnabled(boolean isHistoricalDataEnabled) {
        this.historicalDataEnabled = isHistoricalDataEnabled;
    }

    @Override
    public void saveInstallerAndBot(Installer installer) {

        // save install info
        CustomInstaller installerInfo = objectMapper.convertValue(installer, CustomInstaller.class);
        installerRepository.save(installerInfo);

        // save team and recipient info
        String teamId = installerInfo.getTeamId();
        String teamName = installerInfo.getTeamName();
        if (installerInfo.getIsEnterpriseInstall()) {
            teamId = installerInfo.getEnterpriseId();
            teamName = installerInfo.getEnterpriseName();
        }
        Team team = Team.builder()
                .id(teamId)
                .name(teamName)
                .botToken(installerInfo.getBotAccessToken())
                .active(true)
                .build();
        Recipient recipient = Recipient.builder()
                .id(installerInfo.getInstallerUserId())
                .teamId(installerInfo.getTeamId())
                .installer(true)
                .active(true)
                .build();
        teamService.save(team);
        log.info("Save team info to db {}", team);
        recipientService.save(recipient);
        log.info("Save recipient info to db {}", recipient);
        // save bot info
        CustomBot botInfo = objectMapper.convertValue(installerInfo, CustomBot.class);
        botRepository.save(botInfo);
    }

    @Override
    public void saveBot(Bot bot) {
        CustomBot botInfo = objectMapper.convertValue(bot, CustomBot.class);
        botRepository.save(botInfo);
    }

    @Override
    public void deleteBot(Bot bot) {
        log.info("Delete bot {}", bot);
        CustomBot botInfo = objectMapper.convertValue(bot, CustomBot.class);
        if (botInfo.getIsEnterpriseInstall()) {
            botRepository.deleteByAppIdAndEnterpriseId(botInfo.getAppId(), botInfo.getEnterpriseId());
            teamService.delete(botInfo.getEnterpriseId());
        } else {
            botRepository.deleteByAppIdAndTeamId(botInfo.getAppId(), botInfo.getTeamId());
            teamService.delete(botInfo.getTeamId());
        }
    }

    @Override
    public void deleteInstaller(Installer installer) {
        log.info("Delete installer {}", installer);
        CustomInstaller installerInfo = objectMapper.convertValue(installer, CustomInstaller.class);
        if (installerInfo.getIsEnterpriseInstall()) {
            installerRepository.deleteByEnterpriseIdAndInstallerUserId(
                    installerInfo.getEnterpriseId(), installerInfo.getInstallerUserId());
        } else {
            installerRepository.deleteByTeamIdAndInstallerUserId(
                    installerInfo.getTeamId(), installerInfo.getInstallerUserId());
        }
    }

    @Override
    public Bot findBot(String enterpriseId, String teamId) {
        if (Objects.isNull(enterpriseId) && Objects.isNull(teamId)) {
            throw new IllegalArgumentException("enterpriseId or teamId must not be null");
        }
        if (Objects.nonNull(enterpriseId)) {
            return botRepository.findByEnterpriseId(enterpriseId)
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Not found any bot with enterprise id " + enterpriseId));
        }
        return botRepository.findByTeamId(teamId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Not found any bot with team id " + teamId));
    }

    @Override
    public Installer findInstaller(String enterpriseId, String teamId, String userId) {
        Assert.notNull(userId, "installer userId must not be null");
        if (Objects.isNull(enterpriseId) && Objects.isNull(teamId)) {
            throw new IllegalArgumentException("enterpriseId or teamId must not be null");
        }
        if (Objects.nonNull(enterpriseId)) {
            return installerRepository.findByEnterpriseIdAndInstallerUserId(enterpriseId, userId)
                    .orElseThrow(() -> new IllegalArgumentException(
                            String.format("Not found any bot with enterprise id %s and installer user id %s"
                                    , enterpriseId, userId)));
        }
        return installerRepository.findByTeamIdAndInstallerUserId(teamId, userId)
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("Not found any bot with team id %s and installer user id %s"
                                , teamId, userId)));
    }

    @Override
    public void deleteAll(String enterpriseId, String teamId) {
        log.info("Delete all {} {}", enterpriseId, teamId);
        if (Objects.nonNull(enterpriseId)) {
            botRepository.deleteByEnterpriseId(enterpriseId);
            installerRepository.deleteByEnterpriseId(enterpriseId);
            teamService.delete(enterpriseId);
        }
        if (Objects.nonNull(teamId)) {
            botRepository.deleteByTeamId(teamId);
            installerRepository.deleteByTeamId(teamId);
            teamService.delete(teamId);
        }
    }
}
