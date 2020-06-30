package com.example.springcucumber.spring_cucumber.page;

import com.example.springcucumber.spring_cucumber.RunTests;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.core.env.Environment;
import ru.sbtqa.tag.pagefactory.Page;
import ru.sbtqa.tag.pagefactory.PageFactory;
import ru.sbtqa.tag.pagefactory.annotations.ElementTitle;
import ru.sbtqa.tag.pagefactory.annotations.PageEntry;


@PageEntry(title = "Authorization page", url = "http://localhost:8081/login")
public class LoginPage extends Page {
    @ElementTitle(value = "Login")
    @FindBy(name = "username")
    private WebElement login;
    @ElementTitle(value = "Password")
    @FindBy(name = "password")
    private WebElement pass;
    @ElementTitle(value = "Confirm button")
    @FindBy(className = "btn")
    private WebElement confirmButton;

    public LoginPage() {
        PageFactory.initElements(PageFactory.getWebDriver(),this);
    }

    public void authorization(String userLogin, String userPassword) {
        login.sendKeys(userLogin);
        pass.sendKeys(userPassword);
        confirmButton.click();
    }

    public String getUrl() {
        return ((PageEntry)this.getClass().getAnnotation(PageEntry.class)).url();
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
