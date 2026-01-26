package Utility;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExtentReportManager {
    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    
    public static void initReports() {
        if (extent == null) {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
            String reportPath = System.getProperty("user.dir") + "/test-reports/ExtentReport_" + timestamp + ".html";
            
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
            sparkReporter.config().setDocumentTitle("E-Commerce Automation Test Report");
            sparkReporter.config().setReportName("Automation Exercise Test Results");
            sparkReporter.config().setTheme(Theme.STANDARD);
            
            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
            extent.setSystemInfo("Application", "Automation Exercise");
            extent.setSystemInfo("Environment", "Test");
            extent.setSystemInfo("Browser", "Chrome");
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        }
    }
    
    public static void createTest(String testName, String description) {
        ExtentTest extentTest = extent.createTest(testName, description);
        test.set(extentTest);
    }
    
    public static ExtentTest getTest() {
        return test.get();
    }
    
    public static void flushReports() {
        if (extent != null) {
            extent.flush();
        }
    }
    
    public static void logInfo(String message) {
        getTest().info(message);
    }
    
    public static void logPass(String message) {
        getTest().pass(message);
    }
    
    public static void logFail(String message) {
        getTest().fail(message);
    }
    
    public static void logSkip(String message) {
        getTest().skip(message);
    }
}