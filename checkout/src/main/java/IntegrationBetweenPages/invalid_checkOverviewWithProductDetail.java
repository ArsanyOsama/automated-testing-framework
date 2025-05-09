package IntegrationBetweenPages;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
public class invalid_checkOverviewWithProductDetail {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeTest
    public void setup() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\farid\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(7));
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/v1/index.html");
        //  Login
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
    }

    @Test
    public void followAllStepsExactly() throws InterruptedException {

        //  Add to cart (Sauce Labs Backpack)
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='Sauce Labs Backpack']/following::button[1]"))).click();
        Thread.sleep(1000);

        // click on cart icon
        wait.until(ExpectedConditions.elementToBeClickable(By.className("shopping_cart_link"))).click();
        Thread.sleep(1000);

        //  click on CHECKOUT button
        wait.until(ExpectedConditions.elementToBeClickable(By.className("checkout_button"))).click();
        Thread.sleep(1000);

        // fill information
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("first-name"))).sendKeys("nona");
        driver.findElement(By.id("last-name")).sendKeys("ko");
        driver.findElement(By.id("postal-code")).sendKeys("12345");
        Thread.sleep(1000);

        // click on CONTINUE button
        driver.findElement(By.className("cart_button")).click();
        Thread.sleep(1000);

        // click on product name
        wait.until(ExpectedConditions.elementToBeClickable(By.className("inventory_item_name"))).click();
        Thread.sleep(1000);
        // click on remove button
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='REMOVE']"))).click();
        Thread.sleep(1000);

        // click on back button
        wait.until(ExpectedConditions.elementToBeClickable(By.className("inventory_details_back_button"))).click();
        Thread.sleep(1000);
        // Assertion: Verify the product is no longer in the cart
        String cartText = driver.findElement(By.className("shopping_cart_badge")).getText();
        Assert.assertEquals(cartText, "0", " Product not removed correctly from the cart! Cart count: " + cartText);

        System.out.println("all steps are successfully");
    }

    @AfterClass
    public void teardown() {
        driver.quit();
    }
}

