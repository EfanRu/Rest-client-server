package com.example.resttemplate.service;

import com.example.resttemplate.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
//import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceRest {
    private final RestOperations restTemplate;
    private String serverUrl = "http://localhost:8080";

    @Autowired
    public UserServiceRest(RestOperations restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<User> getAllUsers() {
        return restTemplate.exchange(
                serverUrl + "/admin/all",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<User>>() {
                }
        ).getBody();
    }

    public User addUser(User user) {
        return restTemplate.postForObject(
                serverUrl + "/admin/" + user.getId().toString(),
                user,
                User.class
        );
    }

    public User getUserById(String id) {
        Map<String, String> params = new HashMap<>();
        params.put("id", id);
        return restTemplate.getForObject(
                serverUrl + "/admin/" + id,
                User.class,
                params
        );
    }

    public void updateUser(User user) {
        restTemplate.put(
                serverUrl + "/admin/" + user.getId().toString(),
                user
        );
    }

    public void deleteUser(String id) {
        HashMap<String, String> params = new HashMap<>();
        params.put("id", id);

        restTemplate.delete(
                serverUrl + "/admin/" + id,
                params
        );
    }
}
