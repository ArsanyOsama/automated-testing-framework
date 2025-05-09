package com.Demo.TestNG;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class cart_valid {
     WebDriver driver;
    @BeforeClass
    public void setup() {
        driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/v1/index.html");
        driver.manage().window().maximize();

        // Login first
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
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
        // Add item to cart
        driver.findElement(By.className("btn_inventory")).click();
    }

    @Test (priority = 1)
    public void testOpenCartAndCheckout() throws InterruptedException {
        driver.findElement(By.className("shopping_cart_link")).click();

        // wait on cart screen before click on checkout button
        Thread.sleep(3000);

        driver.findElement(By.className("checkout_button")).click();
        // Assertion: Verify that user navigated to checkout step one
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("checkout-step-one.html"), "Should navigate to checkout-step-one page, but got: " + currentUrl);

        if (currentUrl.contains("checkout-step-one.html")) {
            System.out.println("navigate to checkout page successfully");
        } else {
            System.out.println("can not navigate to checkout page" + currentUrl);
        }
        //wait after navigate to checkout page
        Thread.sleep(3000);
        driver.navigate().back();
        Thread.sleep(3000);
    }
    @Test (priority = 2)
    public void continueshopping()throws InterruptedException {
        //continue shopping button
        driver.findElement(By.cssSelector("#cart_contents_container > div > div.cart_footer > a.btn_secondary")).click();
        Thread.sleep(3000);
        // Assertion: Verify that user is back on products page
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("inventory"), "Should navigate back to inventory page, but got: " + currentUrl);

    }

    @AfterClass
    public void teardown() {
        driver.quit();
}

    }

