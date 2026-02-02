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

public class ShopFirstLoginLaterTest extends BaseTest {
    
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
    
    public ShopFirstLoginLaterTest() throws IOException {
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
        verifyTitle(PropertiesUtil.getProperty("home_title"));
    }
    
    @Test(dataProvider = "getCompleteTestData", priority = 1)
    public void shopFirstThenLoginFlow(User user, Product product, Payment payment) throws InterruptedException, IOException {
        
        ExtentReportManager.createTest("Shop First Then Login Flow", "User shops without login, then logs in during checkout");
        
        try {
            // PHASE 1: ANONYMOUS SHOPPING
            ExtentReportManager.logInfo("=== PHASE 1: ANONYMOUS SHOPPING ===");
            
            // Step 1: Go directly to products and search (like many users do)
            ExtentReportManager.logInfo("Step 1: Going directly to products page");
            productPage.navigateToProducts();
            ExtentReportManager.logPass("Navigated to products page");
            
            // Step 2: Search for product
            ExtentReportManager.logInfo("Step 2: Searching for product: " + product.getProductName());
            productPage.searchProductByName(product);
            productPage.getProductResult(product);
            productPage.viewProduct();
            ExtentReportManager.logPass("Product found and viewed");
            
            // Step 3: Add to cart without being logged in
            ExtentReportManager.logInfo("Step 3: Adding product to cart (anonymous user)");
            productDetailPage.addToCart();
            Thread.sleep(2000);
            productDetailPage.goToCart();
            
            Assert.assertTrue(cartPage.isCartPageLoaded(), "Cart page should load");
            Assert.assertTrue(cartPage.hasItems(), "Cart should have items");
            ExtentReportManager.logPass("Product added to cart successfully (anonymous)");
            
            // PHASE 2: CHECKOUT TRIGGERS LOGIN
            ExtentReportManager.logInfo("=== PHASE 2: CHECKOUT TRIGGERS LOGIN ===");
            
            // Step 4: Try to checkout - should redirect to login
            ExtentReportManager.logInfo("Step 4: Attempting checkout (will trigger login)");
            try {
                cartPage.proceedToCheckout();
                // If we reach here without exception, user might already be logged in
                ExtentReportManager.logPass("Proceeded to checkout directly");
            } catch (RuntimeException e) {
                if (e.getMessage().contains("must be logged in")) {
                    ExtentReportManager.logInfo("Redirected to login as expected");
                    
                    // Step 5: Handle login/signup
                    ExtentReportManager.logInfo("Step 5: Handling user authentication");
                    boolean userAuthenticated = signUpLogInPage.signUp(user);
                    
                    if (userAuthenticated && driver.getCurrentUrl().contains("signup")) {
                        // New user registration
                        createAccountPage.enterAccountInformation(user);
                        createAccountPage.enterAddressInformation(user);
                        createAccountPage.clickOnCreateAccount();
                        accountCreatedPage.continueForMore();
                        ExtentReportManager.logPass("New user registered and logged in");
                    } else if (userAuthenticated) {
                        ExtentReportManager.logPass("Existing user logged in");
                    } else {
                        throw new RuntimeException("Authentication failed");
                    }
                    
                    // Step 6: Go back to cart and proceed to checkout
                    ExtentReportManager.logInfo("Step 6: Returning to cart after login");
                    driver.get("https://automationexercise.com/view_cart");
                    cartPage.proceedToCheckout();
                    ExtentReportManager.logPass("Successfully proceeded to checkout after login");
                } else {
                    throw e;
                }
            }
            
            // PHASE 3: COMPLETE ORDER
            ExtentReportManager.logInfo("=== PHASE 3: COMPLETE ORDER ===");
            
            // Step 7: Complete checkout process
            ExtentReportManager.logInfo("Step 7: Completing order");
            checkoutPage.addOrderComment("Order placed after login during checkout");
            checkoutPage.placeOrder();
            ExtentReportManager.logPass("Order placed successfully");
            
            // Step 8: Payment
            ExtentReportManager.logInfo("Step 8: Processing payment");
            paymentPage.fillPaymentDetails(payment);
            paymentPage.payAndConfirmOrder();
            ExtentReportManager.logPass("Payment processed");
            
            // Step 9: Verify success
            ExtentReportManager.logInfo("Step 9: Verifying order confirmation");
            Assert.assertTrue(orderConfirmationPage.isOrderSuccessful(), "Order should be successful");
            
            String successMessage = orderConfirmationPage.getSuccessMessage();
            Assert.assertTrue(successMessage.contains("Congratulations"), "Should contain success message");
            
            ExtentReportManager.logPass("ORDER COMPLETED: " + successMessage);
            
            System.out.println("\\n" + "=".repeat(60));
            System.out.println("SHOP-FIRST-LOGIN-LATER FLOW SUCCESSFUL!");
            System.out.println("Flow: Anonymous Shopping → Login at Checkout → Order Complete");
            System.out.println("=" .repeat(60));
            
        } catch (Exception e) {
            ExtentReportManager.logFail("Shop-first flow failed: " + e.getMessage());
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