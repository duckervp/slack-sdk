package vn.savvycom.slacksdk.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import vn.savvycom.slacksdk.domain.entity.User;
import vn.savvycom.slacksdk.repository.UserRepository;
import vn.savvycom.slacksdk.service.IUserService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User findByUserId(String userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found with user id " + userId));
    }

    @Override
    public List<User> findByTeamId(String teamId) {
        return userRepository.findByTeamId(teamId);
    }

    @Override
    public List<User> findByEnterpriseId(String enterpriseId) {
        return userRepository.findByEnterpriseId(enterpriseId);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
