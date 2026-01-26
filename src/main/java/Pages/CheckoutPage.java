package Pages;

import BrowserDriver.BrowserDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;

public class CheckoutPage {
    protected WebDriver driver;
    
    private By placeOrderBtn = By.xpath("//a[@href='/payment']");
    private By deliveryAddress = By.xpath("//ul[@id='address_delivery']");
    private By billingAddress = By.xpath("//ul[@id='address_invoice']");
    private By orderComment = By.xpath("//textarea[@name='message']");
    
    public CheckoutPage() throws IOException {
        driver = BrowserDriver.getDriver();
    }
    
    public void addOrderComment(String comment) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement commentBox = wait.until(ExpectedConditions.visibilityOfElementLocated(orderComment));
        commentBox.clear();
        commentBox.sendKeys(comment);
    }
    
    public void placeOrder() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement placeBtn = wait.until(ExpectedConditions.elementToBeClickable(placeOrderBtn));
        placeBtn.click();
    }
    
    public boolean isDeliveryAddressVisible() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.visibilityOfElementLocated(deliveryAddress));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}