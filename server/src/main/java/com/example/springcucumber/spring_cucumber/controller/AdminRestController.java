package com.example.springcucumber.spring_cucumber.controller;

import com.example.springcucumber.spring_cucumber.model.User;
import com.example.springcucumber.spring_cucumber.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
public class AdminRestController {
    private static final Logger LOG = LoggerFactory.getLogger(AdminRestController.class);

    private UserService userService;

    @Autowired
    private void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/admin")
    public ResponseEntity<?> addUser(@Valid @RequestBody String user, Errors errors) {
        ObjectMapper mapper = new ObjectMapper();
        User userDeserialization = new User();

        try {
            userDeserialization = mapper.readValue(user, User.class);

            if (userService.addUser(userDeserialization)) {
                return ResponseEntity.ok().body(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.unprocessableEntity().body(userDeserialization);
    }

    @GetMapping("/admin/all")
    public List<User> allUser() {
        return userService.getAllUsers();
    }

    @GetMapping("/admin/{id}")
    public User getUser(@Valid @ModelAttribute("id") String id, Errors errors) {
        return userService.getUserById(id);
    }

    @RequestMapping(value = "/admin/edit", method = RequestMethod.GET)
    public User editUserPage(@ModelAttribute("id") String id, ModelMap model) {
        return userService.getUserById(id);
    }

    @PutMapping(value = "/admin/{id}")
    public ResponseEntity<?> editUser(@RequestBody String user) {
        ObjectMapper mapper = new ObjectMapper();
        User userDeserialization = new User();

        try {
            userDeserialization = mapper.readValue(user, User.class);

            if (userService.updateUser(userDeserialization)) {
                return ResponseEntity.ok().body(userDeserialization);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.unprocessableEntity().body(userDeserialization);
    }

    @GetMapping(value = "/admin/by_login/{login}")
    public User getUserByLogin(@PathVariable String login) {
        return userService.getUserByLogin(login);
    }

    @DeleteMapping("/admin/{id}")
    public boolean delUser(@PathVariable String id) {
        return userService.deleteUser(id);
    }
}
