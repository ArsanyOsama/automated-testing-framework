package com.Demo.TestNG;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
public class invalid_checkout {
    WebDriver driver;
    WebDriverWait wait;
    @BeforeTest
    public void openBrowser() throws InterruptedException  {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\farid\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.get("https://www.saucedemo.com/v1/index.html"); //website link
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        System.out.println("Login successful");
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
        // Add item to cart
        driver.findElement(By.className("btn_inventory")).click();
        //shopping cart icon
        driver.findElement(By.className("shopping_cart_link")).click();
        // checkout button
        driver.findElement(By.className("checkout_button")).click();

        // navigate to checkout page
        String currentUrl = driver.getCurrentUrl();
        if (currentUrl.contains("checkout-step-one.html")) {
            System.out.println("navigate to checkout page successfully");
        } else {
            System.out.println("can not navigate to checkout page  " + currentUrl);
        }
        Thread.sleep(1000);

    }
    @Test(priority = 1)
    public void invalidspaces() {
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("first-name")));
            System.out.println("Checkout page loaded");

            driver.findElement(By.id("first-name")).sendKeys("  ");
            driver.findElement(By.id("last-name")).sendKeys("  ");
            driver.findElement(By.id("postal-code")).sendKeys("   ");
            Thread.sleep(3000);
            //continue button
            driver.findElement(By.cssSelector(".btn_primary.cart_button")).click();

            // wait after click on Continue
            Thread.sleep(2000);
            driver.navigate().back();
            Thread.sleep(2000);

        } catch (org.openqa.selenium.TimeoutException e) {
            System.out.println("Timeout waiting for element: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }

    @Test(priority = 2)
    public void invalidinput() {
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("first-name")));
            System.out.println("Checkout page loaded");

            driver.findElement(By.id("first-name")).sendKeys("0000");
            driver.findElement(By.id("last-name")).sendKeys("0000");
            driver.findElement(By.id("postal-code")).sendKeys("0000");
            Thread.sleep(2000);

            driver.findElement(By.cssSelector(".btn_primary.cart_button")).click();
            Thread.sleep(2000);
            driver.navigate().back();
            Thread.sleep(2000);

        } catch (org.openqa.selenium.TimeoutException e) {
            System.out.println("Timeout waiting for element: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }
    @Test (priority = 3)
    public void reststate() throws InterruptedException{
        //side menu icon
        driver.findElement(By.cssSelector("#menu_button_container > div > div:nth-child(3) > div > button")).click();
        wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        //reset app state
        driver.findElement(By.cssSelector("#reset_sidebar_link")).click();
        Thread.sleep(2000);

    }

    @AfterClass
    public void teardown() {
        driver.quit();
    }

}
