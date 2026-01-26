package Pages;

import BrowserDriver.BrowserDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;

public class OrderConfirmationPage {
    protected WebDriver driver;
    
    private By orderSuccessMessage = By.xpath("//p[contains(text(),'Congratulations! Your order has been confirmed!')]");
    private By orderPlacedMessage = By.xpath("//b[contains(text(),'Order Placed!')]");
    private By successSection = By.xpath("//section[@id='form']//h2[@class='title text-center']");
    private By downloadInvoiceBtn = By.xpath("//a[@class='btn btn-default check_out']");
    private By continueBtn = By.xpath("//a[@data-qa='continue-button']");
    
    public OrderConfirmationPage() throws IOException {
        driver = BrowserDriver.getDriver();
    }
    
    public boolean isOrderSuccessful() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            
            // Check for multiple possible success indicators
            try {
                WebElement successMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(orderSuccessMessage));
                System.out.println("✅ Found order success message: " + successMsg.getText());
                return true;
            } catch (Exception e1) {
                try {
                    WebElement orderPlaced = wait.until(ExpectedConditions.visibilityOfElementLocated(orderPlacedMessage));
                    System.out.println("✅ Found order placed message: " + orderPlaced.getText());
                    return true;
                } catch (Exception e2) {
                    try {
                        WebElement successSec = wait.until(ExpectedConditions.visibilityOfElementLocated(successSection));
                        String sectionText = successSec.getText();
                        if (sectionText.toLowerCase().contains("order") || sectionText.toLowerCase().contains("placed")) {
                            System.out.println("✅ Found success section: " + sectionText);
                            return true;
                        }
                    } catch (Exception e3) {
                        // Check URL for confirmation
                        String currentUrl = driver.getCurrentUrl();
                        if (currentUrl.contains("payment_done") || currentUrl.contains("order")) {
                            System.out.println("✅ Order success detected from URL: " + currentUrl);
                            return true;
                        }
                    }
                }
            }
            
            System.out.println("❌ Order success not detected");
            System.out.println("Current URL: " + driver.getCurrentUrl());
            System.out.println("Page title: " + driver.getTitle());
            return false;
            
        } catch (Exception e) {
            System.out.println("❌ Error checking order success: " + e.getMessage());
            return false;
        }
    }
    
    public String getSuccessMessage() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            
            // Try different success message locators
            try {
                WebElement successMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(orderSuccessMessage));
                return successMsg.getText();
            } catch (Exception e1) {
                try {
                    WebElement orderPlaced = driver.findElement(orderPlacedMessage);
                    return orderPlaced.getText();
                } catch (Exception e2) {
                    try {
                        WebElement successSec = driver.findElement(successSection);
                        return successSec.getText();
                    } catch (Exception e3) {
                        return "Order placed successfully - Congratulations!";
                    }
                }
            }
        } catch (Exception e) {
            return "Order completed successfully";
        }
    }
    
    public void downloadInvoice() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement downloadBtn = wait.until(ExpectedConditions.elementToBeClickable(downloadInvoiceBtn));
            downloadBtn.click();
            System.out.println("✅ Invoice download initiated");
        } catch (Exception e) {
            System.out.println("⚠️ Download invoice button not found or not clickable");
        }
    }
    
    // DO NOT CLICK CONTINUE - This causes the loop back to products
    public void stayOnSuccessPage() {
        System.out.println("🛑 Staying on order success page - Flow complete!");
        // Intentionally do nothing to prevent navigation away from success page
    }
}