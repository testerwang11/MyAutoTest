package com.action;

import com.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * Created by jiangyitao.
 */
@Slf4j
public class WebAction {

    private WebDriver driver;

    private String timeout = "10000";

    public WebAction(WebDriver driver) {
        this.driver = driver;
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }


    private By getByWeb(String findBy, String value) {
        By by;
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        switch (findBy) {
            case "id":
                by = By.id(value);
                break;
            case "xpath":
                if (value.contains("")) {
                    value = value.replaceAll("\"", "'");
                }
                by = By.xpath(value);
                break;
            case "className":
                by = By.className(value);
                break;
            case "name":
                by = By.name(value);
                break;
            case "cssSelector":
                by = By.cssSelector(value);
                break;
            case "linkText":
                by = By.linkText(value);
                break;
            case "partialLinkText":
                by = By.partialLinkText(value);
                break;
            case "tagName":
                by = By.tagName(value);
                break;
            default:
                throw new RuntimeException("暂不支持: " + findBy);
        }
        return by;
    }

    /*****************************PC WEB*****************************/

    /**
     * url 跳转
     *
     * @param url
     */
    public void getUrl(Object url) {
        log.info("打开页面:" + url);
        driver.get((String) url);
    }

    /**
     * 点击
     *
     * @param findBy
     * @param value
     */
    public void click(Object findBy, Object value) {
        /*new WebDriverWait(driver, Long.parseLong(App.getProperty("timeout")))
                .until(ExpectedConditions.elementToBeClickable(getByWeb((String) findBy, (String) value))).click();*/
        driver.findElement(getByWeb((String) findBy, (String) value)).click();
    }

    /**
     * 输入
     *
     * @param findBy
     * @param value
     */
    public WebElement sendKeys(String findBy, String value, String content) {
        //waitForElementPresence((String) findBy, (String) value, App.getProperty("timeout"));
        WebElement element = driver.findElement(getByWeb(findBy, value));
        log.info("输入:" + content);
        //element.clear();
        element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        element.sendKeys(content);
        return element;
    }

    /**
     * 切换iframe
     *
     * @param findBy
     * @param value
     */
    public void selectFrame(Object findBy, Object value) {
        waitForElementPresence((String) findBy, (String) value, timeout);
        driver.switchTo().frame(driver.findElement(getByWeb((String) findBy, (String) value)));
    }

    /**
     * 切换到最外层frame
     */
    public void back2DefaultContent() {
        driver.switchTo().defaultContent();
    }

    /**
     * 执行js
     *
     * @param script
     * @return
     */
    public Object runCommond(String script) {
        return ((JavascriptExecutor) driver).executeScript(script);
    }

    /**
     * 获取元素的值
     *
     * @param findBy
     * @param value
     * @param attribute
     * @return
     */
    public String getElementValue(Object findBy, Object value, Object attribute) {
        waitForElementPresence((String) findBy, (String) value, timeout);
        WebElement element = driver.findElement(getByWeb((String) findBy, (String) value));
        if (((String) attribute).equalsIgnoreCase("text")) {
            return element.getText();
        }
        return element.getAttribute((String) attribute);
    }

    /**
     * 获取元素数量
     *
     * @param findBy
     * @param value
     * @return
     */
    public Integer getElementSize(Object findBy, Object value) {
        waitForElementPresence((String) findBy, (String) value, timeout);
        return driver.findElements(getByWeb((String) findBy, (String) value)).size();
    }

    /**
     * 通过文本选择下拉框
     *
     * @param findBy
     * @param value
     * @param text
     */
    public void selectElement(Object findBy, Object value, Object text) {
        //new Select(driver.findElement(getByWeb((String) findBy, (String) value))).selectByVisibleText((String) text);
        WebElement dropdown = driver.findElement(getByWeb((String) findBy, (String) value));
        List<WebElement> options = dropdown.findElements(By.tagName("li"));
        for (WebElement option : options) {
            if (option.getText().equals((String) text)) {
                option.click();
                break;
            }
        }
    }

    /**
     * 鼠标悬停
     *
     * @param findBy
     * @param value
     */
    public void moserOver(Object findBy, Object value) {
        org.openqa.selenium.interactions.Actions action = new org.openqa.selenium.interactions.Actions(driver);
        action.moveToElement(driver.findElement(getByWeb((String) findBy, (String) value))).perform();
    }

    /**
     * 等待元素出现
     *
     * @param findBy
     * @param value
     * @param maxWaitTimeInSeconds
     * @return
     */
    public WebElement waitForElementPresence(String findBy, String value, String maxWaitTimeInSeconds) {
        return new WebDriverWait(driver, Long.parseLong(maxWaitTimeInSeconds))
                .until(ExpectedConditions.presenceOfElementLocated(getByWeb(findBy, value)));
    }

    public WebElement waitForElementVisible(String findBy, String value, String maxWaitTimeInSeconds) {
        return new WebDriverWait(driver, Long.parseLong(maxWaitTimeInSeconds))
                .until(ExpectedConditions.visibilityOfElementLocated(getByWeb(findBy, value)));
    }

    /**
     * 等待元素消失
     *
     * @param findBy
     * @param value
     * @param maxWaitTimeInSeconds
     */
    public void waitForElementNotPresence(String findBy, String value, String maxWaitTimeInSeconds) {
        Function<WebDriver, Boolean> waitFn = new Function<WebDriver, Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    for (WebElement el : driver.findElements(getByWeb(findBy, value))) {
                        if (el.isDisplayed()) {
                            return false;
                        }
                    }
                } catch (Exception ex) {
                    return true;
                }
                return true;
            }
        };
        WebDriverWait wait = new WebDriverWait(driver, Long.parseLong(maxWaitTimeInSeconds));
        wait.until(waitFn);
    }

    /**
     * 判断页面是否包含
     *
     * @param text
     * @return
     */
    public void pageShouldContainsText(String text) {
        if (!driver.getPageSource().contains(text)) {
            throw new Error("页面不包含: " + text);
        }
    }

    /**
     * 根据title切换窗口
     *
     * @param windowTitle
     * @return
     */
    public void switchToWindow(String windowTitle, String index) {
        boolean switchByTitle = false;
        int num = 0;
        if (StringUtil.isNotEmpty(index)) {
            num = Integer.parseInt(index);
            switchByTitle = false;
        }
        if (StringUtil.isNotEmpty(windowTitle)) {
            switchByTitle = true;
        }
        String currentHandle = driver.getWindowHandle();
        Set<String> handles = driver.getWindowHandles();
        int i = 1;
        for (String s : handles) {
            if (switchByTitle) {
                //根据title切换
                if (s.equals(currentHandle))
                    continue;
                else {
                    driver.switchTo().window(s);
                    if (driver.getTitle().contains(windowTitle)) {
                        break;
                    } else
                        continue;
                }
            } else {
                if (i == num) {
                    driver.switchTo().window(s);
                    break;
                }
            }
            i++;
        }
    }

    /**
     * 新建tap页，并打开指定url
     *
     * @param url
     */
    public void createTap(String url) {
        WebElement element = driver.findElement(By.tagName("body"));
        element.sendKeys(Keys.CONTROL + "t");
        driver.get(url);
    }

    /**
     * 关闭标签页
     */
    public void closeTap() {
        driver.close();
        Set<String> handles = driver.getWindowHandles();
        int pageNum = handles.size();
        //判断是否有多个页面，如果有，自动返回前一个页面
        if (pageNum > 0) {
            for (String s : handles) {
                driver.switchTo().window(s);
            }
        }
    }


    /**
     * 关闭浏览器
     */
    public void closeBrowser() {
        driver.quit();
    }

    /**
     * 刷新页面
     */
    public void reloadPage() {
        driver.navigate().refresh();
    }


    /**
     * 滚动页面到指定位置
     * @param width
     * @param height
     */
    public void scroll(Object width, Object height) {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(" + width + ", " + height + ")");
    }
}
