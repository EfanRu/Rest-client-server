package com.example.springcucumber.spring_cucumber.Page;

import com.example.springcucumber.spring_cucumber.RunTests;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.core.env.Environment;
//import ru.sbtqa.tag.pagefactory.Page;
//import ru.sbtqa.tag.pagefactory.annotations.ElementTitle;
//import ru.sbtqa.tag.pagefactory.annotations.PageEntry;


//@PageEntry(title = "Authorization")
public class LoginPage  /*implements Page*/ {
    private WebDriver driver;
    private Environment env;
//    @ElementTitle(value = "Login")
    private WebElement login = driver.findElement(By.name("username"));
//    @ElementTitle(value =  "Password")
    private WebElement pass = driver.findElement(By.name("password"));
//    @ElementTitle(value = "Confirm button")
    private WebElement confirmButton = driver.findElement(By.className("btn"));

    public LoginPage() {
        this.driver = RunTests.getDriver();
    }

    public LoginPage(Environment env) {
        this.driver = RunTests.getDriver();
        this.env = env;
    }

    public void getPageUrl() {
        driver.get(env.getRequiredProperty("test.url.login"));
    }

    public WebElement getLogin() {
        return login;
    }

    public WebElement getPass() {
        return pass;
    }

    public WebElement getConfirmButton() {
        return confirmButton;
    }
}
