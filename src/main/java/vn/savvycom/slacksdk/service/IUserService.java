package vn.savvycom.slacksdk.service;

import vn.savvycom.slacksdk.domain.entity.User;

import java.util.List;

public interface IUserService {
    void save(User user);

    User findByUserId(String userId);

    List<User> findByTeamId(String teamId);

    List<User> findByEnterpriseId(String enterpriseId);
}
