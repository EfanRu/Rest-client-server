package com.example.resttemplate.service;

import com.example.resttemplate.model.User;
import com.example.resttemplate.model.UserDeserializer;
import com.example.resttemplate.model.UserSerializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import org.springframework.web.servlet.ModelAndView;

@Service
@JsonSerialize(using = UserSerializer.class)
@JsonDeserialize(using = UserDeserializer.class)
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
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        String json = "";

        module.addSerializer(User.class, new UserSerializer());
        mapper.registerModule(module);

        try {
            json = mapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return restTemplate.postForEntity(
                serverUrl + "/admin",
                json,
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
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        String json = "";

        module.addSerializer(User.class, new UserSerializer());
        mapper.registerModule(module);

        try {
            json = mapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        restTemplate.put(
                serverUrl + "/admin/" + user.getId().toString(),
                json
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
        HashMap<String, String> params = new HashMap<>();
        params.put("login", login);

        ResponseEntity<String> user = restTemplate.getForEntity(
                serverUrl + "/admin/by_login/{login}",
                String.class,
                params
        );
        if (user.getBody() == null) {
            throw new UsernameNotFoundException("User not found");
        }

        User mappedUser = new User();
        try {
            mappedUser = new ObjectMapper().readValue(user.getBody(), User.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return mappedUser;
    }
}
