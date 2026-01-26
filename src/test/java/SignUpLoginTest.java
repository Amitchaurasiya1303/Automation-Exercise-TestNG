import Models.User;
import Pages.SignUpLogInPage;
import Utility.DataContainer;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

public class SignUpLoginTest extends BaseTest{
    SignUpLogInPage signUpPage;

    SignUpLoginTest() throws IOException {
        signUpPage = new SignUpLogInPage();
    }

//    @Test(priority = 1)
//    public void checkPageTitle() throws IOException {
//        BaseTest.verifyTitle(PropertiesUtil.getProperty("login_signup_title"));
//    }

    @Test(dataProvider = "getUser", priority = 2)
    public void signUpUser(User user){
        if (user == null) {
            System.out.println("Skipping null user");
            return;
        }
        signUpPage.signUp(user);
    }

    @DataProvider
    public Object[][] getUser(){
        return DataContainer.getUsersData();
    }
}
