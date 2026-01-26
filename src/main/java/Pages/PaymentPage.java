package Pages;

import BrowserDriver.BrowserDriver;
import Models.Payment;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;

public class PaymentPage {
    protected WebDriver driver;
    
    private By nameOnCardField = By.xpath("//input[@name='name_on_card']");
    private By cardNumberField = By.xpath("//input[@name='card_number']");
    private By cvcField = By.xpath("//input[@name='cvc']");
    private By expiryMonthField = By.xpath("//input[@name='expiry_month']");
    private By expiryYearField = By.xpath("//input[@name='expiry_year']");
    private By payAndConfirmBtn = By.xpath("//button[@id='submit']");
    
    public PaymentPage() throws IOException {
        driver = BrowserDriver.getDriver();
    }
    
    public void fillPaymentDetails(Payment payment) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        WebElement nameField = wait.until(ExpectedConditions.visibilityOfElementLocated(nameOnCardField));
        nameField.clear();
        nameField.sendKeys(payment.getNameOnCard());
        
        WebElement cardField = driver.findElement(cardNumberField);
        cardField.clear();
        cardField.sendKeys(payment.getCardNumber());
        
        WebElement cvcFieldElement = driver.findElement(cvcField);
        cvcFieldElement.clear();
        cvcFieldElement.sendKeys(payment.getCvc());
        
        WebElement monthField = driver.findElement(expiryMonthField);
        monthField.clear();
        monthField.sendKeys(payment.getExpiryMonth());
        
        WebElement yearField = driver.findElement(expiryYearField);
        yearField.clear();
        yearField.sendKeys(payment.getExpiryYear());
    }
    
    public void payAndConfirmOrder() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement payBtn = wait.until(ExpectedConditions.elementToBeClickable(payAndConfirmBtn));
        payBtn.click();
    }
}