package Pages;

import BrowserDriver.BrowserDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;

public class CartPage {
    protected WebDriver driver;
    
    private By proceedToCheckoutBtn = By.xpath("//a[@class='btn btn-default check_out']");
    private By cartItems = By.xpath("//tbody/tr");
    private By registerLoginLink = By.xpath("//u[text()='Register / Login']");
    private By cartTable = By.xpath("//table[@id='cart_info_table']");
    
    public CartPage() throws IOException {
        driver = BrowserDriver.getDriver();
    }
    
    public boolean isCartPageLoaded() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(cartTable));
            System.out.println("✅ Cart page loaded successfully");
            return true;
        } catch (Exception e) {
            System.out.println("❌ Cart page not loaded properly");
            return false;
        }
    }
    
    public void proceedToCheckout() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement checkoutBtn = wait.until(ExpectedConditions.elementToBeClickable(proceedToCheckoutBtn));
            checkoutBtn.click();
            
            // Wait a moment to see if we're redirected to login
            Thread.sleep(2000);
            
            // Check if we're on login page (user not logged in)
            if (driver.getCurrentUrl().contains("login")) {
                System.out.println("⚠️ User not logged in, redirected to login page");
                throw new RuntimeException("User must be logged in to proceed to checkout");
            }
            
            System.out.println("✅ Proceeded to checkout successfully");
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread interrupted during checkout");
        } catch (Exception e) {
            throw new RuntimeException("Failed to proceed to checkout: " + e.getMessage());
        }
    }
    
    public boolean hasItems() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.visibilityOfElementLocated(cartItems));
            return driver.findElements(cartItems).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }
    
    public void clickRegisterLogin() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement registerBtn = wait.until(ExpectedConditions.elementToBeClickable(registerLoginLink));
            registerBtn.click();
            System.out.println("✅ Navigated to Register/Login page");
        } catch (Exception e) {
            throw new RuntimeException("Failed to navigate to Register/Login: " + e.getMessage());
        }
    }
}