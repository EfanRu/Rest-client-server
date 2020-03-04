package com.example.resttemplate.controller;

import com.example.resttemplate.model.User;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.ModelAndView;

import java.nio.charset.Charset;
import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller
public class LoginController {
    private String url = "http://localhost:8080/";

    private RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());

    private HttpComponentsClientHttpRequestFactory getClientHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setHttpClient(httpClient());
        return clientHttpRequestFactory;
    }

    private HttpClient httpClient()
    {
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();

        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials("admin", "admin"));

        HttpClient client = HttpClientBuilder
                .create()
                .setDefaultCredentialsProvider(credentialsProvider)
                .build();
        return client;
    }
//
//    private RestTemplate restTemplate;
//
//    @Autowired
//    public void restController(RestTemplateBuilder builder) {
//        builder.basicAuthentication("admin", "admin");
//        this.restTemplate = builder.build();
//    }

    @GetMapping({"/", "/login"})
    public ModelAndView login() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("user");
        return mav;
    }

    @GetMapping("/error")
    public ModelAndView error() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("error");
        return mav;
    }

    @GetMapping("/admin")
    public ModelAndView admin() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("adminAll");
        return mav;
    }

    @GetMapping("/admin/all")
    public ResponseEntity<String> adminAll() {
        return restTemplate.getForEntity(url + "/admin/all", String.class);
    }


    //
//    @GetMapping("/authorization")
//    public ResponseEntity<String> authorization() {
//        return restTemplate(url, String.class);
//    }

//
//    @PostMapping("/authorization")
//    public ResponseEntity<String> authorization(Model model) {
//        HttpHeaders headers = new HttpHeaders();
//
//
////        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
////
////        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
////        map.add("login", "admin");
////        map.add("password", "admin");
////
////        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
//
//        return restTemplate.postForEntity( url, new HttpEntity<String>(createHeaders("admin", "admin")) , String.class );
//    }

//    HttpHeaders createHeaders(String username, String password){
//        return new HttpHeaders() {{
//            String auth = username + ":" + password;
//            byte[] encodedAuth = Base64.encodeBase64(
//                    auth.getBytes(Charset.forName("US-ASCII")) );
//            String authHeader = "Basic " + new String( encodedAuth );
//            set( "Authorization", authHeader );
//        }};
//    }
}
