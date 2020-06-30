package com.example.springcucumber.spring_cucumber;

import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/features",
        glue = "com.example.springcucumber.spring_cucumber",
        tags = {"@editUserTest, @addUserTest"},
        snippets = SnippetType.CAMELCASE
)
public class RunTests {
//    private static ChromeDriverService service;
//    private static WebDriver driver;
//    private static Properties props = new Properties();

//    Need if don't use sbtqa framework
//    @BeforeClass
//    public static void createAndStartService() throws IOException {
//        props.load(new FileInputStream(new File("src/main/resources/application.properties")));
//        driver = Boolean
//                .parseBoolean(props
//                        .getProperty("test.driver.local")) ? getLocalDriver() : getSelenoidDriver();
//        WebDriverRunner.setWebDriver(driver);
//        SelenideLogger
//                .addListener("AllureSelenide", new AllureSelenide()
//                        .screenshots(true)
//                        .savePageSource(false));
//    }


    //Needo testing. Don't working
//    @BeforeClass
//    public static void prepareToTest() {
//        System.setProperty("webdriver.drivers.path", System.getProperty("test.path.web.driver"));
//    }

    @After
    public void tearDown() {
        WebDriverRunner.closeWebDriver();
    }

//    Need if don't use sbtqa framework
//    private static WebDriver getLocalDriver() throws IOException {
//        service = new ChromeDriverService.Builder()
//                .usingDriverExecutable(new File(props.getProperty("test.path.web.driver")))
//                .usingAnyFreePort()
//                .build();
//        service.start();
//        return new ChromeDriver(service);
//    }
//
//    private static WebDriver getSelenoidDriver() throws IOException {
//        DesiredCapabilities capabilities = new DesiredCapabilities();
//        capabilities.setBrowserName(props.getProperty("test.selenoid.browser"));
//        capabilities.setVersion(props.getProperty("test.selenoid.browser.version"));
//        capabilities.setCapability("enableVNC", true);
//        capabilities.setCapability("enableVideo", true);
//
//        return new RemoteWebDriver(
//                new URL(props.getProperty("test.selenoid.url.web.driver")),
//                capabilities
//        );
//    }

//    public static WebDriver getDriver() {
//        return driver;
//    }
}
