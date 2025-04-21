package net.petstore.service;

import net.petstore.model.User;

import java.util.List;

public interface UserService {
     void createUser(User body) ;

     void createUsersWithArrayInput(List<User> body) ;

     void createUsersWithListInput(List<User> body);

     void deleteUser(String username) ;

     User getUserByName(String username) ;

     String loginUser(String username, String password) ;

     void logoutUser();

     void updateUser(String username, User body);
}