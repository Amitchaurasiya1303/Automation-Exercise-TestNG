import Models.Product;
import Pages.ProductDetailPage;
import Pages.ProductPage;
import Utility.DataContainer;
import Utility.ExtentReportManager;
import Utility.PropertiesUtil;
import org.testng.annotations.*;

import java.io.IOException;

public class QuickTest extends BaseTest {
    
    private ProductPage productPage;
    private ProductDetailPage productDetailPage;
    
    public QuickTest() throws IOException {
        super();
        productPage = new ProductPage();
        productDetailPage = new ProductDetailPage();
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
    
    @Test(dataProvider = "getProductData", priority = 1)
    public void quickCartTest(Product product) throws InterruptedException {
        
        ExtentReportManager.createTest("Quick Cart Test", "Test product search and add to cart functionality");
        
        try {
            ExtentReportManager.logInfo("Starting product search for: " + product.getProductName());
            
            // Search and add product to cart
            productPage.searchProductByName(product);
            productPage.getProductResult(product);
            productPage.viewProduct();
            
            ExtentReportManager.logPass("Product found and viewed successfully");
            
            // Add to cart and navigate
            productDetailPage.addToCart();
            Thread.sleep(3000); // Wait for modal
            productDetailPage.goToCart();
            
            ExtentReportManager.logPass("Product added to cart and navigated to cart page");
            System.out.println("✅ Quick test completed - Product added to cart successfully!");
            
        } catch (Exception e) {
            ExtentReportManager.logFail("Test failed: " + e.getMessage());
            throw e;
        }
    }
    
    @DataProvider
    public Object[][] getProductData() {
        return DataContainer.getProducts();
    }
}