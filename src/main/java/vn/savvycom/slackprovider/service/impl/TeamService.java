package vn.savvycom.slackprovider.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.savvycom.slackprovider.domain.entity.Recipient;
import vn.savvycom.slackprovider.domain.entity.Team;
import vn.savvycom.slackprovider.repository.TeamRepository;
import vn.savvycom.slackprovider.service.ITeamService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamService implements ITeamService {
    private final RecipientService recipientService;
    private final TeamRepository teamRepository;

    @Override
    public Team findActiveTeamById(String id) {
        Optional<Team> team = teamRepository.findById(id);
        if (team.isEmpty() || !team.get().isActive()) {
            throw new IllegalArgumentException("Not found any team with channelId " + id);
        }
        return team.get();
    }

    @Override
    public List<Team> findAllActiveTeam() {
        return teamRepository.findAll()
                .stream().filter(Team::isActive)
                .collect(Collectors.toList());
    }

    @Override
    public void save(Team team) {
        teamRepository.save(team);
    }

    @Override
    public void delete(String id) {
        Team activeTeam = findActiveTeamById(id);
        List<Recipient> activeRecipients = recipientService.findActiveRecipientByTeamId(id);
        recipientService.deleteRecipients(activeRecipients);
        activeTeam.setActive(false);
        save(activeTeam);
    }
}
