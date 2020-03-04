package com.example.springcucumber.spring_cucumber.security;

import com.example.springcucumber.spring_cucumber.model.Role;
import com.example.springcucumber.spring_cucumber.model.User;
import com.example.springcucumber.spring_cucumber.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

//@Component
//@PropertySource("classpath:application.properties")
//@Order(3)
public class DefaultUserAndAdmin {
//    @Autowired
//    private UserService userService;
//    @Autowired
//    private Environment env;
//
//    @Bean
//    public void DefaultUserAndAdmin() {
//        User user = new User();
//        user.setFirstName("Default user");
//        user.setLastName("Default user");
//        user.setPhoneNumber(99L);
//        user.setLogin(env.getProperty("db.default.user.login"));
//        user.setPassword(env.getProperty("db.default.user.password"));
//        user.setRole(new Role("user"));
//        userService.addUser(user);
//
//        User admin = new User();
//        admin.setFirstName("Default admin");
//        admin.setLastName("Default admin");
//        admin.setPhoneNumber(99L);
//        admin.setLogin(env.getProperty("db.default.admin.login"));
//        admin.setPassword(env.getProperty("db.default.admin.password"));
//        admin.setRole(new Role("admin"));
//        userService.addUser(admin);
//    }
}
