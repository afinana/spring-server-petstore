package net.petstore.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.petstore.model.User;
import net.petstore.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements  UserService{

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    ObjectMapper objectMapper;


    @Autowired
    UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;


    public void createUser(User body) {
        try{
            //  save the user to the database
            net.petstore.domain.User userDTO = modelMapper.map(body,  net.petstore.domain.User.class);
            String userJson = objectMapper.writeValueAsString(userDTO);
            String routingKey = "users-add.key";
            rabbitTemplate.convertAndSend("user-exchange", routingKey, userJson);

            log.info("Sent INSERT message with key {}: {}", routingKey, userJson);

        } catch (Exception e) {
          throw new RuntimeException("Failed to delete pet", e);
        }

}


    public void deleteUser( String username) {
        //  find the user by username and delete it
        //  save the user to the database
        String routingKey = "users-delete.key";
        rabbitTemplate.convertAndSend("user-exchange", routingKey, username);

        log.info("Sent DELETE message with key {} : {}", routingKey, username);


    }

    public User getUserByName( String username) {
        net.petstore.domain.User byUsername = userRepository.findByUsername(username);
        return modelMapper.map(byUsername, User.class);
        
    }

    public void updateUser( String username,User body) {

        try {
            //  save the user to the database
            net.petstore.domain.User userDTO = modelMapper.map(body,  net.petstore.domain.User.class);
            String userJson = objectMapper.writeValueAsString(userDTO);
            String routingKey = "users-add.key";
            rabbitTemplate.convertAndSend("user-exchange", routingKey, userJson);

            log.info("Sent INSERT message with key {}}: {}}", routingKey, userJson);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete pet", e);
        }

    }

    public void createUsersWithArrayInput(List<User> body) {
        // use a stream to save all the users in the list
        body.forEach(this::createUser);

    }

    public void createUsersWithListInput(List<User> body) {

        createUsersWithArrayInput(body);
    }


    public String loginUser( String username,  String password) {
        throw new java.lang.UnsupportedOperationException("Not supported yet.");
    }

    public void logoutUser() {
        throw new java.lang.UnsupportedOperationException("Not supported yet.");
    }

}
