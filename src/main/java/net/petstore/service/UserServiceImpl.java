package net.petstore.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import net.petstore.model.User;
import net.petstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

@Service
public class UserServiceImpl implements  UserService{

    @Autowired
    UserRepository userRepository;


    public void createUser(User body) {

    }


    public void deleteUser( String username) {

    }

    public User getUserByName( String username) {
        return null;
    }

    public void updateUser( String username,User body) {

    }

    public void createUsersWithArrayInput(List<User> body) {
        throw new java.lang.UnsupportedOperationException("Not supported yet.");
    }

    public void createUsersWithListInput(List<User> body) {
        throw new java.lang.UnsupportedOperationException("Not supported yet.");
    }


    public String loginUser( String username,  String password) {
        throw new java.lang.UnsupportedOperationException("Not supported yet.");
    }

    public void logoutUser() {
        throw new java.lang.UnsupportedOperationException("Not supported yet.");
    }

}
