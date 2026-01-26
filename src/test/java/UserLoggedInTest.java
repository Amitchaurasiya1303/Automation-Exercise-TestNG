import Pages.UserLoggedInPage;
import org.testng.annotations.Test;

import java.io.IOException;

public class UserLoggedInTest {
    UserLoggedInPage userLoggedInPage;

    UserLoggedInTest() throws IOException {
        userLoggedInPage = new UserLoggedInPage();
    }

    @Test(priority = 1)
    public void isUserLoggedIn(){
        userLoggedInPage.isLoggedIn();
    }

    @Test(priority = 2)
    public void navigate(){
        userLoggedInPage.navigateToProducts();
    }
}
