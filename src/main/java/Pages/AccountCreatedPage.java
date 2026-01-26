package Pages;

import BrowserDriver.BrowserDriver;
import Utility.PropertiesUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.IOException;
import java.time.Duration;

public class AccountCreatedPage {
    protected WebDriver driver;
    By continueBtn = By.xpath("//*[@id=\"form\"]/div/div/div/div/a");

    public AccountCreatedPage() throws IOException {
        driver = BrowserDriver.getDriver();
    }

    public void continueForMore(){
        driver.findElement(continueBtn).click();
    }
}
