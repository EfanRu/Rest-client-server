package com.example.springcucumber.spring_cucumber.Page;

import com.example.springcucumber.spring_cucumber.RunTests;
import org.openqa.selenium.WebDriver;
import org.springframework.core.env.Environment;

public class LogoutPage {
    private WebDriver driver;
    private Environment env;


    public LogoutPage() {
        this.driver = RunTests.getDriver();
    }

    public LogoutPage(Environment env) {
        this.driver = RunTests.getDriver();
        this.env = env;
    }

    public void getPageUrl() {
        driver.get(env.getRequiredProperty("test.url.logout"));
    }
}
