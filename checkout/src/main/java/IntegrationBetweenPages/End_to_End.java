package IntegrationBetweenPages;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class End_to_End {
    WebDriver driver = new ChromeDriver();

    @BeforeTest
    public void openBrowser() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\farid\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        driver.navigate().to("https://www.saucedemo.com/v1/index.html");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test(priority = 1)
    public void standardlogin() throws InterruptedException {
        WebElement username = driver.findElement(By.id("user-name"));
        WebElement password = driver.findElement(By.id("password"));
        username.sendKeys("standard_user");
        password.sendKeys("secret_sauce");
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

        List<WebElement> addButtons = driver.findElements(By.className("btn_inventory"));

        if (addButtons.size() >= 1) {
            WebElement firstButton = addButtons.get(0);
            firstButton.click();
            Thread.sleep(1000);
        }

        driver.findElement(By.className("shopping_cart_link")).click();
        Thread.sleep(1000);

        driver.findElement(By.cssSelector("a.btn_action.checkout_button")).click();
        Thread.sleep(1000);

        driver.findElement(By.id("first-name")).sendKeys("nour");
        driver.findElement(By.id("last-name")).sendKeys("nasser");
        driver.findElement(By.id("postal-code")).sendKeys("123456");
        driver.findElement(By.xpath("//input[@value='CONTINUE']")).click();
        Thread.sleep(1000);

        driver.findElement(By.linkText("FINISH")).click();
        Thread.sleep(1000);

        }

    @AfterTest
    public void closeBrowser() {
        driver.quit();
    }
}

