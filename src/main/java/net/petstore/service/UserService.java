package net.petstore.service;

import net.petstore.model.User;

import java.util.List;

public interface UserService {
    public void createUser(User body) ;

    public void createUsersWithArrayInput(List<User> body) ;

    public void createUsersWithListInput(List<User> body);

    public void deleteUser(String username) ;

    public User getUserByName(String username) ;

    public String loginUser(String username, String password) ;

    public void logoutUser();

    public void updateUser(String username, User body);
}