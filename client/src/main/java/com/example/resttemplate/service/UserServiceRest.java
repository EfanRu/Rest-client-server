package com.example.resttemplate.service;

import com.example.resttemplate.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
//import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceRest /*implements UserDetailsService*/ {
    private RestOperations restOperations;
    private String serverUrl = "http://localhost:8080";

    @Autowired
    public void setRestOperations(RestTemplate restOperations) {
        this.restOperations = restOperations;
    }

    public List<User> getAllUsers() {
        return restOperations.exchange(
                serverUrl + "/admin/all",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<User>>() {
                }
        ).getBody();
    }

    public User addUser(User user) {
        return restOperations.postForObject(
                serverUrl + "/admin",
                user,
                User.class
        );
    }

    public User getUserById(String id) {
        Map<String, String> params = new HashMap<>();
        params.put("id", id);
        return restOperations.getForObject(
                serverUrl + "/admin/" + id,
                User.class,
                params
        );
    }

    public void updateUser(User user) {
        restOperations.put(
                serverUrl + "/admin/" + user.getId().toString(),
                user
        );
    }

    public void deleteUser(String id) {
        HashMap<String, String> params = new HashMap<>();
        params.put("id", id);

        restOperations.delete(
                serverUrl + "/admin/" + id,
                params
        );
    }

//    @Override
//    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
//        HashMap<String, String> params = new HashMap<>();
//        params.put("login", login);
//
//        User user = restOperations.getForObject(
//                serverUrl + "/admin/by_login/{login}",
//                User.class,
//                params
//        );
//        if (user == null) {
//            throw new UsernameNotFoundException("User not found");
//        }
//        return user;
//    }
}
