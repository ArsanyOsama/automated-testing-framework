package IntegrationBetweenPages;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class checkoutWithOverviewThenCart {
    WebDriver driver;
    WebDriverWait wait;
    @BeforeTest
    public void openBrowser() throws InterruptedException  {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\farid\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.get("https://www.saucedemo.com/v1/index.html"); // website link
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        System.out.println("Login successful");
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
        driver.findElement(By.className("shopping_cart_link")).click();

        //checkout button
        driver.findElement(By.className("checkout_button")).click();

        // navigate to checkout page
        String currentUrl = driver.getCurrentUrl();
        if (currentUrl.contains("checkout-step-one.html")) {
            System.out.println("checkout page opens successfully");
        } else {
            System.out.println("can not navigate to checkout page " + currentUrl);
        }
        //wait after navigate to page
        Thread.sleep(1000);
    }
    @Test
    public void checkoutWithCart(){
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("first-name")));
            System.out.println("Checkout page loaded");

            driver.findElement(By.id("first-name")).sendKeys("Farida");
            driver.findElement(By.id("last-name")).sendKeys("Hossam");
            driver.findElement(By.id("postal-code")).sendKeys("11765");
            Thread.sleep(1000);
            //continue button
            driver.findElement(By.cssSelector(".btn_primary.cart_button")).click();

            // wait after click on continue button
            Thread.sleep(1000);

            // navigate back to checkout page
            driver.navigate().back();
            Thread.sleep(1000);
            // click on shopping cart icon
            driver.findElement(By.cssSelector("#shopping_cart_container > a > svg > path")).click();
            Thread.sleep(1000);
            // remove button in cart screen
            driver.findElement(By.cssSelector(".btn_secondary.cart_button")).click();
            Thread.sleep(1000);
            // checkout button
            driver.findElement(By.className("checkout_button")).click();
            Thread.sleep(1000);
        } catch (org.openqa.selenium.TimeoutException e) {
            System.out.println("Timeout waiting for element: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }
    @AfterClass
    public void teardown() {
        driver.quit();
    }
}



