import Models.User;
import Pages.CreateAccountPage;
import Utility.DataContainer;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

public class CreateAccountTest {
    CreateAccountPage createAccountPage;

    CreateAccountTest() throws IOException {
        createAccountPage = new CreateAccountPage();
    }

//    @Test(priority = 1)
//    public void checkPageTitle() throws IOException {
//        BaseTest.verifyTitle(PropertiesUtil.getProperty("create_account_title"));
//    }

    @Test(dataProvider = "getUser", priority = 2)
    public void createAccount(User user){
        if (user == null) {
            System.out.println("Skipping null user");
            return;
        }
        createAccountPage.enterAccountInformation(user);
        createAccountPage.enterAddressInformation(user);
        createAccountPage.clickOnCreateAccount();
    }

    @DataProvider
    public Object[][] getUser(){
        return DataContainer.getUsersData();
    }
}
