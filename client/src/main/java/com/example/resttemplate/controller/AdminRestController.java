package com.example.resttemplate.controller;

import com.example.resttemplate.model.User;
import com.example.resttemplate.service.UserServiceRest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class AdminRestController {
    private static final Logger LOG = LoggerFactory.getLogger(AdminRestController.class);

    private UserServiceRest userService;

    @Autowired
    private void setUserService(UserServiceRest userService) {
        this.userService = userService;
    }

    @PostMapping("/admin")
    public ResponseEntity<?> addUser(@Valid @RequestBody User user, Errors errors) {
        if (userService.addUser(user) != null) {
            return ResponseEntity.ok().body(user);
        } else {
            return ResponseEntity.unprocessableEntity().body(user);
        }
    }

    @GetMapping("/admin/all")
    public List<?> allUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/admin/{id}")
    public Object getUser(@Valid @ModelAttribute("id") String id, Errors errors) {
        return userService.getUserById(id);
    }

    @RequestMapping(value = "/admin/edit", method = RequestMethod.GET)
    public Object editUserPage(@ModelAttribute("id") String id, ModelMap model) {
        return userService.getUserById(id);
    }

    @PutMapping(value = "/admin/{id}")
    public Object editUser(@RequestBody User user) {
        userService.updateUser(user);
        return user;
    }

    @DeleteMapping("/admin/{id}")
    public void delUser(@PathVariable String id) {
        userService.deleteUser(id);
    }
}
