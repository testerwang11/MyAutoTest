package com.core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;

public class Factory {

    public static ThreadLocal<WebDriver> threadDriver=new ThreadLocal<WebDriver>();
    private static WebDriver driver;
    private static DesiredCapabilities dCaps;

    public static ThreadLocal<WebDriver> getDriver() {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + File.separator + "src/main/resources/drivers/chrome/chromedriver.exe");
        dCaps = new DesiredCapabilities();

        driver = new ChromeDriver(dCaps);
        threadDriver.set(driver);
        return threadDriver;
    }

    public static void main(String[] args) {
        WebDriver driver = getDriver().get();
        driver.navigate().to("https://www.baidu.com");
        driver.quit();
    }
}
