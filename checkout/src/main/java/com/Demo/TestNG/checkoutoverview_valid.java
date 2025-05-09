package com.Demo.TestNG;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class checkoutoverview_valid {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeTest
    public void openBrowser() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\farid\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.get("https://www.saucedemo.com/v1/index.html");

        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        System.out.println("Login successful");

        try {
            Alert alert = driver.switchTo().alert();
            alert.accept();
        } catch (NoAlertPresentException ignored) {}

        try {
            WebElement closeBtn = driver.findElement(By.cssSelector(".modal__close"));
            if (closeBtn.isDisplayed()) {
                closeBtn.click();
            }
        } catch (NoSuchElementException ignored) {}

        WebElement addToCartBtn = wait.until(ExpectedConditions.elementToBeClickable(By.className("btn_inventory")));
        addToCartBtn.click();
        System.out.println("Item added to cart");

        driver.findElement(By.className("shopping_cart_link")).click();
        WebElement checkoutBtn = wait.until(ExpectedConditions.elementToBeClickable(By.className("checkout_button")));
        checkoutBtn.click();

        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("checkout-step-one.html"), "Failed to reach checkout-step-one page");

        driver.findElement(By.id("first-name")).sendKeys("Farida");
        driver.findElement(By.id("last-name")).sendKeys("Hossam");
        driver.findElement(By.id("postal-code")).sendKeys("11765");

        WebElement continueBtn = driver.findElement(By.cssSelector(".btn_primary.cart_button"));
        continueBtn.click();
        System.out.println("Continue button clicked.");
    }

    @Test(priority = 1)
    public void testCheckoutOverview() throws InterruptedException {
        String overviewUrl = driver.getCurrentUrl();
        Assert.assertTrue(overviewUrl.contains("checkout-step-two.html"), "Failed to reach Checkout Overview page.");

        try {
            WebElement itemName = driver.findElement(By.className("inventory_item_name"));
            WebElement itemPrice = driver.findElement(By.className("inventory_item_price"));
            Assert.assertTrue(itemName.isDisplayed(), "Item name not displayed");
            Assert.assertTrue(itemPrice.isDisplayed(), "Item price not displayed");
            System.out.println("Product Name: " + itemName.getText());
            System.out.println("Product Price: " + itemPrice.getText());
        } catch (NoSuchElementException e) {
            Assert.fail("Product data not found.");
        }

        try {
            WebElement totalPrice = driver.findElement(By.className("summary_total_label"));
            Assert.assertTrue(totalPrice.isDisplayed(), "Total price not displayed");
            System.out.println("Total Price: " + totalPrice.getText());
        } catch (NoSuchElementException e) {
            Assert.fail("Total price not found.");
        }
    }

    @Test(priority = 2)
    public void testFinishAndVerifyCompletion() throws InterruptedException {
        WebElement finishBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".btn_action.cart_button")));
        finishBtn.click();

        Thread.sleep(2000);
        wait.until(ExpectedConditions.urlContains("checkout-complete.html"));

        String finalUrl = driver.getCurrentUrl();
        Assert.assertTrue(finalUrl.contains("checkout-complete.html"), "Checkout not completed. URL: " + finalUrl);

        WebElement thankYouMsg = driver.findElement(By.className("complete-header"));
        Assert.assertEquals(thankYouMsg.getText().toLowerCase(), "thank you for your order", "Thank-you message is incorrect or missing.");
    }

    @AfterClass
    public void teardown() {
        driver.quit();
    }
}
