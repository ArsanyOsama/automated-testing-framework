package com.Demo.TestNG;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import java.util.concurrent.TimeUnit;

public class Invalid_Login {
    WebDriver driver = new ChromeDriver();
    @BeforeTest
    public void openBrowser() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\farid\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        driver.navigate().to("https://www.saucedemo.com/v1/index.html");
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        // If a JS alert appears, accept it
        try {
            Alert alert = driver.switchTo().alert();
            alert.accept();
        } catch (NoAlertPresentException ignored) {
        }

        // If an HTML modal appears (e.g., “Change your password”), close it
        try {
            WebElement closeBtn = driver.findElement(By.cssSelector(".modal__close"));
            if (closeBtn.isDisplayed()) {
                closeBtn.click();
            }
        } catch (NoSuchElementException ignored) {
        }
    }

    @Test(priority = 1)
    public void caselogin1() throws InterruptedException{
        driver.navigate().to("https://www.saucedemo.com/v1/index.html");
        WebElement username = driver.findElement(By.id("user-name"));
        WebElement password = driver.findElement(By.id("password"));
        username.sendKeys("locked_out_user");
        password.sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        Thread.sleep(1000);
        WebElement error = driver.findElement(By.cssSelector("[data-test='error']"));
        String actualErrorText = error.getText();
        Assert.assertEquals(actualErrorText.trim(), "Epic sadface: Sorry, this user has been locked out.");
    }

    @Test(priority = 2)
    public void caselogin2() throws InterruptedException{
        driver.navigate().to("https://www.saucedemo.com/v1/index.html");
        WebElement username = driver.findElement(By.id("user-name"));
        WebElement password = driver.findElement(By.id("password"));
        username.sendKeys("");
        password.sendKeys("");
        Thread.sleep(2000);
        driver.findElement(By.id("login-button")).click();

        WebElement error = driver.findElement(By.cssSelector("[data-test='error']"));
        String actualErrorText = error.getText();
        Assert.assertTrue(actualErrorText.contains("Epic sadface: Username is required"));
    }

    @Test(priority = 3)
    public void caselogin3() throws InterruptedException{
        driver.navigate().to("https://www.saucedemo.com/v1/index.html");
        WebElement username = driver.findElement(By.id("user-name"));
        WebElement password = driver.findElement(By.id("password"));
        username.sendKeys("45standard_user#");
        password.sendKeys("@secret_sauce21");
        Thread.sleep(1000);
        driver.findElement(By.id("login-button")).click();
        WebElement error = driver.findElement(By.cssSelector("[data-test='error']"));
        String actualErrorText = error.getText();
        // Forced False
        Assert.assertTrue(actualErrorText.contains("Epic sadface: Username and password do not match any user in this service"));
    }


        @AfterClass
    public void teardown() {
        driver.quit();
    }
}
