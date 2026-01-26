package BrowserDriver;
import Utility.PropertiesUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.IOException;

public class BrowserDriver {
    private static WebDriver driver = null;

    private BrowserDriver(){
    }

    public static WebDriver getDriver() throws IOException {
        if(driver==null){
            String browser = PropertiesUtil.getProperty("browser");
            switch(browser.toLowerCase()) {
                case "chrome":
                    driver = new ChromeDriver();
                    break;
                case "firefox":
                    driver = new FirefoxDriver();
                    break;
                case "edge":
                    driver = new EdgeDriver();
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported browser: " + browser);
            }
        }
        return driver;
    }

    public static void quitDriver(){
       if(driver!=null) {
           driver.quit();
           driver = null;
       }
    }
}
