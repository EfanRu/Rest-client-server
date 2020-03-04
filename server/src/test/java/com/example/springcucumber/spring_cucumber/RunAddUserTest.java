package com.example.springcucumber.spring_cucumber;

import com.example.springcucumber.spring_cucumber.dao.UserDao;
import com.example.springcucumber.spring_cucumber.model.User;
import com.example.springcucumber.spring_cucumber.service.UserService;
import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.io.IOException;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/features",
        glue = "com.example.springcucumber.spring_cucumber",
//        tags = "@add",
        snippets = SnippetType.CAMELCASE
)
@SpringBootTest
public class RunAddUserTest {
    private static ChromeDriverService service;
    protected static WebDriver driver;

    @BeforeClass
    public static void createAndStartService() throws IOException {
        service = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File("C:\\Users\\tanch\\Downloads\\chromedriver_win32 (1)\\chromedriver.exe"))
                .usingAnyFreePort()
                .build();
        service.start();
        driver = new ChromeDriver(service);
    }
}
