package IntegrationBetweenPages;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class valid_checkoverviewWithProduct {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeTest
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\farid\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("https://www.saucedemo.com/v1/index.html");
    }

    @Test
    public void testCancelAtCheckout() throws InterruptedException {
        // Login
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        Thread.sleep(1000);
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
        // Add "Sauce Labs Backpack" and "Sauce Labs Bike Light" to cart
        driver.findElement(By.xpath("//div[text()='Sauce Labs Backpack']/following::button[1]")).click();
        driver.findElement(By.xpath("//div[text()='Sauce Labs Bike Light']/following::button[1]")).click();
        Thread.sleep(1000);
        // Click the cart icon and wait for page load
        driver.findElement(By.className("shopping_cart_link")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("checkout_button"))); // Ensure checkout button is visible
        Thread.sleep(1000);
        // Click Checkout button
        WebElement checkoutBtn = driver.findElement(By.className("checkout_button"));
        checkoutBtn.click();
        Thread.sleep(1000);
        //  Fill in information and click Continue
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("first-name"))); // Ensure first-name field is visible
        driver.findElement(By.id("first-name")).sendKeys("nour");
        driver.findElement(By.id("last-name")).sendKeys("ahmed");
        driver.findElement(By.id("postal-code")).sendKeys("11211");
        driver.findElement(By.className("cart_button")).click();
        Thread.sleep(1000);
        //  Click Cancel button to go back to the
        //  products page
        wait.until(ExpectedConditions.elementToBeClickable(By.className("cart_cancel_link")));
        WebElement cancelBtn = driver.findElement(By.className("cart_cancel_link"));
        cancelBtn.click();
        Thread.sleep(1000);
        // Verify if we are back on the product page
        wait.until(ExpectedConditions.urlContains("inventory"));
        Thread.sleep(1000);
        String currentUrl = driver.getCurrentUrl();
        if (currentUrl.contains("inventory")) {
            System.out.println(" Successfully returned to the product page.");
        } else {
            System.out.println(" Failed to return to the product page. Current URL: " + currentUrl);
        }
}
    @AfterClass
    public void teardown() {
        driver.quit();
    }
    }
