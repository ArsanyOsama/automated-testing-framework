package com.Demo.TestNG;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class invalid_checkoutoverview {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeTest
    public void openBrowser() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\farid\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
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
    public void testCheckoutWithProblemUser() {
        // Login with 'problem_user'
        driver.get("https://www.saucedemo.com/v1/index.html");
        driver.findElement(By.id("user-name")).sendKeys("problem_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        System.out.println(" Logged in with problem_user");

        // Add first two products to the cart
        driver.findElements(By.className("btn_primary")).get(0).click();
        driver.findElements(By.className("btn_primary")).get(1).click();
        System.out.println(" Added first two items to cart");

        //  Open the cart page directly
        driver.get("https://www.saucedemo.com/v1/cart.html");
        System.out.println(" Opened cart directly via URL");

        // Wait 3 seconds to simulate message display
        try {
            Thread.sleep(3000);
            System.out.println(" Note: Product images/text might be incorrect (bug simulation)");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Proceed to Checkout
        WebElement checkoutBtn = wait.until(ExpectedConditions.elementToBeClickable(By.className("checkout_button")));
        checkoutBtn.click();
        System.out.println("Clicked Checkout");

        // Fill in valid customer information
        driver.findElement(By.id("first-name")).sendKeys("Nouran");
        driver.findElement(By.id("last-name")).sendKeys("Nasser");
        driver.findElement(By.id("postal-code")).sendKeys("11223");

        driver.findElement(By.cssSelector(".btn_primary.cart_button")).click();
        System.out.println("Clicked Continue");

        // Stay on Overview page and print bug note
        try {
            Thread.sleep(3000);
            System.out.println(" BUG: Wrong products or pricing may appear on the Checkout: Overview page!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//  Assertions to verify correct product data
        try {
            WebElement itemName = driver.findElement(By.className("inventory_item_name"));
            WebElement itemPrice = driver.findElement(By.className("inventory_item_price"));

            String expectedName = "Sauce Labs Backpack";
            String expectedPrice = "$29.99";

            String actualName = itemName.getText();
            String actualPrice = itemPrice.getText();

            // Use TestNG Asserts
            Assert.assertEquals(actualName, expectedName, "Product name mismatch!");
            Assert.assertEquals(actualPrice, expectedPrice, "Product price mismatch!");

            System.out.println("Assertions passed: Product name and price are correct.");

        } catch (NoSuchElementException e) {
            Assert.fail("Product elements not found on overview page.");
        }

        // stop here without clicking Finish
        System.out.println(" Stopped before finalizing checkout to verify overview info.");
}
    @Test(priority = 2)
    public void testCheckoutWithEmptyCartOnly() {
        // 1. Log in
        driver.get("https://www.saucedemo.com/v1/index.html");
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        System.out.println("Login successful");
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // 2. Navigate directly to the cart without adding any products
        driver.findElement(By.className("shopping_cart_link")).click();
        System.out.println("Opened empty cart");

        // Wait for 3 seconds to visually confirm the cart is empty
        try {
            Thread.sleep(3000); // pause for observation
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 3. Click the Checkout button using an extended explicit wait
        WebDriverWait cartWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement checkoutBtn = cartWait.until(ExpectedConditions.elementToBeClickable(By.className("checkout_button")));
        checkoutBtn.click();
        System.out.println("Clicked Checkout");
        // 4. Enter customer information
        driver.findElement(By.id("first-name")).sendKeys("nour");
        driver.findElement(By.id("last-name")).sendKeys("nasser");
        driver.findElement(By.id("postal-code")).sendKeys("12345");

        // 5. Click Continue
        driver.findElement(By.cssSelector(".btn_primary.cart_button")).click();
        System.out.println("Clicked Continue");

        // 6. Click Finish
        WebElement finishBtn = wait.until(ExpectedConditions.elementToBeClickable(By.className("cart_button")));
        finishBtn.click();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        System.out.println(" Clicked Finish");

        // 7. Verify system behavior
        String currentUrl = driver.getCurrentUrl();
        boolean onCompletePage = currentUrl.contains("checkout-complete.html");

        if (onCompletePage) {
            System.out.println("BUG: Checkout completed with EMPTY cart! Final URL: " + currentUrl);
        } else {
            System.out.println("Expected behavior: Could not complete checkout with empty cart.");
        }

        // Assertion to detect the bug
        Assert.assertTrue(onCompletePage, " Bug: User should NOT be able to finish checkout with empty cart!");
}

    @AfterClass
    public void teardown() {
        driver.quit();
    }
}

