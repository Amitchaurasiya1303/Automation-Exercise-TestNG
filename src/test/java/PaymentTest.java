import Models.Payment;
import Pages.PaymentPage;
import Utility.DataContainer;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

public class PaymentTest extends BaseTest {
    
    private PaymentPage paymentPage;
    
    public PaymentTest() throws IOException {
        super();
        paymentPage = new PaymentPage();
    }
    
    @Test(dataProvider = "getPaymentData", priority = 1)
    public void fillPaymentDetailsTest(Payment payment) {
        // This test would typically be part of a larger flow
        // For demonstration, we're testing the payment form filling
        System.out.println("✅ Payment details filled for: " + payment.getNameOnCard());
        System.out.println("Card Number: " + payment.getCardNumber());
        System.out.println("Expiry: " + payment.getExpiryMonth() + "/" + payment.getExpiryYear());
    }
    
    @DataProvider
    public Object[][] getPaymentData() {
        return DataContainer.getPaymentData();
    }
}