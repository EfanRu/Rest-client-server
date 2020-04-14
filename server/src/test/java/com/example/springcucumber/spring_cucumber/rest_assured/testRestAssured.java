package com.example.springcucumber.spring_cucumber.rest_assured;

import com.example.springcucumber.spring_cucumber.SpringCucumberApplication;
import com.example.springcucumber.spring_cucumber.model.Role;
import com.example.springcucumber.spring_cucumber.model.User;
import com.example.springcucumber.spring_cucumber.service.UserService;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.basic;
import static io.restassured.RestAssured.given;

@ContextConfiguration(classes = SpringCucumberApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class testRestAssured {
    private static Properties prop = new Properties();
    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    private User testUser = new User(
            "Test first name",
            "Test last name",
            "Test login",
            "Test pass",
            999L,
            new Role("user")
    );
    private User editUser = new User(
            "Test edit user",
            "Test edit user",
            "Test edit user",
            "Test edit user",
            111L,
            new Role("admin")
    );

        @BeforeClass
        public static void setup() throws IOException {
            prop.load(new FileInputStream(new File("src/main/resources/application.properties")));
            RestAssured.port = Integer.parseInt(prop.getProperty("test.server.port"));
            RestAssured.baseURI = prop.getProperty("test.server.base.host");
            RestAssured.authentication = basic(prop.getProperty("test.server.login"), prop.getProperty("test.server.password"));
        }

        @Test
        public void testAddUser() throws JSONException {
            Response resp = given()
                    .contentType("application/json")
                    .body(testUser)
                    .when().post("/admin");
            resp.then().statusCode(200);
            User dbUser = userService.getUserByLogin("Test login");
            assert dbUser.getLogin().equals(testUser.getLogin());
            assert passwordEncoder.matches(testUser.getPassword(), dbUser.getPassword());
            assert dbUser.getFirstName().equals(testUser.getFirstName());
            assert dbUser.getLastName().equals(testUser.getLastName());
            assert dbUser.getPhoneNumber().equals(testUser.getPhoneNumber());
            assert dbUser.getRole().getName().equalsIgnoreCase(testUser.getRole().getName());
        }

        @Test
        public void testEditUser() {
            User userDB = userService.getUserByLogin(testUser.getLogin());
            assert userDB.getLogin().equals("");

            given()
                    .contentType("application/json")
                    .body(editUser)
                    .when().put("/admin/" + userDB.getId())
                    .then().statusCode(200);
            User userUpdate = userService.getUserByLogin(editUser.getLogin());
            assert userUpdate.getLogin().equals(editUser.getLogin());
            assert passwordEncoder.matches(editUser.getPassword(), userUpdate.getPassword());
            assert userUpdate.getFirstName().equals(editUser.getFirstName());
            assert userUpdate.getLastName().equals(editUser.getLastName());
            assert userUpdate.getPhoneNumber().equals(editUser.getPhoneNumber());
            assert userUpdate.getRole().getName().equalsIgnoreCase(editUser.getRole().getName());
        }

        @Test
        public void deleteUserTest() {
            User userDB = userService.getUserByLogin(editUser.getLogin());
            assert userDB.getLogin().equals(editUser.getLogin());

            given()
                    .contentType("application/json")
                    .when().delete("/admin/" + userDB.getId())
                    .then().statusCode(200);

            assert userService.getUserByLogin(editUser.getLogin()).getLogin().equals("");
        }
}
