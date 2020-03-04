package com.example.springcucumber.spring_cucumber.service;

import com.example.springcucumber.spring_cucumber.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> getAllUsers();
    boolean addUser(User u);
    boolean deleteUser(String id);
    boolean updateUser(String id, String firstName, String lastName, String phoneNumber, String role, String login, String password);
    boolean updateUser(User user);
    boolean checkAuth(String login, String password);
    User getUserById(String id);
    User getUserByLogin(String login);
}
