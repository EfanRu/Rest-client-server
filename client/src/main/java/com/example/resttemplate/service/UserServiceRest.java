package com.example.resttemplate.service;

import com.example.resttemplate.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceRest implements UserDetailsService {
    private RestTemplate restTemplate;
    private String serverUrl = "http://localhost:8080";

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
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
        return restTemplate.postForEntity(
                serverUrl + "/admin",
                user,
                User.class
        ).getBody();
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

    @Override
    public User loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = new User();
        user.setLogin("");
        user = restTemplate.getForObject(
                serverUrl + "/admin/by_login/" + login,
                User.class
        );
        return user;
    }
}
