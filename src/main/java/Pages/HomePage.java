package Pages;

import BrowserDriver.BrowserDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import java.io.IOException;

public class HomePage {
    protected WebDriver driver;
    By loginSignUpPageNav = By.xpath("//*[@id=\"header\"]/div/div/div/div[2]/div/ul/li[4]/a");

    public HomePage() throws IOException {
        driver = BrowserDriver.getDriver();
    }

    public void navigateByBtn(){
        driver.findElement(loginSignUpPageNav).click();
    }
}
