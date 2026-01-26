import Models.Payment;
import Models.Product;
import Pages.CartPage;
import Pages.ProductDetailPage;
import Pages.ProductPage;
import Utility.DataContainer;
import Utility.PropertiesUtil;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

public class CartTest extends BaseTest {
    
    private ProductPage productPage;
    private ProductDetailPage productDetailPage;
    private CartPage cartPage;
    
    public CartTest() throws IOException {
        super();
        productPage = new ProductPage();
        productDetailPage = new ProductDetailPage();
        cartPage = new CartPage();
    }
    
    @BeforeMethod
    public void setup() throws IOException {
        driver.get(PropertiesUtil.getProperty("home_url"));
    }
    
    @Test(dataProvider = "getProductData", priority = 1)
    public void addProductToCartTest(Product product) {
        // Search and add product to cart
        productPage.searchProductByName(product);
        productPage.getProductResult(product);
        productPage.viewProduct();
        
        productDetailPage.addToCart();
        productDetailPage.goToCart();
        
        // Verify cart is not empty
        Assert.assertTrue(cartPage.hasItems(), "Cart should contain the added product");
        
        System.out.println("✅ Product successfully added to cart: " + product.getProductName());
    }
    
    @DataProvider
    public Object[][] getProductData() {
        return DataContainer.getProducts();
    }
}