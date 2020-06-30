package com.example.springcucumber.spring_cucumber.steps;

import com.example.springcucumber.spring_cucumber.SpringCucumberApplication;
import com.example.springcucumber.spring_cucumber.model.Role;
import com.example.springcucumber.spring_cucumber.model.User;
import com.example.springcucumber.spring_cucumber.page.LoginPage;
import com.example.springcucumber.spring_cucumber.page.LogoutPage;
import com.example.springcucumber.spring_cucumber.page.MainPage;
import com.example.springcucumber.spring_cucumber.service.UserService;
import cucumber.api.java.ru.Допустим;
import cucumber.api.java.ru.Если;
import cucumber.api.java.ru.Тогда;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.sbtqa.tag.pagefactory.PageFactory;

import java.time.Duration;

@ContextConfiguration(classes = SpringCucumberApplication.class)
@PropertySource("classpath:application.properties")
@RunWith(SpringJUnit4ClassRunner.class)
public class StepsForAddUser {
    private final UserService userService;
    private final Environment env;
    private final WebDriver driver = PageFactory.getWebDriver();
    private final LoginPage loginPage = new LoginPage();
    private final LogoutPage logoutPage = new LogoutPage();
    private final MainPage mainPage = new MainPage();
    private final WebDriverWait wait = new WebDriverWait(driver, 5);
    private final FluentWait<WebDriver> fluentWait = new FluentWait<>(driver)
            .withTimeout(Duration.ofSeconds(1))
            .pollingEvery(Duration.ofMillis(200));


    @Autowired
    public StepsForAddUser(UserService userService, Environment environment) {
        this.userService = userService;
        this.env = environment;
    }

    @Допустим("^мы авторизовались под админом$")
    public void мы_авторизовались_под_админом() throws Throwable {
        driver.get(logoutPage.getUrl());
        driver.get(loginPage.getUrl());
        loginPage.authorization(
                env.getRequiredProperty("db.default.admin.login"),
                env.getRequiredProperty("db.default.admin.password")
        );
    }

    @Допустим("^зашли в меню добавления пользователя$")
    public void зашли_в_меню_добавления_пользователя() throws Throwable {
        wait.until(ExpectedConditions
                .elementToBeClickable(mainPage
                        .getAddUserForm()))
                .click();
    }

    @Если("^на сервере не существует такого же логина \"([^\"]*)\"$")
    public void на_сервере_не_существует_такого_же_логина(String arg1) throws Throwable {
        User user = userService.getUserByLogin(arg1);
        if (!user.getLogin().equals("")) {
            userService.deleteUser(user.getId().toString());
        }
    }

    @Тогда("^пользователь с логином \"([^\"]*)\" и паролем \"([^\"]*)\" добавляется корретно$")
    public void пользователь_с_логином_и_паролем_добавляется_корретно(String login, String password) throws Throwable {
        wait.until(ExpectedConditions.elementToBeClickable(mainPage.getAddFirstName()));
        mainPage.addUser("Testing",
                "Add user",
                login,
                password,
                "9999",
                "user");
        mainPage.getUserTable().click();
        fluentWait.until(driver -> {
            User user = userService.getUserByLogin(login);
            return user.getLogin().equals(login);
        });
    }

    @Если("^на сервере существует такой же логин \"([^\"]*)\" с паролем \"([^\"]*)\"$")
    public void на_сервере_существует_такой_же_логин_с_паролем(String login, String password) throws Throwable {
        if (!userService.getUserByLogin(login).getLogin().equals(login)) {
            User user = new User(
                    login,
                    password,
                    login,
                    password,
                    999L,
                    new Role("admin")
            );
            userService.addUser(user);
        }
    }

    @Тогда("^пользователь с логином \"([^\"]*)\" и паролем \"([^\"]*)\" не добавляется$")
    public void пользователь_с_логином_и_паролем_не_добавляется(String login, String password) throws Throwable {
        mainPage.getAddUserForm().click();
        wait.until(ExpectedConditions.elementToBeClickable(mainPage.getAddFirstName()));
        mainPage.addUser("Negative testing",
                "Add user",
                login,
                password,
                "999",
                "user");
        assert mainPage.getErrorInAddUser().isDisplayed();
    }
}
