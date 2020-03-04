package com.example.springcucumber.spring_cucumber;

import com.example.springcucumber.spring_cucumber.model.Role;
import com.example.springcucumber.spring_cucumber.model.User;
import com.example.springcucumber.spring_cucumber.service.UserService;
import cucumber.api.PendingException;
import cucumber.api.java.ru.Допустим;
import cucumber.api.java.ru.Если;
import cucumber.api.java.ru.Тогда;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

@ContextConfiguration(classes = SpringCucumberApplication.class)
@PropertySource("classpath:application.properties")
@RunWith(SpringJUnit4ClassRunner.class)
public class MySteps {
    @Autowired
    private UserService userService;
    @Autowired
    private Environment env;
    private WebDriver driver = RunAddUserTest.driver;

    @Допустим("^мы авторизовались под админом$")
    public void мы_авторизовались_под_админом() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        try {
            driver.get("http://localhost:8080/logout");
            driver.get("http://localhost:8080/login");
            driver.findElement(By.name("username")).sendKeys(env.getRequiredProperty("db.default.admin.login"));
            driver.findElement(By.name("password")).sendKeys(env.getRequiredProperty("db.default.admin.password"));
            driver.findElement(By.className("btn")).click();
        } catch (Exception e) {
            e.printStackTrace();
            throw new PendingException();
        }
    }

    @Допустим("^зашли в меню добавления пользователя$")
    public void зашли_в_меню_добавления_пользователя() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        try {
            System.out.println("In menu add user");
        } catch (Exception e) {
            e.printStackTrace();
            throw new PendingException();
        }
    }

    @Если("^на сервере не существует такого же логина \"([^\"]*)\"$")
    public void на_сервере_не_существует_такого_же_логина(String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        try{
            User user = userService.getUserByLogin(arg1);
            if (!user.getLogin().equals("")) {
                userService.deleteUser(user.getId().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new PendingException();
        }
    }

    @Тогда("^пользователь с логином \"([^\"]*)\" и паролем \"([^\"]*)\" добавляется корретно$")
    public void пользователь_с_логином_и_паролем_добавляется_корретно(String arg1, String arg2) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        try {
            driver.findElement(By.id("navAddForm")).click();
            driver.manage().timeouts().implicitlyWait(200, TimeUnit.MILLISECONDS);
            driver.findElement(By.id("addFirstName")).sendKeys(arg1);
            driver.findElement(By.id("addLastName")).sendKeys(arg2);
            driver.findElement(By.id("addPhoneNumber")).sendKeys("999");
            driver.findElement(By.id("addLogin")).sendKeys(arg1);
            driver.findElement(By.id("addPassword")).sendKeys(arg2);
            driver.findElement(By.id("butAddUser")).submit();
            //working only thread sleep
            Thread.currentThread().sleep(200);
            driver.findElement(By.id("navLinkTable")).click();

            User user = userService.getUserByLogin(arg1);

            if (user.getLogin().equals("")) {
                throw new Exception("Exception in add user with unique login");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new PendingException();
        }
    }

    @Если("^на сервере существует такой же логин \"([^\"]*)\" с паролем \"([^\"]*)\"$")
    public void на_сервере_существует_такой_же_логин_с_паролем(String arg1, String arg2) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        try{
            if (!userService.getUserByLogin(arg1).getLogin().equals(arg1)) {
                User user = new User(
                        arg1,
                        arg2,
                        arg1,
                        arg2,
                        999L,
                        new Role("admin")
                        );
                userService.addUser(user);
            }
        } catch (Exception e) {
            throw new PendingException();
        }
    }

    @Тогда("^пользователь с логином \"([^\"]*)\" и паролем \"([^\"]*)\" не добавляется$")
    public void пользователь_с_логином_и_паролем_не_добавляется(String arg1, String arg2) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        try{
            driver.findElement(By.id("navAddForm")).click();
            driver.manage().timeouts().implicitlyWait(200, TimeUnit.MILLISECONDS);
            driver.findElement(By.id("addFirstName")).sendKeys(arg1);
            driver.findElement(By.id("addLastName")).sendKeys(arg2);
            driver.findElement(By.id("addPhoneNumber")).sendKeys("999");
            driver.findElement(By.id("addLogin")).sendKeys(arg1);
            driver.findElement(By.id("addPassword")).sendKeys(arg2);
            driver.findElement(By.id("butAddUser")).submit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new PendingException();
        }
    }
}
