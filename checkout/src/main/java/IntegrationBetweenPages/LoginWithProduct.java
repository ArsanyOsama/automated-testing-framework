package IntegrationBetweenPages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class LoginWithProduct {
    WebDriver driver = new ChromeDriver();

    @BeforeTest
    public void openBrowser() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\farid\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        driver.navigate().to("https://www.saucedemo.com/v1/index.html");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test(priority = 1)
    public void loginAndLogout() throws InterruptedException {
        // Login
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        // Wait for login to complete (check for a product or cart icon)

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement productContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("inventory_list")));
        Assert.assertTrue(productContainer.isDisplayed(), "Product container is not visible after login.");

        Thread.sleep(1000);

        // Side menu and logout
        driver.findElement(By.className("bm-burger-button")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("logout_sidebar_link")).click();
        Thread.sleep(1000);

        // Assert that we're back on the login page
        WebElement loginButton = driver.findElement(By.id("login-button"));
        Assert.assertTrue(loginButton.isDisplayed(), "Login button is not visible after logout.");
    }

    @Test(priority = 2)
    public void loginAddToCartLogoutLoginAgain() throws InterruptedException {
        // Re-login
        driver.navigate().to("https://www.saucedemo.com/v1/index.html");
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        Thread.sleep(1000);

        // Assert successful login by checking for product listings
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement productContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("inventory_list")));
        Assert.assertTrue(productContainer.isDisplayed(), "Product container is not visible after re-login.");

        // Add to cart
        List<WebElement> addButtons = driver.findElements(By.className("btn_inventory"));
        if (addButtons.size() >= 1) {
            WebElement firstButton = addButtons.get(0);
            firstButton.click();
            Thread.sleep(1000);
// Assert that the cart icon is updated
            WebElement cartIcon = driver.findElement(By.className("shopping_cart_link"));
            String cartText = cartIcon.getText();
            Assert.assertTrue(cartText.contains("1"), "Cart icon doesn't reflect the added item.");
        }

        // Side menu and logout
        driver.findElement(By.className("bm-burger-button")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("logout_sidebar_link")).click();
        Thread.sleep(1000);

        // Re-login again
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        Thread.sleep(1000);

        // Assert that login was successful after re-login
        WebElement productContainerAfterLogin = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("inventory_list")));
        Assert.assertTrue(productContainerAfterLogin.isDisplayed(), "Product container is not visible after second login.");
    }


        @AfterClass
    public void closeBrowser() {
        driver.quit();
    }
}
