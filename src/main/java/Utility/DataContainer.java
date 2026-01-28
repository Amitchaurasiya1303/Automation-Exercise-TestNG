package Utility;

import Models.Payment;
import Models.Product;
import Models.User;

import java.time.LocalDate;


public class DataContainer {
    public static Object[][] getUsersData() {
        Object[][] users = new Object[5][1];
        users[0][0] =  new User("Mr", "Amit", "amit798dps6@test.com", "Test@123", LocalDate.of(1995, 5, 10), "Amit", "Chaurasiya", "Mumbai Street 1", "India", "Maharashtra", "Mumbai", "400001", "9876543210");
        return users;
    }

    public static Object[][] getProducts(){
        Object[][] products = new Product[5][1];
        products[0][0] = new Product(1,"Blue Top", "Women", "Tops", true, "New", "Polo", 500);
        return products;
    }
    
    public static Object[][] getPaymentData(){
        Object[][] payments = new Payment[1][1];
        payments[0][0] = new Payment("Amit Chaurasiya", "4242424242424242", "123", "12", "2027");
        return payments;
    }
}
