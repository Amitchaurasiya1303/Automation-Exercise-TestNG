import Models.Payment;
import Models.Product;
import Models.User;
import Pages.*;
import Utility.DataContainer;
import Utility.PropertiesUtil;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

public class OrderConfirmationTest extends BaseTest {
    
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
    
    public OrderConfirmationTest() throws IOException {
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
    
    @BeforeMethod
    public void setup() throws IOException {
        driver.get(PropertiesUtil.getProperty("home_url"));
    }
    
    @Test(dataProvider = "getCompleteData", priority = 1)
    public void orderConfirmationTest(User user, Product product, Payment payment) throws IOException {
        // Complete flow to order confirmation
        homePage.navigateByBtn();
        signUpLogInPage.signUp(user);
        createAccountPage.enterAccountInformation(user);
        createAccountPage.enterAddressInformation(user);
        createAccountPage.clickOnCreateAccount();
        accountCreatedPage.continueForMore();
        
        productPage.searchProductByName(product);
        productPage.getProductResult(product);
        productPage.viewProduct();
        productDetailPage.addToCart();
        productDetailPage.goToCart();
        
        cartPage.proceedToCheckout();
        checkoutPage.addOrderComment("Test order");
        checkoutPage.placeOrder();
        
        paymentPage.fillPaymentDetails(payment);
        paymentPage.payAndConfirmOrder();
        
        // Verify order confirmation
        Assert.assertTrue(orderConfirmationPage.isOrderSuccessful(), "Order should be successful");
        
        String successMessage = orderConfirmationPage.getSuccessMessage();
        Assert.assertTrue(successMessage.contains("Congratulations"), "Success message should contain 'Congratulations'");
        
        System.out.println("✅ Order confirmed successfully: " + successMessage);
    }
    
    @DataProvider
    public Object[][] getCompleteData() {
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