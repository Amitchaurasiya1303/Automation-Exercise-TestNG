package Pages;

import BrowserDriver.BrowserDriver;
import Models.User;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;

public class CreateAccountPage extends ActionPage {
    protected WebDriver driver;
    By titleMr = By.id("uniform-id_gender1");
    By titleMrs = By.id("uniform-id_gender2");
    By password = By.id("password");
    By dayDropdown = By.id("days");
    By monthDropdown = By.id("months");
    By yearDropdown = By.id("years");
    By firstName = By.id("first_name");
    By lastName = By.id("last_name");
    By address = By.id("address1");
    By countryDropdown = By.id("country");
    By state = By.id("state");
    By city = By.id("city");
    By zipcode = By.id("zipcode");
    By mobile_number = By.id("mobile_number");
    By createAccountBtn = By.xpath("//*[@id=\"form\"]/div/div/div/div[1]/form/button");

    public CreateAccountPage() throws IOException {
        driver = BrowserDriver.getDriver();
    }

    public void enterAccountInformation(User user){
        selectTitle(user);
        driver.findElement(password).sendKeys(user.getPassword());
        selectDOB(user.getDob());
    }

    public void enterAddressInformation(User user){
        scrollPage();
        driver.findElement(firstName).sendKeys(user.getFirstName());
        driver.findElement(lastName).sendKeys(user.getLastName());
        driver.findElement(address).sendKeys(user.getAddress());
        selectDropdown(countryDropdown,user.getCountry());
        driver.findElement(state).sendKeys(user.getState());
        driver.findElement(city).sendKeys(user.getCity());
        driver.findElement(zipcode).sendKeys(user.getZipCode());
        driver.findElement(mobile_number).sendKeys(user.getMobileNumber());
    }

    public void clickOnCreateAccount(){
        driver.findElement(createAccountBtn).click();
    }

    public void selectTitle(User user){
        if(user.getTitle().equalsIgnoreCase("Mr")){
            driver.findElement(titleMr).click();
        }else{
            driver.findElement(titleMrs).click();
        }
    }

    public void selectDOB(LocalDate date){
        String day = String.valueOf(date.getDayOfMonth());
        String year = String.valueOf(date.getYear());

        String month = date.getMonth().name();
        month = month.substring(0, 1) + month.substring(1).toLowerCase();

        selectDropdown(dayDropdown, day);
        selectDropdown(monthDropdown, month);
        selectDropdown(yearDropdown, year);
    }

    public void selectDropdown(By dropdownLocator, String value) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement dropdown = wait.until(
                ExpectedConditions.visibilityOfElementLocated(dropdownLocator)
        );

        Select select = new Select(dropdown);
        try {
            select.selectByVisibleText(value);
        } catch (Exception e) {
            WebElement option = dropdown.findElement(
                    By.xpath(".//option[normalize-space(text())='" + value + "']")
            );
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView(true);", option);

            option.click();
        }
    }
}
