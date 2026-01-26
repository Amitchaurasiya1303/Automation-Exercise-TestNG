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

public class CheckoutTest extends BaseTest {
    
    private HomePage homePage;
    private SignUpLogInPage signUpLogInPage;
    private CreateAccountPage createAccountPage;
    private AccountCreatedPage accountCreatedPage;
    private ProductPage productPage;
    private ProductDetailPage productDetailPage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;
    
    public CheckoutTest() throws IOException {
        super();
        homePage = new HomePage();
        signUpLogInPage = new SignUpLogInPage();
        createAccountPage = new CreateAccountPage();
        accountCreatedPage = new AccountCreatedPage();
        productPage = new ProductPage();
        productDetailPage = new ProductDetailPage();
        cartPage = new CartPage();
        checkoutPage = new CheckoutPage();
    }
    
    @BeforeMethod
    public void setup() throws IOException {
        driver.get(PropertiesUtil.getProperty("home_url"));
    }
    
    @Test(dataProvider = "getTestData", priority = 1)
    public void checkoutProcessTest(User user, Product product) throws IOException {
        // Register user
        homePage.navigateByBtn();
        signUpLogInPage.signUp(user);
        createAccountPage.enterAccountInformation(user);
        createAccountPage.enterAddressInformation(user);
        createAccountPage.clickOnCreateAccount();
        accountCreatedPage.continueForMore();
        
        // Add product to cart
        productPage.searchProductByName(product);
        productPage.getProductResult(product);
        productPage.viewProduct();
        productDetailPage.addToCart();
        productDetailPage.goToCart();
        
        // Proceed to checkout
        cartPage.proceedToCheckout();
        
        // Verify checkout page
        Assert.assertTrue(checkoutPage.isDeliveryAddressVisible(), "Delivery address should be visible");
        
        checkoutPage.addOrderComment("Test order comment");
        checkoutPage.placeOrder();
        
        System.out.println("✅ Checkout process completed successfully");
    }
    
    @DataProvider
    public Object[][] getTestData() {
        Object[][] userData = DataContainer.getUsersData();
        Object[][] productData = DataContainer.getProducts();
        
        Object[][] combinedData = new Object[1][2];
        combinedData[0][0] = userData[0][0];
        combinedData[0][1] = productData[0][0];
        
        return combinedData;
    }
}