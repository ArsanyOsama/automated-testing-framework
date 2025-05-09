package com.Demo.TestNG;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class invalid_cart {
    WebDriver driver;

    @BeforeClass
    public void setup() {
        driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/v1/index.html");
        driver.manage().window().maximize();

        // Login with problem_user
        driver.findElement(By.id("user-name")).sendKeys("problem_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
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
    public void testCheckoutWithEmptyCart() throws InterruptedException {
        // Open cart without adding anything
        driver.findElement(By.className("shopping_cart_link")).click();
        Thread.sleep(3000);
        // Click Checkout
        driver.findElement(By.className("checkout_button")).click();
        // Wait on Checkout screen
        Thread.sleep(3000);
        System.out.println("opens checkout page");
        // Assertion: Verify that we reached checkout-step-one even with empty cart
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("checkout-step-one.html"), "Expected to navigate to checkout page even with empty cart. Current URL: " + currentUrl);

    }
    @Test(priority = 2)
    public void testOpenItemInfoFromCart() throws InterruptedException {
        // Navigate back to products page
        driver.navigate().back();
        Thread.sleep(3000);

        driver.navigate().back();
        Thread.sleep(3000);

        // Add first item to cart
        driver.findElement(By.className("btn_inventory")).click();

        // Open cart
        driver.findElement(By.className("shopping_cart_link")).click();
        Thread.sleep(3000);

        // Click on item name to open information screen
        driver.findElement(By.className("inventory_item_name")).click();
        // Wait 5 seconds on information screen
        Thread.sleep(5000);
        System.out.println("display product information ");
        // Assertion: Verify we are on product details page
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("inventory-item"), "Expected to be on product details page. Current URL: " + currentUrl);

    }

    @AfterClass
    public void teardown() {
        driver.quit();
}
    }
