package com.page;

import com.action.WebAction;
import com.core.Locator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.nio.file.WatchEvent;

public class Jira {

    private WebAction action;

    @FindBy(id = "login-form-username")
    private WebElement nameInput;

    @FindBy(id="login-form-password")
    private WebElement passwdInput;

    public Jira(WebAction action) {
        this.action = action;
    }

    /**
     * 登录jira
     * @param name
     * @param passwd
     */
    public void login(String name, String passwd) {
        action.getUrl("https://jira.mingyuanyun.com/login.jsp");
        action.sendKeys("id", "login-form-username", name);

        action.sendKeys("id", "login-form-password", passwd);
        action.click("id", "login-form-submit");
        action.waitForElementPresence(Locator.ID.name(), "header-details-user-fullname", "10");
    }


    /**
     * 退出
     */
    public void quit() {
        action.click(Locator.ID.name(), "header-details-user-fullname");
        action.click(Locator.ID.name(), "log_out");
    }

    /**
     * 添加备注
     * @param text
     */
    public void addRemark(String text) {
        action.click("id", "footer-comment-button");
        action.waitForElementVisible(Locator.ID.name(), "footer-comment-button", "10");
        action.sendKeys("id", "comment", "Tag:v2.3.14.13");
        action.click("id", "issue-comment-add-submit");
    }

}
