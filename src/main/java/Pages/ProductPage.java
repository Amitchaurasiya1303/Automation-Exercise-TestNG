package Pages;

import BrowserDriver.BrowserDriver;
import Models.Product;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class ProductPage {

    protected WebDriver driver;

    // Updated Locators with more robust selectors
    private By productsLink = By.xpath("//a[@href='/products']");
    private By searchBox = By.id("search_product");
    private By searchBtn = By.id("submit_search");
    private By productCards = By.xpath("//div[@class='product-image-wrapper']");
    private By productName = By.xpath(".//div[@class='productinfo text-center']//p");
    private By viewProductBtn = By.xpath(".//a[contains(@href,'/product_details/')]");
    private By searchResultsSection = By.xpath("//div[@class='features_items']");

    private WebElement productResult;

    public ProductPage() throws IOException {
        driver = BrowserDriver.getDriver();
    }

    // Navigate to products page first
    public void navigateToProducts() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        
        try {
            WebElement productsLinkElement = wait.until(ExpectedConditions.elementToBeClickable(productsLink));
            productsLinkElement.click();
        } catch (Exception e) {
            // Alternative navigation if direct link fails
            driver.get("https://automationexercise.com/products");
        }

        // Wait for products page to load
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchBox));
    }

    // Search product by name with improved error handling
    public void searchProductByName(Product product) {
        // First navigate to products page
        navigateToProducts();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        
        try {
            // Wait for search box to be visible and interactable
            WebElement searchInput = wait.until(ExpectedConditions.elementToBeClickable(searchBox));
            
            // Clear any existing text and enter search term
            searchInput.clear();
            try {
                Thread.sleep(500); // Small delay to ensure clear is complete
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            searchInput.sendKeys(product.getProductName());
            
            // Click search button
            WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(searchBtn));
            searchButton.click();
            
            // Wait for search results to load
            wait.until(ExpectedConditions.visibilityOfElementLocated(searchResultsSection));
            try {
                Thread.sleep(2000); // Allow time for results to fully load
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to search for product: " + product.getProductName() + ". Error: " + e.getMessage());
        }
    }

    // Get the specific product from search results
    public void getProductResult(Product product) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        
        try {
            // Wait for product cards to be visible
            List<WebElement> cards = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(productCards));
            
            System.out.println("Found " + cards.size() + " product cards");
            
            for (WebElement card : cards) {
                try {
                    WebElement nameElement = card.findElement(productName);
                    String name = nameElement.getText().trim();
                    System.out.println("Checking product: " + name);
                    
                    if (name.toLowerCase().contains(product.getProductName().toLowerCase())) {
                        productResult = card;
                        System.out.println("Found matching product: " + name);
                        return;
                    }
                } catch (NoSuchElementException e) {
                    System.out.println("Could not find product name in card, skipping...");
                    continue;
                }
            }
            
            throw new RuntimeException("Product not found in search results: " + product.getProductName());
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to get product result: " + e.getMessage());
        }
    }

    // Click on "View Product" with improved reliability
    public void viewProduct() {
        if (productResult == null) {
            throw new IllegalStateException("Product result not set. Call getProductResult() first.");
        }

        try {
            WebElement viewBtn = productResult.findElement(viewProductBtn);

            // Scroll to the button before clicking
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView({block:'center'});", viewBtn);
            
            try {
                Thread.sleep(1000); // Allow scroll to complete
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            // Wait until clickable and click
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.elementToBeClickable(viewBtn));

            // Try regular click first, fallback to JavaScript click if needed
            try {
                viewBtn.click();
            } catch (ElementClickInterceptedException e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", viewBtn);
            }
            
            System.out.println("✅ Successfully clicked View Product button");
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to click View Product button: " + e.getMessage());
        }
    }
}