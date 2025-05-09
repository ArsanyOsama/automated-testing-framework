package IntegrationBetweenPages;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class invalid_cartWithProductDetail {
    WebDriver driver;

    @BeforeClass
    public void setup() {
        driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/v1/index.html");
        driver.manage().window().maximize();

        // Login with standard_user
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
    }

    @Test
    public void testRemoveFromItemInfoScreen() throws InterruptedException {
        // Add first item
        driver.findElement(By.className("btn_inventory")).click();
        Thread.sleep(1000);

        // Open cart
        driver.findElement(By.className("shopping_cart_link")).click();
        Thread.sleep(1000);

        // Click on item name from cart to open information screen
        driver.findElement(By.className("inventory_item_name")).click();
        Thread.sleep(2000); // Wait on item info screen

        // Click Remove on information screen
        driver.findElement(By.className("btn_secondary")).click();
        Thread.sleep(1000);

        // navigate back
        driver.navigate().back();
        Thread.sleep(2000);
        System.out.println("delete product from product detail page and navigate back");
        // Assertion: Check that cart is now empty
        boolean isCartEmpty = driver.findElements(By.className("cart_item")).isEmpty();
        Assert.assertTrue(isCartEmpty, "Cart should be empty after removing the item from the product detail page.");

    }

    @AfterClass
    public void teardown() {
        driver.quit();
}
}

