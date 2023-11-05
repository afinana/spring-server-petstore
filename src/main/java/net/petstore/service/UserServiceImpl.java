package net.petstore.service;

import net.petstore.model.User;
import net.petstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
