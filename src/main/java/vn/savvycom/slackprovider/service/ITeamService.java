package vn.savvycom.slackprovider.service;

import vn.savvycom.slackprovider.domain.entity.Team;

import java.util.List;

public interface ITeamService {
    Team findActiveTeamById(String id);

    List<Team> findAllActiveTeam();

    void save(Team team);

    void delete(String id);
}
