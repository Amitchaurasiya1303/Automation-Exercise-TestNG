import Models.Product;
import Pages.ProductDetailPage;
import Pages.ProductPage;
import Utility.DataContainer;
import Utility.PropertiesUtil;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

public class ProductTest extends BaseTest {
    ProductPage productPage;
    ProductDetailPage productDetailPage;

    ProductTest() throws IOException {
        super();
        productPage = new ProductPage();
        productDetailPage = new ProductDetailPage();
    }
    
    @BeforeMethod
    public void setup() throws IOException {
        driver.get(PropertiesUtil.getProperty("home_url"));
    }

    @Test(dataProvider = "getData" , priority = 1)
    public void searchAndViewProduct(Product product) {
        if(product==null){
            System.out.println("Skipping null product");
            return;
        }
        
        // Search for product
        productPage.searchProductByName(product);
        productPage.getProductResult(product);
        productPage.viewProduct();
        
        System.out.println("✅ Successfully searched and viewed product: " + product.getProductName());
    }
    
    @Test(dataProvider = "getData" , priority = 2)
    public void searchViewAndAddToCart(Product product) {
        if(product==null){
            System.out.println("Skipping null product");
            return;
        }
        
        // Search and view product
        productPage.searchProductByName(product);
        productPage.getProductResult(product);
        productPage.viewProduct();
        
        // Add to cart
        productDetailPage.addToCart();
        productDetailPage.continueShopping();
        
        System.out.println("✅ Successfully added product to cart: " + product.getProductName());
    }

    @DataProvider
    public Object[][] getData(){
        return DataContainer.getProducts();
    }
}
