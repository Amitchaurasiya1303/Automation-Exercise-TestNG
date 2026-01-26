package Pages;

import BrowserDriver.BrowserDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.io.IOException;

public class UserLoggedInPage {
    protected WebDriver driver;
    By products = By.cssSelector("a[href='/products']");
    By logout   = By.cssSelector("a[href='/logout']");

    public UserLoggedInPage() throws IOException {
        driver = BrowserDriver.getDriver();
    }

    public void isLoggedIn(){
        Assert.assertTrue(driver.findElement(logout).isDisplayed(),"User should be logged in, but logout button not visible");
    }

    public void navigateToProducts() {
        driver.findElement(products).click();
    }
}
