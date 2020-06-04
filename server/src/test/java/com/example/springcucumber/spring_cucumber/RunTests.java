package com.example.springcucumber.spring_cucumber;

import com.codeborne.selenide.logevents.SelenideLogger;
import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import io.qameta.allure.selenide.AllureSelenide;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/features",
        glue = "com.example.springcucumber.spring_cucumber",
        tags = {"@editUserTest"/*, "@addUserTest"*/},
        snippets = SnippetType.CAMELCASE
)
public class RunTests {
    private static ChromeDriverService service;
    private static WebDriver driver;
    private static Properties props = new Properties();

    @BeforeClass
    public static void createAndStartService() throws IOException {
        props.load(new FileInputStream(new File("src/main/resources/application.properties")));
        service = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File(props.getProperty("test.path.web.driver")))
                .usingAnyFreePort()
                .build();
        service.start();
        driver = new ChromeDriver(service);
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(false));
    }

    public static WebDriver getDriver() {
        return driver;
    }
}
