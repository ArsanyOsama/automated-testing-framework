package com.Demo.TestNG;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import java.util.concurrent.TimeUnit;

public class Login_valid {
    WebDriver driver = new ChromeDriver();
    @BeforeTest
    public void openBrowser() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\farid\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        driver.navigate().to("https://www.saucedemo.com/v1/index.html");
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test(priority = 1)
    public void standardlogin() {
        driver.navigate().to("https://www.saucedemo.com/v1/index.html");
        WebElement username = driver.findElement(By.id("user-name"));
        WebElement password = driver.findElement(By.id("password"));
        username.sendKeys("standard_user");
        password.sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();


        Assert.assertTrue(driver.getCurrentUrl().contains("inventory.html"));
    }

    @Test(priority = 2)
    public void problemlogin() {
        driver.navigate().to("https://www.saucedemo.com/v1/index.html");
        WebElement username = driver.findElement(By.id("user-name"));
        WebElement password = driver.findElement(By.id("password"));
        username.sendKeys("problem_user");
        password.sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();


        Assert.assertTrue(driver.getCurrentUrl().contains("inventory.html"));
    }

    @Test(priority = 3)
    public void performancelogin() {
        driver.navigate().to("https://www.saucedemo.com/v1/index.html");
        WebElement username = driver.findElement(By.id("user-name"));
        WebElement password = driver.findElement(By.id("password"));
        username.sendKeys("performance_glitch_user");
        password.sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory.html"));
    }

    @AfterClass
    public void teardown() {
        driver.quit();
    }
}
