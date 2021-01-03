package ui;

import com.action.WebAction;
import com.core.Factory;
import com.page.Jira;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class JiraAddRemark {

    private WebDriver driver;
    private WebAction action;

    private Jira jira;

    @BeforeTest
    public void beforeTest() {
        driver = Factory.getDriver().get();
        action = new WebAction(driver);
        jira = new Jira(action);
        jira.login("wanghy70", "why1988!!");

    }

    @AfterTest
    public void afterTest() {
        jira.quit();
        driver.close();
        driver.quit();
    }

    @Test
    public void test001() throws InterruptedException {
       // jira.addRemark("Tag:v2.3.14.13");
        System.out.println("1");
    }
}
