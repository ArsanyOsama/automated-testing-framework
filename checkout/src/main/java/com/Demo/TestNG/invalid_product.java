package com.Demo.TestNG;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class invalid_product {
    WebDriver driver;

    @BeforeTest
    public void openBrowser() {
        System.setProperty("webdriver.chrome.driver", "C:\\Windows\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("https://www.saucedemo.com/v1/index.html");
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
    public void addAndRemoveTwoProducts() throws InterruptedException {
        // add to cart button
        List<WebElement> addButtons = driver.findElements(By.className("btn_inventory"));

        if (addButtons.size() >= 2) {
            addButtons.get(0).click();
            Thread.sleep(1000);
            addButtons.get(4).click();

            Thread.sleep(1000);

            // remove button on product screen
            List<WebElement> removeButtons = driver.findElements(By.xpath("//button[text()='REMOVE']"));

            if (removeButtons.size() >= 2) {
                removeButtons.get(0).click();
                removeButtons.get(1).click();
                System.out.println(" Removed the two products from cart");
                // Assertion: check if cart empty
                List<WebElement> cartBadge = driver.findElements(By.className("shopping_cart_badge"));
                Assert.assertTrue(cartBadge.isEmpty(), "Cart should be empty after removing both products.");

            } else {
                System.out.println(" Couldn't find 2 remove buttons");
            }
        } else {
            System.out.println(" Less than 2 products found to add.");
        }
    }
    @Test(priority = 2)
    public void selectSecondProductAndAddToCartFromDetails() throws InterruptedException {
        Thread.sleep(2000);
        // inventory item name
        List<WebElement> productNames = driver.findElements(By.className("inventory_item_name"));
        // index of product
        if (productNames.size() >= 4) {
            WebElement fourthProduct = productNames.get(3);
            fourthProduct.click();
            Thread.sleep(2000);
            // add to cart button
            WebElement addToCartBtn = driver.findElement(By.className("btn_primary"));
            addToCartBtn.click();
            Thread.sleep(2000);
            // Assertion: check added item with index 1
            WebElement cartBadge = driver.findElement(By.className("shopping_cart_badge"));
            String itemCount = cartBadge.getText();
            Assert.assertEquals(itemCount, "1", "Cart should have 1 item after adding one product.");

            //inventory details back button
            WebElement backBtn = driver.findElement(By.className("inventory_details_back_button"));
            backBtn.click();
            Thread.sleep(2000);
        }
    }

    @AfterTest
    public void closeBrowser() {
        driver.quit();
}

}
