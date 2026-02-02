import BrowserDriver.BrowserDriver;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.io.IOException;

public class BaseTest {
    protected static WebDriver driver;

    BaseTest() throws IOException {
        driver = BrowserDriver.getDriver();
    }

    public static void verifyTitle(String expectedTitle){
        String actualTitle = driver.getTitle();
        Assert.assertEquals(actualTitle,expectedTitle,"Home page title mismatch!");
    }
}
