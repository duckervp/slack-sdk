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
import vn.savvycom.slackprovider.domain.entity.Workspace;
import vn.savvycom.slackprovider.repository.CustomBotRepository;
import vn.savvycom.slackprovider.repository.CustomInstallerRepository;
import vn.savvycom.slackprovider.service.IRecipientService;
import vn.savvycom.slackprovider.service.IWorkspaceService;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class JpaInstallationService implements InstallationService {
    private final CustomInstallerRepository installerRepository;
    private final CustomBotRepository botRepository;
    private final IWorkspaceService workspaceService;
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

        // save workspace and recipient info
        String workspaceId = installerInfo.getTeamId();
        String workspaceName = installerInfo.getTeamName();
        if (installerInfo.getIsEnterpriseInstall()) {
            workspaceId = installerInfo.getEnterpriseId();
            workspaceName = installerInfo.getEnterpriseName();
        }
        Workspace workspace = Workspace.builder()
                .id(workspaceId)
                .name(workspaceName)
                .botToken(installerInfo.getBotAccessToken())
                .active(true)
                .build();
        Recipient recipient = Recipient.builder()
                .id(installerInfo.getInstallerUserId())
                .workspaceId(installerInfo.getTeamId())
                .installUser(true)
                .active(true)
                .build();
        workspaceService.save(workspace);
        log.info("Save workspace info to db {}", workspace);
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
        CustomBot botInfo = objectMapper.convertValue(bot, CustomBot.class);
        if (botInfo.getIsEnterpriseInstall()) {
            botRepository.deleteByAppIdAndEnterpriseId(botInfo.getAppId(), botInfo.getEnterpriseId());
            workspaceService.delete(botInfo.getEnterpriseId());
        } else {
            botRepository.deleteByAppIdAndTeamId(botInfo.getAppId(), botInfo.getTeamId());
            workspaceService.delete(botInfo.getTeamId());
        }
    }

    @Override
    public void deleteInstaller(Installer installer) {
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
        if (Objects.nonNull(enterpriseId)) {
            botRepository.deleteByEnterpriseId(enterpriseId);
            installerRepository.deleteByEnterpriseId(enterpriseId);
            workspaceService.delete(enterpriseId);
        }
        if (Objects.nonNull(teamId)) {
            botRepository.deleteByEnterpriseId(teamId);
            installerRepository.deleteByEnterpriseId(teamId);
            workspaceService.delete(enterpriseId);
        }
    }
}
