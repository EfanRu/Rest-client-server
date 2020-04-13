package com.example.springcucumber.spring_cucumber.steps;

import com.example.springcucumber.spring_cucumber.SpringCucumberApplication;
import com.example.springcucumber.spring_cucumber.model.User;
import com.example.springcucumber.spring_cucumber.RunTests;
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

import java.time.Duration;

//Use only css selectors and xpath with text locators for education
@ContextConfiguration(classes = SpringCucumberApplication.class)
@PropertySource("classpath:application.properties")
@RunWith(SpringJUnit4ClassRunner.class)
public class StepsForEditUser {
    private Environment env;
    private WebDriver driver;
    private UserService userService;
    private FluentWait<WebDriver> fluentWait;
    private WebDriverWait wait;

    @Autowired
    public StepsForEditUser(Environment env, UserService userService) {
        this.env = env;
        this.driver = RunTests.getDriver();
        this.userService = userService;
        this.fluentWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(5))
                .pollingEvery(Duration.ofMillis(200))
                .ignoring(ElementNotInteractableException.class, AssertionError.class)
                .ignoring(NoSuchElementException.class);
        this.wait = new WebDriverWait(driver, 3, 200);
    }


    @Допустим("^мы авторизовались с ролью админа$")
    public void мы_авторизовались_с_ролью_админа() throws Throwable {
        driver.get(env.getRequiredProperty("test.url.logout"));
        driver.get(env.getRequiredProperty("test.url.login"));
        driver.findElement(By.cssSelector("form > input:nth-child(1)")).sendKeys(env.getRequiredProperty("db.default.admin.login"));
        driver.findElement(By.cssSelector("form > input:nth-child(2)")).sendKeys(env.getRequiredProperty("db.default.admin.password"));
        driver.findElement(By.cssSelector("form > button:nth-child(3)")).click();
    }

    @Допустим("^добавили пользователя с именем \"([^\"]*)\", фамилией \"([^\"]*)\", логином \"([^\"]*)\", паролем \"([^\"]*)\", телефоном \"([^\"]*)\", ролью \"([^\"]*)\"\\.$")
    public void добавили_пользователя_с_именем_фамилией_логином_паролем_телефоном_ролью(String arg1, String arg2, String arg3, String arg4, String arg5, String arg6) throws Throwable {
        WebElement newUserForm = driver.findElement(By.cssSelector(".nav-item[id='navAddForm']"));
        WebElement inputFirstName = driver.findElement(By.cssSelector(".input-lg[name='firstName']"));
        WebElement inputLastName = driver.findElement(By.cssSelector(".input-lg[name='lastName']"));
        WebElement inputPhoneNumber = driver.findElement(By.cssSelector(".input-lg[name='phoneNumber']"));
        WebElement inputRole;
        WebElement inputLogin = driver.findElement(By.cssSelector(".input-lg[name='login']"));
        WebElement inputPassword = driver.findElement(By.cssSelector(".input-lg[name='password']"));

        wait.until(ExpectedConditions.elementToBeClickable(newUserForm));
        newUserForm.click();

        wait.until(ExpectedConditions.elementToBeClickable(inputFirstName));

        if (arg6.equalsIgnoreCase("admin")) {
            inputRole = driver.findElement(By.cssSelector("select > option:nth-child(1)"));
        } else {
            inputRole = driver.findElement(By.cssSelector("select > option:nth-child(2)"));
        }

        inputFirstName.sendKeys(arg1);
        inputLastName.sendKeys(arg2);
        inputLogin.sendKeys(arg3);
        inputPassword.sendKeys(arg4);
        inputPhoneNumber.sendKeys(arg5);
        inputRole.click();

        driver.findElement(By.cssSelector("button[id='butAddUser']")).click();

        fluentWait.until(driver -> userService.getUserByLogin(arg3).getLogin().equals(arg3));
    }

    @Если("^на сервере имеется запись с логином \"([^\"]*)\"$")
    public void на_сервере_имеется_запись_с_логином(String arg1) throws Throwable {
        if (userService.getUserByLogin(arg1).equals("")) {
            throw new NoSuchElementException("Test element not present in DB");
        }
    }

    @И("^отсутствует запись с логином \"([^\"]*)\"$")
    public void отсутствуетЗаписьСЛогином(String arg0) throws Throwable {
        User user = userService.getUserByLogin(arg0);
        if (user.getLogin().equals(arg0)) {
            userService.deleteUser(user.getId().toString());
        }
    }

    @То("^вносим следующие изменения в запись с логином \"([^\"]*)\": имя \"([^\"]*)\", фамилия \"([^\"]*)\", логином \"([^\"]*)\", паролем \"([^\"]*)\", телефоном \"([^\"]*)\", ролью \"([^\"]*)\"$")
    public void вносим_следующие_изменения_в_запись_с_логином_имя_фамилия_логином_паролем_телефоном_ролью(String arg1, String arg2, String arg3, String arg4, String arg5, String arg6, String arg7) throws Throwable {
        WebElement table = driver.findElement(By.xpath("//a[contains(text(),'s table')]"));
        WebElement editButton = driver.findElement(By.xpath("(//td[contains(text(),'" + arg1 + "')]/following-sibling::td//button[contains(text(), 'edit')])[1]"));
        WebElement changedLogin = driver.findElement(By.xpath("//td[contains(text(),'" + arg4 + "')]"));

        table.click();
        fluentWait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("(//td[contains(text(),'" + arg1 + "')]/following-sibling::td//button[contains(text(), 'edit')])[1]"))));
        editButton.click();
        fluentWait.until(driver -> driver
                .findElement(By.xpath("(//div//b[text()='First name:']/following-sibling::input)[1]"))
                .isDisplayed());

        WebElement firstNameInput = driver.findElement(By.xpath("(//div//b[text()='First name:']/following-sibling::input)[1]"));
        WebElement lastNameInput = driver.findElement(By.xpath("(//div//b[text()='Last name:']/following-sibling::input)[1]"));
        WebElement phoneNumberInput = driver.findElement(By.xpath("(//div//b[text()='Phone number:']/following-sibling::input)[1]"));
        WebElement roleInput;
        WebElement loginInput = driver.findElement(By.xpath("(//div//b[text()='Login:']/following-sibling::input)[1]"));
        WebElement passwordInput = driver.findElement(By.xpath("(//div//b[text()='Password:']/following-sibling::input)[1]"));
        WebElement sumbitChangeButton = driver.findElement(By.xpath("(//button[text()='Change'])[1]"));

        firstNameInput.clear();
        firstNameInput.sendKeys(arg2);
        lastNameInput.clear();
        lastNameInput.sendKeys(arg3);
        loginInput.clear();
        loginInput.sendKeys(arg4);
        passwordInput.sendKeys(arg5);
        phoneNumberInput.clear();
        phoneNumberInput.sendKeys(arg6);
        if (arg7.equalsIgnoreCase("admin")) {
            roleInput = driver.findElement(By.xpath("//select//option[@value='admin']"));
        } else {
            roleInput = driver.findElement(By.xpath("//select//option[@value='user']"));
        }
        roleInput.click();
        sumbitChangeButton.click();

        assert changedLogin.isDisplayed();
    }

    @Тогда("^в UI и DB будет отображены изменения: имя \"([^\"]*)\", фамилия \"([^\"]*)\", логином \"([^\"]*)\", паролем \"([^\"]*)\", телефоном \"([^\"]*)\", ролью \"([^\"]*)\"$")
    public void в_UI_и_DB_будет_отображены_изменения_имя_фамилия_логином_паролем_телефоном_ролью(String arg1, String arg2, String arg3, String arg4, String arg5, String arg6) throws Throwable {
        fluentWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//td[text()='" + arg1 + "']")));
        assert driver.findElement(By.xpath("//td[text()='" + arg1 + "']")).isDisplayed();
        assert driver.findElement(By.xpath("//td[text()='" + arg2 + "']")).isDisplayed();
        assert driver.findElement(By.xpath("//td[text()='" + arg3 + "']")).isDisplayed();
        assert driver.findElement(By.xpath("//td[text()='" + arg5 + "']")).isDisplayed();
        assert driver.findElement(By.xpath("//td[text()='" + arg6 + "']")).isDisplayed();
    }

    @То("^вносим следующие изменения с существующим в DB логином в запись с логином \"([^\"]*)\": имя \"([^\"]*)\", фамилия \"([^\"]*)\", логином \"([^\"]*)\", паролем \"([^\"]*)\", телефоном \"([^\"]*)\", ролью \"([^\"]*)\"$")
    public void вносим_следующие_изменения_с_существующим_в_DB_логином_в_запись_с_логином_имя_фамилия_логином_паролем_телефоном_ролью(String arg1, String arg2, String arg3, String arg4, String arg5, String arg6, String arg7) throws Throwable {
        WebElement table = driver.findElement(By.xpath("//a[contains(text(),'s table')]"));
        WebElement editButton = driver.findElement(By.xpath("(//td[contains(text(),'" + arg1 + "')]/following-sibling::td//button[contains(text(), 'edit')])[1]"));

        table.click();
        fluentWait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("(//td[contains(text(),'" + arg1 + "')]/following-sibling::td//button[contains(text(), 'edit')])[1]"))));
        editButton.click();

        fluentWait.until(driver -> driver
                .findElement(By.xpath("(//div//b[text()='First name:']/following-sibling::input)[1]")))
                .isDisplayed();

        WebElement firstNameInput = driver.findElement(By.xpath("(//div//b[text()='First name:']/following-sibling::input)[1]"));
        WebElement lastNameInput = driver.findElement(By.xpath("(//div//b[text()='Last name:']/following-sibling::input)[1]"));
        WebElement phoneNumberInput = driver.findElement(By.xpath("(//div//b[text()='Phone number:']/following-sibling::input)[1]"));
        WebElement roleInput;
        WebElement loginInput = driver.findElement(By.xpath("(//div//b[text()='Login:']/following-sibling::input)[1]"));
        WebElement passwordInput = driver.findElement(By.xpath("(//div//b[text()='Password:']/following-sibling::input)[1]"));
        WebElement sumbitChangeButton = driver.findElement(By.xpath("(//button[text()='Change'])[1]"));

        firstNameInput.clear();
        firstNameInput.sendKeys(arg2);
        lastNameInput.clear();
        lastNameInput.sendKeys(arg3);
        loginInput.clear();
        loginInput.sendKeys(arg4);
        passwordInput.sendKeys(arg5);
        phoneNumberInput.clear();
        phoneNumberInput.sendKeys(arg6);
        if (arg7.equalsIgnoreCase("admin")) {
            roleInput = driver.findElement(By.xpath("//select//option[@value='admin']"));
        } else {
            roleInput = driver.findElement(By.xpath("//select//option[@value='user']"));
        }
        roleInput.click();
        sumbitChangeButton.click();
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
