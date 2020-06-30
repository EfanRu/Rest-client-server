package com.example.springcucumber.spring_cucumber.page;

import com.example.springcucumber.spring_cucumber.RunTests;
import org.openqa.selenium.WebDriver;
import org.springframework.core.env.Environment;
import ru.sbtqa.tag.pagefactory.Page;
import ru.sbtqa.tag.pagefactory.annotations.PageEntry;

@PageEntry(title = "Logout page", url = "http://localhost:8081/logout")
public class LogoutPage extends Page {

    public String getUrl() {
        return ((PageEntry)this.getClass().getAnnotation(PageEntry.class)).url();
    }


//    private WebDriver driver;
//    private Environment env;


//    public LogoutPage() {
//        this.driver = RunTests.getDriver();
//    }

//    public LogoutPage(Environment env) {
//        this.driver = RunTests.getDriver();
//        this.env = env;
//    }
//
//    public void getPageUrl() {
//        driver.get(env.getRequiredProperty("test.url.logout"));
//    }
}
