package Pages;

import BrowserDriver.BrowserDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;

public class ProductDetailPage {
    protected WebDriver driver;
    
    private By addToCartBtn = By.xpath("//button[@class='btn btn-default cart']");
    private By addedToCartModal = By.xpath("//div[@class='modal-content']");
    private By viewCartLink = By.xpath("//u[text()='View Cart']");
    private By continueShoppingBtn = By.xpath("//button[@class='btn btn-success close-modal btn-block']");
    
    public ProductDetailPage() throws IOException {
        driver = BrowserDriver.getDriver();
    }
    
    public void addToCart() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement addBtn = wait.until(ExpectedConditions.elementToBeClickable(addToCartBtn));
            
            // Scroll to button and click
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addBtn);
            Thread.sleep(1000);
            addBtn.click();
            
            // Wait for modal to appear
            wait.until(ExpectedConditions.visibilityOfElementLocated(addedToCartModal));
            System.out.println("✅ Product added to cart successfully");
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to add product to cart: " + e.getMessage());
        }
    }
    
    public void goToCart() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            
            // Click View Cart from the modal
            WebElement viewCartBtn = wait.until(ExpectedConditions.elementToBeClickable(viewCartLink));
            viewCartBtn.click();
            
            // Wait for cart page to load
            Thread.sleep(2000);
            System.out.println("✅ Navigated to cart page");
            
        } catch (Exception e) {
            // Fallback: Direct navigation to cart
            System.out.println("⚠️ Modal View Cart failed, navigating directly to cart");
            driver.get("https://automationexercise.com/view_cart");
        }
    }
    
    public void continueShopping() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement continueBtn = wait.until(ExpectedConditions.elementToBeClickable(continueShoppingBtn));
            continueBtn.click();
            System.out.println("✅ Continued shopping");
        } catch (Exception e) {
            System.out.println("⚠️ Continue shopping button not found");
        }
    }
}