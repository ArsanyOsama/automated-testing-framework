package com.Demo.TestNG;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import org.testng.Assert;


public class checkout_valid {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeTest
    public void openBrowser() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\farid\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.get("https://www.saucedemo.com/v1/index.html"); // website link
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        System.out.println("Login successful");

        try {
            Alert alert = driver.switchTo().alert();
            alert.accept();
        } catch (NoAlertPresentException ignored) {
        }

        try {
            WebElement closeBtn = driver.findElement(By.cssSelector(".modal__close"));
            if (closeBtn.isDisplayed()) {
                closeBtn.click();
            }
        } catch (NoSuchElementException ignored) {
        }

        driver.findElement(By.className("btn_inventory")).click();
        driver.findElement(By.className("shopping_cart_link")).click();
        driver.findElement(By.className("checkout_button")).click();

        String currentUrl = driver.getCurrentUrl();
        if (currentUrl.contains("checkout-step-one.html")) {
            System.out.println("navigate to checkout page successfully");
        } else {
            System.out.println("can not navigate to checkout page " + currentUrl);
        }

        Thread.sleep(1000);
    }

    @Test(priority = 1)
    public void checkouthappy() {
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("first-name")));
            System.out.println("Checkout page loaded");

            driver.findElement(By.id("first-name")).sendKeys("Farida");
            driver.findElement(By.id("last-name")).sendKeys("Hossam");
            driver.findElement(By.id("postal-code")).sendKeys("11765");
            Thread.sleep(2000);
            driver.findElement(By.cssSelector(".btn_primary.cart_button")).click();

            Thread.sleep(2000);

            Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-two.html"));

            driver.navigate().back();
            Thread.sleep(2000);

        } catch (TimeoutException e) {
            System.out.println("Timeout waiting for element: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }

    @Test(priority = 2)
    public void cancelButton() {
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driver.findElement(By.cssSelector(".cart_cancel_link.btn_secondary")).click();

        Assert.assertTrue(driver.getCurrentUrl().contains("cart.html"));

        wait = new WebDriverWait(driver, Duration.ofSeconds(80));
    }

    @AfterClass
    public void teardown() {
        driver.quit();
    }
}
