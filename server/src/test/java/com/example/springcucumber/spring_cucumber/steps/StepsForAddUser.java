package com.example.springcucumber.spring_cucumber.steps;

import com.example.springcucumber.spring_cucumber.SpringCucumberApplication;
import com.example.springcucumber.spring_cucumber.model.Role;
import com.example.springcucumber.spring_cucumber.model.User;
import com.example.springcucumber.spring_cucumber.RunTests;
import com.example.springcucumber.spring_cucumber.service.UserService;
import cucumber.api.java.ru.Допустим;
import cucumber.api.java.ru.Если;
import cucumber.api.java.ru.Тогда;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@ContextConfiguration(classes = SpringCucumberApplication.class)
@PropertySource("classpath:application.properties")
@RunWith(SpringJUnit4ClassRunner.class)
public class StepsForAddUser {
    private UserService userService;
    private Environment env;
    private WebDriver driver;

    @Autowired
    public StepsForAddUser(UserService userService, Environment environment) {
        this.userService = userService;
        this.env = environment;
        this.driver = RunTests.getDriver();
    }

    @Допустим("^мы авторизовались под админом$")
    public void мы_авторизовались_под_админом() throws Throwable {
        driver.get(env.getRequiredProperty("test.url.logout"));
        driver.get(env.getRequiredProperty("test.url.login"));
        driver.findElement(By.name("username")).sendKeys(env.getRequiredProperty("db.default.admin.login"));
        driver.findElement(By.name("password")).sendKeys(env.getRequiredProperty("db.default.admin.password"));
        driver.findElement(By.className("btn")).click();
    }

    @Допустим("^зашли в меню добавления пользователя$")
    public void зашли_в_меню_добавления_пользователя() throws Throwable {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        WebElement addForm = driver.findElement(By.id("navAddForm"));
        wait.until(ExpectedConditions.elementToBeClickable(addForm));
        addForm.click();
    }

    @Если("^на сервере не существует такого же логина \"([^\"]*)\"$")
    public void на_сервере_не_существует_такого_же_логина(String arg1) throws Throwable {
        User user = userService.getUserByLogin(arg1);
        if (!user.getLogin().equals("")) {
            userService.deleteUser(user.getId().toString());
        }
    }

    @Тогда("^пользователь с логином \"([^\"]*)\" и паролем \"([^\"]*)\" добавляется корретно$")
    public void пользователь_с_логином_и_паролем_добавляется_корретно(String arg1, String arg2) throws Throwable {
        WebElement navLinkTable = driver.findElement(By.id("navLinkTable"));
        FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(1))
                .pollingEvery(Duration.ofMillis(200));
        driver.manage().timeouts().implicitlyWait(200, TimeUnit.MILLISECONDS);
        driver.findElement(By.id("addFirstName")).sendKeys(arg1);
        driver.findElement(By.id("addLastName")).sendKeys(arg2);
        driver.findElement(By.id("addPhoneNumber")).sendKeys("999");
        driver.findElement(By.id("addLogin")).sendKeys(arg1);
        driver.findElement(By.id("addPassword")).sendKeys(arg2);
        driver.findElement(By.id("butAddUser")).submit();
        navLinkTable.click();
        wait.until(driver -> {
            User user = userService.getUserByLogin(arg1);
            return user.getLogin().equals(arg1);
        });
    }

    @Если("^на сервере существует такой же логин \"([^\"]*)\" с паролем \"([^\"]*)\"$")
    public void на_сервере_существует_такой_же_логин_с_паролем(String arg1, String arg2) throws Throwable {
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
    }

    @Тогда("^пользователь с логином \"([^\"]*)\" и паролем \"([^\"]*)\" не добавляется$")
    public void пользователь_с_логином_и_паролем_не_добавляется(String arg1, String arg2) throws Throwable {
        driver.findElement(By.id("navAddForm")).click();
        driver.manage().timeouts().implicitlyWait(200, TimeUnit.MILLISECONDS);
        driver.findElement(By.id("addFirstName")).sendKeys(arg1);
        driver.findElement(By.id("addLastName")).sendKeys(arg2);
        driver.findElement(By.id("addPhoneNumber")).sendKeys("999");
        driver.findElement(By.id("addLogin")).sendKeys(arg1);
        driver.findElement(By.id("addPassword")).sendKeys(arg2);
        driver.findElement(By.id("butAddUser")).submit();
        assert driver.findElement(By.id("errorInAddUser")).isDisplayed();
    }
}
