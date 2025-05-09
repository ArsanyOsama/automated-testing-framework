package com.Demo.TestNG;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.*;
import org.testng.Assert;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.time.Duration;

public class product_valid {
    WebDriver driver;

    @BeforeTest
    public void openBrowser() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\farid\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get("https://www.saucedemo.com/v1/index.html");
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

    @Test(priority = 1)
    public void addfirstproduct() throws InterruptedException {
        List<WebElement> addButtons = driver.findElements(By.className("btn_inventory"));
        // add to cart button
        if (addButtons.size() >= 1) {
            WebElement firstButton = addButtons.get(0);
            firstButton.click();
            Thread.sleep(1000);
        }
        // Assertion: check button change to "REMOVE"
        WebElement updatedButton = driver.findElements(By.className("btn_inventory")).get(0);
        String buttonText = updatedButton.getText();
        Assert.assertEquals(buttonText.toLowerCase(), "remove", "The button should change to REMOVE after adding to cart.");


    }

    @Test(priority = 2)
    public void selectSecondProductAndAddToCartFromDetails() throws InterruptedException {
        Thread.sleep(2000); // wait page to load
        //inventory item name
        List<WebElement> productNames = driver.findElements(By.className("inventory_item_name"));

        if (productNames.size() >= 2) {
            WebElement secondProduct = productNames.get(2);
            secondProduct.click();
            Thread.sleep(2000);
            //add to cart button
            WebElement addToCartBtn = driver.findElement(By.className("btn_primary"));
            addToCartBtn.click();
            Thread.sleep(2000);
            WebElement cartBadge = driver.findElement(By.className("shopping_cart_badge"));
            String itemCount = cartBadge.getText();
            Assert.assertEquals(itemCount, "2", "Cart item count should be 2 after adding two products.");

        }
    }

    @Test(priority = 3)
    public void sortingproducts() throws InterruptedException {
        driver.navigate().to("https://www.saucedemo.com/v1/index.html");
        WebElement username = driver.findElement(By.id("user-name"));
        WebElement password = driver.findElement(By.id("password"));
        username.sendKeys("standard_user");
        password.sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        Thread.sleep(2000);
        WebElement dropdown = driver.findElement(By.className("product_sort_container"));
        Select sortDropdown = new Select(dropdown);
        // 1. Sort Z to A
        sortDropdown.selectByVisibleText("Name (Z to A)");
        Thread.sleep(1000);

        // 2. Sort by Price Low to High
        sortDropdown.selectByVisibleText("Price (low to high)");
        Thread.sleep(1000);

        // 3. Sort by Price High to Low
        sortDropdown.selectByVisibleText("Price (high to low)");
        Thread.sleep(1000);

        // 4. Sort A to Z
        sortDropdown.selectByVisibleText("Name (A to Z)");
        Thread.sleep(1000);
        Assert.assertEquals(sortDropdown.getFirstSelectedOption().getText(), "Name (A to Z)", "Should be sorted A to Z");

    }

    @AfterClass
    public void teardown() {
        driver.quit();
    }
}


