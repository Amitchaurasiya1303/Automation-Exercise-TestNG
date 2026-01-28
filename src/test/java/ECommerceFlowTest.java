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

/**
 * Complete E-Commerce flow test - Login → Shop → Cart → Checkout → Payment → Success
 * Author: Amit Chaurasiya
 */
public class ECommerceFlowTest extends BaseTest {
    
    // Page objects
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
            // Phase 1: User Authentication
            ExtentReportManager.logInfo("=== PHASE 1: USER AUTHENTICATION ===");
            
            homePage.navigateByBtn();
            verifyTitle(PropertiesUtil.getProperty("login_signup_title"));
            ExtentReportManager.logPass("Successfully navigated to Login/Signup page");
            
            boolean userAuthenticated = signUpLogInPage.signUp(user);
            
            if (userAuthenticated && driver.getCurrentUrl().contains("signup")) {
                // New user registration
                ExtentReportManager.logInfo("New user detected - completing registration");
                verifyTitle(PropertiesUtil.getProperty("create_account_title"));
                createAccountPage.enterAccountInformation(user);
                createAccountPage.enterAddressInformation(user);
                createAccountPage.clickOnCreateAccount();
                verifyTitle(PropertiesUtil.getProperty("account_created_title"));
                accountCreatedPage.continueForMore();
                ExtentReportManager.logPass("New user account created and logged in successfully");
            } else if (userAuthenticated) {
                ExtentReportManager.logPass("Existing user logged in successfully");
            } else {
                ExtentReportManager.logFail("User authentication failed");
                Assert.fail("User authentication failed - cannot proceed");
            }
            
            // Phase 2: Product Shopping
            ExtentReportManager.logInfo("=== PHASE 2: PRODUCT SHOPPING ===");
            
            ExtentReportManager.logInfo("Searching for product: " + product.getProductName());
            productPage.searchProductByName(product);
            productPage.getProductResult(product);
            productPage.viewProduct();
            ExtentReportManager.logPass("Product found and product details page opened");
            
            ExtentReportManager.logInfo("Adding product to cart");
            productDetailPage.addToCart();
            Thread.sleep(2000);
            ExtentReportManager.logPass("Product successfully added to cart");
            
            ExtentReportManager.logInfo("Going directly to cart page");
            productDetailPage.goToCart();
            
            Assert.assertTrue(cartPage.isCartPageLoaded(), "Cart page should be loaded");
            Assert.assertTrue(cartPage.hasItems(), "Cart should contain the added product");
            ExtentReportManager.logPass("Successfully navigated to cart page with product");
            
            // Phase 3: Checkout Process
            ExtentReportManager.logInfo("=== PHASE 3: CHECKOUT PROCESS ===");
            
            ExtentReportManager.logInfo("Proceeding to checkout");
            cartPage.proceedToCheckout();
            ExtentReportManager.logPass("Successfully proceeded to checkout page");
            
            ExtentReportManager.logInfo("Reviewing order and placing order");
            checkoutPage.addOrderComment("Please handle with care - automated test order");
            checkoutPage.placeOrder();
            ExtentReportManager.logPass("Order placed successfully, proceeding to payment");
            
            // Phase 4: Payment and Confirmation
            ExtentReportManager.logInfo("=== PHASE 4: PAYMENT AND CONFIRMATION ===");
            
            ExtentReportManager.logInfo("Processing payment");
            paymentPage.fillPaymentDetails(payment);
            paymentPage.payAndConfirmOrder();
            ExtentReportManager.logPass("Payment processed successfully");
            
            ExtentReportManager.logInfo("Verifying order confirmation");
            Assert.assertTrue(orderConfirmationPage.isOrderSuccessful(), 
                "Order confirmation should be displayed");
            
            String successMessage = orderConfirmationPage.getSuccessMessage();
            Assert.assertTrue(successMessage.contains("Congratulations"), 
                "Success message should contain 'Congratulations'");
            
            ExtentReportManager.logPass("ORDER COMPLETED SUCCESSFULLY: " + successMessage);
            
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