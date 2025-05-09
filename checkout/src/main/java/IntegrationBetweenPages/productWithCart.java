package IntegrationBetweenPages;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.time.Duration;
import java.util.List;

public class productWithCart {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeTest
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\farid\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/v1/index.html");
    }

    @Test
    public void productWithCart() throws InterruptedException {
        //  Login
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        Thread.sleep(2000);
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

        // Add first product to cart
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//button[contains(text(),'ADD TO CART')])[1]"))).click();
        Thread.sleep(2000);
        // Assertion: Verify cart badge shows "1"
        WebElement cartBadge = driver.findElement(By.className("shopping_cart_badge"));
        Assert.assertEquals(cartBadge.getText(), "1", "Cart badge should show 1 after adding one item.");

        //  Go to cart
        wait.until(ExpectedConditions.elementToBeClickable(By.className("shopping_cart_link"))).click();
        Thread.sleep(2000);
        // Assertion: Check item is present in the cart
        List<WebElement> cartItems = driver.findElements(By.className("cart_item"));
        Assert.assertTrue(cartItems.size() > 0, "There should be at least one item in the cart.");


        //  Click Remove
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='REMOVE']"))).click();
        Thread.sleep(2000);
        // Assertion: Check cart is empty
        List<WebElement> updatedCartItems = driver.findElements(By.className("cart_item"));
        Assert.assertEquals(updatedCartItems.size(), 0, "Cart should be empty after removing the item.");

        //  Click Continue Shopping
        wait.until(ExpectedConditions.elementToBeClickable(By.className("btn_secondary"))).click();
        Thread.sleep(2000);
        // Assertion: Verify returned to products page
        boolean onProductsPage = driver.getCurrentUrl().contains("inventory");
        Assert.assertTrue(onProductsPage, "Should navigate back to the products page.");

    }

    @AfterTest
    public void tearDown() {
        driver.quit();
}

    }

