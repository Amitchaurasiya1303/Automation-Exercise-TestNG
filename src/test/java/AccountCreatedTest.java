import Pages.AccountCreatedPage;
import org.testng.annotations.Test;

import java.io.IOException;

public class AccountCreatedTest {
    AccountCreatedPage accountCreatedPage;

    AccountCreatedTest() throws IOException {
        accountCreatedPage = new AccountCreatedPage();
    }

    @Test
    public void continueForward(){
        accountCreatedPage.continueForMore();
    }
}
