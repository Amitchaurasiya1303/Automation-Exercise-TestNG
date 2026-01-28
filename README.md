<<<<<<< HEAD
# E-Commerce Automation Testing Project

## Overview
Complete end-to-end automation testing for https://automationexercise.com using Selenium WebDriver, TestNG, and Java. Features user registration/login, product search, cart management, checkout flow, payment processing, and order confirmation with comprehensive reporting.

## Project Structure
```
AutomationFirstAmit/
├── src/
│   ├── main/java/
│   │   ├── BrowserDriver/          # WebDriver management
│   │   ├── Models/                 # Data models (User, Product, Payment)
│   │   ├── Pages/                  # Page Object Model classes
│   │   └── Utility/                # Utility classes and data containers
│   └── test/
│       ├── java/                   # Test classes
│       └── resources/              # Configuration files
├── test-reports/                   # Extent Reports output
├── testng.xml                      # TestNG suite configuration
├── pom.xml                         # Maven dependencies
└── run-tests.bat                   # Test execution script
```

## Key Features

### 🔧 Core Functionality:
- **Smart User Authentication**: Handles both new user registration and existing user login
- **Seamless Cart Navigation**: Direct cart navigation without home page loops
- **Robust Product Search**: Enhanced search with error handling and retry mechanisms
- **Complete Checkout Flow**: End-to-end order placement with payment processing
- **Order Confirmation**: Comprehensive success verification

### 🆕 Advanced Features:
- **Extent Reports**: Beautiful HTML reports with step-by-step execution
- **Page Object Model**: Clean, maintainable code architecture
- **Data-Driven Testing**: Configurable test data for users, products, and payments
- **Multiple Test Scenarios**: Various user journey flows
- **Auto Browser Cleanup**: Proper session management

## Test Data

### User Information:
- **Name**: Amit Chaurasiya
- **Email**: amit798dps6@test.com
- **Password**: Test@123
- **Address**: Mumbai Street 1, Mumbai, Maharashtra, India, 400001
- **Phone**: 9876543210

### Product Data:
- **Product**: Blue Top
- **Category**: Women > Tops
- **Brand**: Polo
- **Price**: ₹500

### Payment Details:
- **Card Holder**: Amit Chaurasiya
- **Card Number**: 4242424242424242
- **CVC**: 123
- **Expiry**: 12/2027

## Test Classes

### Main Test Flows:
- `SimpleECommerceFlowTest`: Linear flow with automatic logout and browser closure
- `ECommerceFlowTest`: Complete user journey from registration to order confirmation
- `ShopFirstLoginLaterTest`: Anonymous shopping with login during checkout

### Component Tests:
- `HomeTest`, `SignUpLoginTest`, `CreateAccountTest`
- `ProductTest`, `CartTest`, `CheckoutTest`, `PaymentTest`
- `OrderConfirmationTest`, `UserLoggedInTest`

## How to Run Tests

### Prerequisites:
- Java 11 or higher
- Maven 3.6+
- Chrome browser
- VS Code with Java extensions (optional)

### Execution Options:

#### Option 1: Using Batch Script (Recommended)
```bash
run-tests.bat
# Select option 1: Simple Linear Flow (No Loops)
```

#### Option 2: Maven Commands
```bash
# Compile project
mvn clean compile test-compile

# Run simple linear flow
mvn test -DsuiteXmlFile=testng-simple.xml

# Run specific test
mvn test -Dtest=SimpleECommerceFlowTest
```

#### Option 3: VS Code Integration
1. Open project in VS Code
2. Navigate to test classes
3. Click "Run Test" button
4. Or use Command Palette: "Java: Run Tests"

## Test Execution Flow

### Simple Linear Flow (Recommended):
```
🏠 Home → 🔐 Login → 🛍️ Search → 🛒 Add to Cart → 💳 Checkout → ✅ Order Success → 🔓 Logout → ❌ Close
```

### Key Benefits:
- ✅ No infinite loops or home page redirects
- ✅ Direct cart navigation after adding products
- ✅ Smart user authentication (signup/login handling)
- ✅ Automatic browser cleanup after order completion
- ✅ Comprehensive success verification

## Reports

### Extent Reports:
- **Location**: `test-reports/ExtentReport_[timestamp].html`
- **Features**: Step-by-step execution, screenshots, detailed logging
- **Phases**: Authentication → Shopping → Checkout → Payment → Confirmation

### Maven Reports:
- **Location**: `target/surefire-reports/`
- **Format**: XML and TXT reports for CI/CD integration

## Configuration

### Browser Settings:
- **Default**: Chrome
- **Configurable**: Via `config.properties`

### Test Data:
- **Location**: `DataContainer.java`
- **Customizable**: User, Product, and Payment information

## Troubleshooting

### Common Issues:

1. **Compilation Errors**:
   ```bash
   mvn clean compile test-compile
   ```

2. **Browser Issues**:
   - Ensure Chrome is updated
   - Check internet connectivity
   - Verify website accessibility

3. **Test Failures**:
   - Check console output for detailed error messages
   - Review Extent Reports for step-by-step execution
   - Verify test data in DataContainer.java

## VS Code Setup

### Required Extensions:
- Extension Pack for Java
- TestNG for Java
- Maven for Java

### Running Tests:
1. Open Command Palette (`Ctrl+Shift+P`)
2. Select "Java: Run Tests"
3. Choose specific test or test suite
4. View results in Test Explorer

## Success Criteria

The automation verifies:
-✅ User registration/login functionality
-✅ Product search and selection
-✅ Cart management and navigation
-✅ Checkout process completion
-✅ Payment processing
-✅ Order confirmation with success message
-✅ Proper session cleanup

## Technical Stack

- **Language**: Java 11
- **Framework**: Selenium WebDriver 4.40.0
- **Testing**: TestNG 7.12.0
- **Reporting**: Extent Reports 5.1.1
- **Build Tool**: Maven
- **Architecture**: Page Object Model
- **IDE**: VS Code / IntelliJ IDEA

## Author

**Amit Chaurasiya**
- GitHub: [Amitchaurasiya1303](https://github.com/Amitchaurasiya1303)
- Project: Automation Exercise TestNG

## License

This project is open source and available under the [MIT License](LICENSE).
=======
# Automation-Exercise-TESTNG
Automated an end-to-end eCommerce application using Selenium WebDriver, TestNG, and Page Object Model (POM). The framework covers signup, login, product selection, add-to-cart, checkout, and payment validation with reusable, scalable, and maintainable test design.
>>>>>>> 1def933c451eaa9da53d473455da4277c7afbbf1
