import Models.Product;
import Pages.ProductDetailPage;
import Pages.ProductPage;
import Utility.DataContainer;
import Utility.PropertiesUtil;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

public class ProductDetailTest extends BaseTest {
    
    private ProductPage productPage;
    private ProductDetailPage productDetailPage;
    
    public ProductDetailTest() throws IOException {
        super();
        productPage = new ProductPage();
        productDetailPage = new ProductDetailPage();
    }
    
    @BeforeMethod
    public void setup() throws IOException {
        driver.get(PropertiesUtil.getProperty("home_url"));
    }
    
    @Test(dataProvider = "getProductData", priority = 1)
    public void addToCartTest(Product product) {
        productPage.searchProductByName(product);
        productPage.getProductResult(product);
        productPage.viewProduct();
        
        productDetailPage.addToCart();
        productDetailPage.continueShopping();
        
        System.out.println("✅ Product added to cart: " + product.getProductName());
    }
    
    @DataProvider
    public Object[][] getProductData() {
        return DataContainer.getProducts();
    }
}