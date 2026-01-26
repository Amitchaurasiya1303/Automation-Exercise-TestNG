package Pages;

import BrowserDriver.BrowserDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;

public class ActionPage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public ActionPage() throws IOException {
        this.driver = BrowserDriver.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void waitForElementVisibility(By elementName){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(elementName));
    }

    public void scrollPage(){
       JavascriptExecutor executor = (JavascriptExecutor) driver;
       executor.executeScript("window.scrollBy(0, 500)");
    }

//    protected void clickElement(By locator) {
//        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
//    }
}
