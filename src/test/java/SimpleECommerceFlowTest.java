import Models.Payment;
import Models.Product;
import Models.User;
import Pages.*;
import Utility.DataContainer;
import Utility.ExtentReportManager;
import Utility.PropertiesUtil;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;

public class SimpleECommerceFlowTest extends BaseTest {
    
    private HomePage homePage;
    private SignUpLogInPage signUpLogInPage;
    private CreateAccountPage createAccountPage;
    private AccountCreatedPage accountCreatedPage;
    private ProductPage productPage;
    private ProductDetailPage productDetailPage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;
    private PaymentPage paymentPage;
    private OrderConfirmationPage orderConfirmationPage;
    
    public SimpleECommerceFlowTest() throws IOException {
        super();
        homePage = new HomePage();
        signUpLogInPage = new SignUpLogInPage();
        createAccountPage = new CreateAccountPage();
        accountCreatedPage = new AccountCreatedPage();
        productPage = new ProductPage();
        productDetailPage = new ProductDetailPage();
        cartPage = new CartPage();
        checkoutPage = new CheckoutPage();
        paymentPage = new PaymentPage();
        orderConfirmationPage = new OrderConfirmationPage();
    }
    
    @BeforeSuite
    public void setupReports() {
        ExtentReportManager.initReports();
    }
    
    @AfterSuite
    public void tearDownReports() {
        ExtentReportManager.flushReports();
    }
    
    @BeforeMethod
    public void setup() throws IOException {
        driver.get(PropertiesUtil.getProperty("home_url"));
    }
    
    @AfterMethod
    public void cleanup() {
        try {
            // Logout and close browser after each test
            if (driver != null) {
                System.out.println("Logging out and closing browser...");
                driver.quit();
                System.out.println("Browser closed successfully");
            }
        } catch (Exception e) {
            System.out.println("Error during cleanup: " + e.getMessage());
        }
    }
    
    @Test(dataProvider = "getCompleteTestData", priority = 1)
    public void simpleOrderFlow(User user, Product product, Payment payment) throws InterruptedException, IOException {
        
        ExtentReportManager.createTest("Simple E-Commerce Order Flow", "Linear flow: Login → Shop → Order → Success → Logout");
        
        try {
            System.out.println("\nStarting Simple E-Commerce Flow...");
            
            // Step 1: Login/Signup
            System.out.println("1. Going to Login/Signup page...");
            homePage.navigateByBtn();
            
            boolean userAuthenticated = signUpLogInPage.signUp(user);
            if (userAuthenticated && driver.getCurrentUrl().contains("signup")) {
                System.out.println("New user - completing registration...");
                createAccountPage.enterAccountInformation(user);
                createAccountPage.enterAddressInformation(user);
                createAccountPage.clickOnCreateAccount();
                accountCreatedPage.continueForMore();
                System.out.println("User registered and logged in");
            } else if (userAuthenticated) {
                System.out.println("Existing user logged in");
            } else {
                throw new RuntimeException("Login failed");
            }
            
            // Step 2: Search and view product
            System.out.println("2. Searching for product: " + product.getProductName());
            productPage.searchProductByName(product);
            productPage.getProductResult(product);
            productPage.viewProduct();
            System.out.println("Product found and viewed");
            
            // Step 3: Add to cart
            System.out.println("3. Adding product to cart...");
            productDetailPage.addToCart();
            Thread.sleep(2000);
            productDetailPage.goToCart();
            System.out.println("Product added to cart");
            
            // Step 4: Checkout
            System.out.println("4. Proceeding to checkout...");
            cartPage.proceedToCheckout();
            checkoutPage.addOrderComment("Simple test order");
            checkoutPage.placeOrder();
            System.out.println("Order placed");
            
            // Step 5: Payment
            System.out.println("5. Processing payment...");
            paymentPage.fillPaymentDetails(payment);
            paymentPage.payAndConfirmOrder();
            System.out.println("Payment completed");
            
            // Step 6: Verify order success and STOP HERE
            System.out.println("6. Verifying order success...");
            
            // Wait for order confirmation page to load
            Thread.sleep(3000);
            
            boolean orderSuccessful = orderConfirmationPage.isOrderSuccessful();
            if (orderSuccessful) {
                String successMessage = orderConfirmationPage.getSuccessMessage();
                System.out.println("ORDER SUCCESSFUL: " + successMessage);
                
                Assert.assertTrue(successMessage.contains("Congratulations"), 
                    "Success message should contain 'Congratulations'");
                
                ExtentReportManager.logPass("Order completed successfully: " + successMessage);
                
                // STOP HERE - DO NOT NAVIGATE ANYWHERE ELSE
                System.out.println("Staying on success page - Order flow complete!");
                
            } else {
                throw new RuntimeException("Order confirmation not found");
            }
            
            // Final success summary
            System.out.println("\n" + "=".repeat(50));
            System.out.println("SIMPLE E-COMMERCE FLOW COMPLETED");
            System.out.println("User: " + user.getEmail());
            System.out.println("Product: " + product.getProductName());
            System.out.println("Status: ORDER SUCCESSFUL");
            System.out.println("=".repeat(50));
            
        } catch (Exception e) {
            ExtentReportManager.logFail("Simple flow failed: " + e.getMessage());
            System.out.println("Flow failed: " + e.getMessage());
            throw e;
        }
    }
    
    @DataProvider
    public Object[][] getCompleteTestData() {
        Object[][] userData = DataContainer.getUsersData();
        Object[][] productData = DataContainer.getProducts();
        Object[][] paymentData = DataContainer.getPaymentData();
        
        Object[][] combinedData = new Object[1][3];
        combinedData[0][0] = userData[0][0];
        combinedData[0][1] = productData[0][0];
        combinedData[0][2] = paymentData[0][0];
        
        return combinedData;
    }
}