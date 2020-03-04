package com.example.resttemplate.controller;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

//import org.springframework.web.servlet.ModelAndView;

//@Controller
public class AdminController {

//    private RestTemplate restTemplate;

    //    private RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());
//
//    private HttpComponentsClientHttpRequestFactory getClientHttpRequestFactory() {
//        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
//        clientHttpRequestFactory.setHttpClient(httpClient());
//        return clientHttpRequestFactory;
//    }
//
//    private HttpClient httpClient()
//    {
//        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
//
//        credentialsProvider.setCredentials(AuthScope.ANY,
//                new UsernamePasswordCredentials("admin", "admin"));
//
//        HttpClient client = HttpClientBuilder
//                .create()
//                .setDefaultCredentialsProvider(credentialsProvider)
//                .build();
//        return client;
//    }
//
//    @Autowired
//    public void restController(RestTemplateBuilder builder) {
//        builder.basicAuthentication("admin", "admin");
//        this.restTemplate = builder.build();
//    }


//    private UserServiceRest userService;
    private String url = "http://localhost:8080/";


//    @Autowired
//    private void setUserService(UserServiceRest userService) {
//        this.userService = userService;
//    }

//    @GetMapping("/admin")
//    public ResponseEntity<String> getUserList() {
//        return restTemplate.getForEntity(url, String.class);
//    }

}
