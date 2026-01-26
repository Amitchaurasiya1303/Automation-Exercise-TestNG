import Pages.HomePage;
import Utility.PropertiesUtil;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.io.IOException;

public class HomeTest extends BaseTest{
    HomePage homePage;

    HomeTest() throws IOException {
        homePage = new HomePage();
    }

    @BeforeMethod
    public void navigateTo() throws IOException {
        driver.get(PropertiesUtil.getProperty("home_url"));
        driver.manage().window().maximize();
    }

//    @Test(priority = 1)
//    public void checkPageTitle() throws IOException {
//        BaseTest.verifyTitle(PropertiesUtil.getProperty("home_title"));
//    }

    @Test(priority = 2)
    public void navigateToSignUpPage(){
        homePage.navigateByBtn();
    }
}
