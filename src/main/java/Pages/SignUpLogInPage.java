package Pages;

import BrowserDriver.BrowserDriver;
import Models.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;

public class SignUpLogInPage extends ActionPage {
    protected WebDriver driver;
    
    // Sign Up elements
    By signUpName = By.xpath("//input[@name='name']");
    By signUpEmail = By.xpath("//input[@data-qa='signup-email']");
    By signUpBtn = By.xpath("//button[@data-qa='signup-button']");
    
    // Login elements
    By loginEmail = By.xpath("//input[@data-qa='login-email']");
    By loginPassword = By.xpath("//input[@data-qa='login-password']");
    By loginBtn = By.xpath("//button[@data-qa='login-button']");
    
    // Error messages
    By signUpError = By.xpath("//p[contains(text(),'Email Address already exist!')]");
    By loginError = By.xpath("//p[contains(text(),'Your email or password is incorrect!')]");

    public SignUpLogInPage() throws IOException {
        driver = BrowserDriver.getDriver();
    }

    public boolean signUp(User user) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            
            WebElement nameField = wait.until(ExpectedConditions.visibilityOfElementLocated(signUpName));
            nameField.clear();
            nameField.sendKeys(user.getName());
            
            WebElement emailField = driver.findElement(signUpEmail);
            emailField.clear();
            emailField.sendKeys(user.getEmail());
            
            driver.findElement(signUpBtn).click();
            
            // Check if email already exists
            try {
                Thread.sleep(2000);
                if (driver.findElements(signUpError).size() > 0) {
                    System.out.println("⚠️ Email already exists, trying to login...");
                    return loginUser(user);
                }
            } catch (Exception e) {
                // Continue with sign up
            }
            
            System.out.println("✅ Sign up form submitted");
            return true;
            
        } catch (Exception e) {
            System.out.println("❌ Sign up failed: " + e.getMessage());
            return loginUser(user);
        }
    }
    
    public boolean loginUser(User user) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            
            WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(loginEmail));
            emailField.clear();
            emailField.sendKeys(user.getEmail());
            
            WebElement passwordField = driver.findElement(loginPassword);
            passwordField.clear();
            passwordField.sendKeys(user.getPassword());
            
            driver.findElement(loginBtn).click();
            
            Thread.sleep(2000);
            
            // Check for login error
            if (driver.findElements(loginError).size() > 0) {
                System.out.println("❌ Login failed - incorrect credentials");
                return false;
            }
            
            System.out.println("✅ User logged in successfully");
            return true;
            
        } catch (Exception e) {
            System.out.println("❌ Login failed: " + e.getMessage());
            return false;
        }
    }
}