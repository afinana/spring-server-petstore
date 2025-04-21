package net.petstore.service;

import lombok.extern.slf4j.Slf4j;
import net.petstore.model.User;
import net.petstore.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements  UserService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;


    public void createUser(User body) {
        // log request
        log.info("createUser: {}", body);
        // convert user model to user entity
        net.petstore.domain.User user = modelMapper.map(body, net.petstore.domain.User.class);
        // save user entity
        userRepository.save(user);

    }


    public void deleteUser( String username) {
        log.info("deleteUser: {}", username);
        net.petstore.domain.User user = userRepository.findByUsername(username);
        if (user != null) {
            userRepository.delete(user);
        }

    }

    public User getUserByName( String username) {
        log.info("getUserByName: {}", username);


        net.petstore.domain.User byUsername = userRepository.findByUsername(username);
        // convert user entity to user model
        return modelMapper.map(byUsername, User.class);

    }

    public void updateUser( String username,User body) {
        log.info("updateUser: {}", username);
        // find user by username
        net.petstore.domain.User user = userRepository.findByUsername(username);
        if (user != null) {
            // update user entity
            user.setFirstName(body.getFirstName());
            user.setLastName(body.getLastName());
            user.setEmail(body.getEmail());
            user.setPassword(body.getPassword());
            userRepository.save(user);
        }
    }

    public void createUsersWithArrayInput(List<User> body) {
        // save all users
        for (User user: body) {
            createUser(user);
        }
    }

    public void createUsersWithListInput(List<User> body) {
        // save all users
        for (User user: body) {
            createUser(user);
        }
    }


    public String loginUser( String username,  String password) {
        throw new java.lang.UnsupportedOperationException("Not supported yet.");
    }

    public void logoutUser() {
        throw new java.lang.UnsupportedOperationException("Not supported yet.");
    }

}
