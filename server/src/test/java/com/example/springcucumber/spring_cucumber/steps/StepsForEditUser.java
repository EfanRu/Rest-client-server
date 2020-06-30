package com.example.springcucumber.spring_cucumber.steps;

import com.example.springcucumber.spring_cucumber.SpringCucumberApplication;
import com.example.springcucumber.spring_cucumber.model.User;
import com.example.springcucumber.spring_cucumber.page.LoginPage;
import com.example.springcucumber.spring_cucumber.page.LogoutPage;
import com.example.springcucumber.spring_cucumber.page.MainPage;
import com.example.springcucumber.spring_cucumber.service.UserService;
import cucumber.api.java.ru.*;
import org.junit.runner.RunWith;
import org.openqa.selenium.*;
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
public class StepsForEditUser {
    private final Environment env;
    private final WebDriver driver = PageFactory.getWebDriver();
    private final UserService userService;
    private final FluentWait<WebDriver> fluentWait = new FluentWait<>(driver)
            .withTimeout(Duration.ofSeconds(5))
            .pollingEvery(Duration.ofMillis(200))
            .ignoring(ElementNotInteractableException.class, AssertionError.class)
            .ignoring(NoSuchElementException.class);
    private final WebDriverWait wait = new WebDriverWait(driver, 3, 200);
    private final LoginPage loginPage = new LoginPage();
    private final LogoutPage logoutPage = new LogoutPage();
    private final MainPage mainPage = new MainPage();

    @Autowired
    public StepsForEditUser(Environment env, UserService userService) {
        this.env = env;
        this.userService = userService;
    }


    @Допустим("^мы авторизовались с ролью админа$")
    public void мы_авторизовались_с_ролью_админа() throws Throwable {
        driver.get(logoutPage.getUrl());
        driver.get(loginPage.getUrl());
        loginPage.authorization(
                env.getRequiredProperty("db.default.admin.login"),
                env.getRequiredProperty("db.default.admin.password")
        );
    }

    @Допустим("^добавили пользователя с именем \"([^\"]*)\", фамилией \"([^\"]*)\", логином \"([^\"]*)\", паролем \"([^\"]*)\", телефоном \"([^\"]*)\", ролью \"([^\"]*)\"\\.$")
    public void добавили_пользователя_с_именем_фамилией_логином_паролем_телефоном_ролью(String firstName, String lastName, String login, String password, String phoneNumber, String role) throws Throwable {
        wait.until(ExpectedConditions.elementToBeClickable(mainPage.getAddUserForm()));
        mainPage.getAddUserForm().click();
        wait.until(ExpectedConditions.elementToBeClickable(mainPage.getAddFirstName()));
        mainPage.addUser(firstName, lastName, login, password, phoneNumber, role);
        fluentWait.until(driver -> userService
                .getUserByLogin(login)
                .getLogin()
                .equals(login));
    }

    @Если("^на сервере имеется запись с логином \"([^\"]*)\"$")
    public void на_сервере_имеется_запись_с_логином(String login) throws Throwable {
        if (userService.getUserByLogin(login).equals("")) {
            throw new NoSuchElementException("Test element not present in DB");
        }
    }

    @И("^отсутствует запись с логином \"([^\"]*)\"$")
    public void отсутствуетЗаписьСЛогином(String login) throws Throwable {
        User user = userService.getUserByLogin(login);
        if (user.getLogin().equals(login)) {
            userService.deleteUser(user.getId().toString());
        }
    }

    @То("^вносим следующие изменения в запись с логином \"([^\"]*)\": имя \"([^\"]*)\", фамилия \"([^\"]*)\", логином \"([^\"]*)\", паролем \"([^\"]*)\", телефоном \"([^\"]*)\", ролью \"([^\"]*)\"$")
    public void вносим_следующие_изменения_в_запись_с_логином_имя_фамилия_логином_паролем_телефоном_ролью(String loginForChanged, String firstName, String lastName, String login, String password, String phoneNumber, String role) throws Throwable {
        WebElement editButton = driver.findElement(By.xpath("(//td[contains(text(),'" + loginForChanged + "')]/following-sibling::td//button[contains(text(), 'edit')])[1]"));
        WebElement changedLogin = driver.findElement(By.xpath("//td[contains(text(),'" + login + "')]"));

        mainPage.getUserTable().click();
        fluentWait.until(ExpectedConditions
                .elementToBeClickable(driver
                        .findElement(By
                                .xpath("(//td[contains(text(),'" + loginForChanged + "')]/following-sibling::td//button[contains(text(), 'edit')])[1]"))));
        editButton.click();
        fluentWait.until(driver -> mainPage
                .getEditMenuOpened()
                .isDisplayed());

        mainPage.editUser(firstName, lastName, login, password, phoneNumber, role);

        assert changedLogin.isDisplayed();
    }

    @Тогда("^в UI и DB будет отображены изменения: имя \"([^\"]*)\", фамилия \"([^\"]*)\", логином \"([^\"]*)\", паролем \"([^\"]*)\", телефоном \"([^\"]*)\", ролью \"([^\"]*)\"$")
    public void в_UI_и_DB_будет_отображены_изменения_имя_фамилия_логином_паролем_телефоном_ролью(String firstName, String lastName, String login, String password, String phoneNumber, String role) throws Throwable {
        fluentWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//td[text()='" + firstName + "']")));
        assert driver.findElement(By.xpath("//td[text()='" + firstName + "']")).isDisplayed() &&
                driver.findElement(By.xpath("//td[text()='" + lastName + "']")).isDisplayed() &&
                driver.findElement(By.xpath("//td[text()='" + login + "']")).isDisplayed() &&
                driver.findElement(By.xpath("//td[text()='" + phoneNumber + "']")).isDisplayed() &&
                driver.findElement(By.xpath("//td[text()='" + role + "']")).isDisplayed();
    }

    @То("^вносим следующие изменения с существующим в DB логином в запись с логином \"([^\"]*)\": имя \"([^\"]*)\", фамилия \"([^\"]*)\", логином \"([^\"]*)\", паролем \"([^\"]*)\", телефоном \"([^\"]*)\", ролью \"([^\"]*)\"$")
    public void вносим_следующие_изменения_с_существующим_в_DB_логином_в_запись_с_логином_имя_фамилия_логином_паролем_телефоном_ролью(String loginForChanged, String firstName, String lastName, String login, String password, String phoneNumber, String role) throws Throwable {
        WebElement editButton = driver.findElement(By.xpath("(//td[contains(text(),'" + loginForChanged + "')]/following-sibling::td//button[contains(text(), 'edit')])[1]"));

        mainPage.getUserTable().click();
        fluentWait.until(ExpectedConditions
                .elementToBeClickable(driver
                        .findElement(By
                                .xpath("(//td[contains(text(),'" + loginForChanged + "')]/following-sibling::td//button[contains(text(), 'edit')])[1]"))));
        editButton.click();

        fluentWait.until(driver -> driver
                .findElement(By
                        .xpath("(//div//b[text()='First name:']/following-sibling::input)[1]")))
                .isDisplayed();

        mainPage.editUser(firstName, lastName, login, password, phoneNumber, role);
    }

    @Тогда("^должно появиться сообщение об ошибке из-за наличия в DB аналогичного логина$")
    public void должно_появиться_сообщение_об_ошибке_из_за_наличия_в_DB_аналогичного_логина() throws Throwable {
        fluentWait.until(driver -> driver
                .findElement(By.xpath("//a[text()='Ошибка в редактировании пользователя. Логин должен быть уникальным. Проверьте вводимые данные.']"))
                .isDisplayed());
    }

    @Тогда("^должно появиться сообщение об ошибке из-за некоректного номера телефона$")
    public void должно_появиться_сообщение_об_ошибке_из_за_некоректного_номера_телефона() throws Throwable {
        fluentWait.until(driver -> driver
                .findElement(By.xpath("//a[text()='Ошибка в редактировании пользователя. Логин должен быть уникальным. Проверьте вводимые данные.']"))
                .isDisplayed());
    }

    @Тогда("^должно появиться сообщение об ошибке пустого логина$")
    public void должно_появиться_сообщение_об_ошибке_пустого_логина() throws Throwable {
        fluentWait.until(driver -> driver
                .findElement(By.xpath("//a[text()='Ошибка в редактировании пользователя. Логин должен быть уникальным. Проверьте вводимые данные.']"))
                .isDisplayed());
    }
}
