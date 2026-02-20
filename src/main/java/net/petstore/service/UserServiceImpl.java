package net.petstore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.petstore.mapper.UserMapper;
import net.petstore.model.User;
import net.petstore.repository.UserRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public void createUser(User body) {
        log.info("createUser: {}", body);
        net.petstore.domain.User user = userMapper.toEntity(body);
        userRepository.save(user);
    }

    @CacheEvict(cacheNames = "users", key = "#username")
    public void deleteUser(String username) {
        log.info("deleteUser: {}", username);
        net.petstore.domain.User user = userRepository.findByUsername(username);
        if (user != null) {
            userRepository.delete(user);
        }
    }

    @Cacheable(cacheNames = "users", key = "#username")
    public User getUserByName(String username) {
        log.info("getUserByName: {}", username);
        net.petstore.domain.User byUsername = userRepository.findByUsername(username);
        return userMapper.toDto(byUsername);
    }

    @CacheEvict(cacheNames = "users", key = "#username")
    public void updateUser(String username, User body) {
        log.info("updateUser: {}", username);
        net.petstore.domain.User user = userRepository.findByUsername(username);
        if (user != null) {
            user.setFirstName(body.getFirstName());
            user.setLastName(body.getLastName());
            user.setEmail(body.getEmail());
            user.setPassword(body.getPassword());
            userRepository.save(user);
        }
    }

    public void createUsersWithArrayInput(List<User> body) {
        body.forEach(this::createUser);
    }

    public void createUsersWithListInput(List<User> body) {
        body.forEach(this::createUser);
    }

    public String loginUser(String username, String password) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void logoutUser() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
