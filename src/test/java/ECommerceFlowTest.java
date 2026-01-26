import BrowserDriver.BrowserDriver;
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

public class ECommerceFlowTest extends BaseTest {
    
    private HomePage homePage;
    private SignUpLogInPage signUpLogInPage;
    private CreateAccountPage createAccountPage;
    private AccountCreatedPage accountCreatedPage;
    private UserLoggedInPage userLoggedInPage;
    private ProductPage productPage;
    private ProductDetailPage productDetailPage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;
    private PaymentPage paymentPage;
    private OrderConfirmationPage orderConfirmationPage;
    
    public ECommerceFlowTest() throws IOException {
        super();
        homePage = new HomePage();
        signUpLogInPage = new SignUpLogInPage();
        createAccountPage = new CreateAccountPage();
        accountCreatedPage = new AccountCreatedPage();
        userLoggedInPage = new UserLoggedInPage();
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
        verifyTitle(PropertiesUtil.getProperty("home_title"));
    }
    
    @Test(dataProvider = "getCompleteTestData", priority = 1)
    public void realWorldECommerceFlow(User user, Product product, Payment payment) throws InterruptedException, IOException {
        
        ExtentReportManager.createTest("Real-World E-Commerce Flow", "Complete user journey from registration to order confirmation");
        
        try {
            // PHASE 1: USER AUTHENTICATION
            ExtentReportManager.logInfo("=== PHASE 1: USER AUTHENTICATION ===");
            
            // Step 1: Go to Login/Signup page first (like real users do)
            ExtentReportManager.logInfo("Step 1: Navigating to Login/Signup page");
            homePage.navigateByBtn();
            verifyTitle(PropertiesUtil.getProperty("login_signup_title"));
            ExtentReportManager.logPass("Successfully navigated to Login/Signup page");
            
            // Step 2: Try to signup, handle existing user scenario
            ExtentReportManager.logInfo("Step 2: Attempting user registration/login");
            boolean userAuthenticated = signUpLogInPage.signUp(user);
            
            if (userAuthenticated && driver.getCurrentUrl().contains("signup")) {
                // New user - complete registration process
                ExtentReportManager.logInfo("New user detected - completing registration");
                verifyTitle(PropertiesUtil.getProperty("create_account_title"));
                createAccountPage.enterAccountInformation(user);
                createAccountPage.enterAddressInformation(user);
                createAccountPage.clickOnCreateAccount();
                verifyTitle(PropertiesUtil.getProperty("account_created_title"));
                accountCreatedPage.continueForMore();
                ExtentReportManager.logPass("New user account created and logged in successfully");
            } else if (userAuthenticated) {
                // Existing user logged in successfully
                ExtentReportManager.logPass("Existing user logged in successfully");
            } else {
                ExtentReportManager.logFail("User authentication failed");
                Assert.fail("User authentication failed - cannot proceed");
            }
            
            // PHASE 2: PRODUCT SHOPPING
            ExtentReportManager.logInfo("=== PHASE 2: PRODUCT SHOPPING ===");
            
            // Step 3: Navigate to products and search
            ExtentReportManager.logInfo("Step 3: Searching for product: " + product.getProductName());
            productPage.searchProductByName(product);
            productPage.getProductResult(product);
            productPage.viewProduct();
            ExtentReportManager.logPass("Product found and product details page opened");
            
            // Step 4: Add product to cart (critical step)
            ExtentReportManager.logInfo("Step 4: Adding product to cart");
            productDetailPage.addToCart();
            Thread.sleep(2000); // Wait for modal to appear
            ExtentReportManager.logPass("Product successfully added to cart");
            
            // Step 5: Navigate to cart (NO going back to home/products)
            ExtentReportManager.logInfo("Step 5: Going directly to cart page");
            productDetailPage.goToCart();
            
            // Verify we're on cart page and cart has items
            Assert.assertTrue(cartPage.isCartPageLoaded(), "Cart page should be loaded");
            Assert.assertTrue(cartPage.hasItems(), "Cart should contain the added product");
            ExtentReportManager.logPass("Successfully navigated to cart page with product");
            
            // PHASE 3: CHECKOUT PROCESS
            ExtentReportManager.logInfo("=== PHASE 3: CHECKOUT PROCESS ===");
            
            // Step 6: Proceed to checkout (user is already logged in)
            ExtentReportManager.logInfo("Step 6: Proceeding to checkout");
            cartPage.proceedToCheckout();
            ExtentReportManager.logPass("Successfully proceeded to checkout page");
            
            // Step 7: Review order details and place order
            ExtentReportManager.logInfo("Step 7: Reviewing order and placing order");
            checkoutPage.addOrderComment("Please handle with care - automated test order");
            checkoutPage.placeOrder();
            ExtentReportManager.logPass("Order placed successfully, proceeding to payment");
            
            // PHASE 4: PAYMENT AND CONFIRMATION
            ExtentReportManager.logInfo("=== PHASE 4: PAYMENT AND CONFIRMATION ===");
            
            // Step 8: Fill payment details and confirm
            ExtentReportManager.logInfo("Step 8: Processing payment");
            paymentPage.fillPaymentDetails(payment);
            paymentPage.payAndConfirmOrder();
            ExtentReportManager.logPass("Payment processed successfully");
            
            // Step 9: Verify order confirmation
            ExtentReportManager.logInfo("Step 9: Verifying order confirmation");
            Assert.assertTrue(orderConfirmationPage.isOrderSuccessful(), 
                "Order confirmation should be displayed");
            
            String successMessage = orderConfirmationPage.getSuccessMessage();
            Assert.assertTrue(successMessage.contains("Congratulations"), 
                "Success message should contain 'Congratulations'");
            
            ExtentReportManager.logPass("ORDER COMPLETED SUCCESSFULLY: " + successMessage);
            
            // Final success log
            System.out.println("\n" + "=".repeat(60));
            System.out.println("✅ COMPLETE E-COMMERCE FLOW SUCCESSFUL!");
            System.out.println("User: " + user.getName() + " (" + user.getEmail() + ")");
            System.out.println("Product: " + product.getProductName());
            System.out.println("Payment: " + payment.getNameOnCard());
            System.out.println("Order Status: " + successMessage);
            System.out.println("=".repeat(60));
            
        } catch (Exception e) {
            ExtentReportManager.logFail("E-Commerce flow failed: " + e.getMessage());
            System.out.println("❌ Test failed at: " + e.getMessage());
            throw e;
        }
    }
    
    @DataProvider
    public Object[][] getCompleteTestData() {
        Object[][] userData = DataContainer.getUsersData();
        Object[][] productData = DataContainer.getProducts();
        Object[][] paymentData = DataContainer.getPaymentData();
        
        Object[][] combinedData = new Object[1][3];
        combinedData[0][0] = userData[0][0]; // User
        combinedData[0][1] = productData[0][0]; // Product
        combinedData[0][2] = paymentData[0][0]; // Payment
        
        return combinedData;
    }
}