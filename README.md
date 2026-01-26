# E-Commerce Automation Testing Project

## Overview
This project automates the complete e-commerce flow on https://automationexercise.com including:
- User registration and login
- Product search and selection
- Add to cart functionality
- Checkout process
- Payment processing
- Order confirmation verification

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
├── testng.xml                      # TestNG suite configuration
├── pom.xml                         # Maven dependencies
└── run-tests.bat                   # Test execution script
```

## Key Features Fixed/Added

### 🔧 Fixed Issues:
1. **Search Box Location**: Updated locators with more robust selectors
2. **Product Search**: Improved search functionality with better error handling
3. **View Product**: Fixed "View Product" button clicking with fallback mechanisms
4. **Data Passing**: Enhanced data flow between test methods

### 🆕 New Features Added:
1. **ProductDetailPage**: Handles add to cart functionality
2. **CartPage**: Manages cart operations and checkout navigation
3. **CheckoutPage**: Handles order review and placement
4. **PaymentPage**: Processes payment information
5. **OrderConfirmationPage**: Verifies successful order completion
6. **Complete E2E Test**: Full end-to-end e-commerce flow test

## Test Classes

### Component Tests:
- `HomeTest`: Home page functionality
- `SignUpLoginTest`: User authentication
- `CreateAccountTest`: Account creation
- `ProductTest`: Product search and viewing
- `CartTest`: Cart operations
- `PaymentTest`: Payment processing

### End-to-End Tests:
- `ECommerceFlowTest`: Complete purchase flow from registration to order confirmation

## How to Run Tests

### Prerequisites:
1. Java 22 installed
2. Maven installed
3. Chrome browser installed
4. VS Code with Java extensions

### Running Tests:

#### Option 1: Using the batch script (Recommended)
```bash
# Double-click run-tests.bat or run in terminal:
run-tests.bat
```

#### Option 2: Using Maven commands
```bash
# Compile the project
mvn clean compile test-compile

# Run all tests
mvn test

# Run specific test suite
mvn test -DsuiteXmlFile=testng.xml
```

#### Option 3: Using VS Code
1. Open the project in VS Code
2. Navigate to any test class
3. Click the "Run Test" button above test methods
4. Or right-click on testng.xml and select "Run Tests"

## Test Data

### Product Data:
- Product Name: "Blue Top"
- Category: Women > Tops
- Brand: Polo
- Price: 500

### User Data:
- Name: Amit Chaurasiya
- Email: amit798dss6@test.com (auto-generated)
- Password: Test@123
- Address: Mumbai, Maharashtra, India

### Payment Data:
- Card Name: John Doe
- Card Number: 4242424242424242
- CVC: 123
- Expiry: 12/2025

## Configuration

### Browser Settings:
- Default: Chrome
- Configurable via `config.properties`

### URLs:
All URLs are configured in `src/test/resources/config.properties`

## Test Reports

After running tests, reports are available in:
- `target/surefire-reports/` - Maven Surefire reports
- Console output with detailed step-by-step execution logs

## Troubleshooting

### Common Issues:

1. **Search box not found**:
   - Ensure internet connection is stable
   - Check if website is accessible
   - Verify Chrome browser is updated

2. **Element not clickable**:
   - Tests include wait conditions and retry mechanisms
   - JavaScript click fallbacks are implemented

3. **Test data issues**:
   - Email addresses are auto-generated to avoid conflicts
   - Payment uses test card numbers

### Debug Mode:
Enable detailed logging by checking console output during test execution.

## VS Code Integration

### Running Tests in VS Code:
1. Install "Extension Pack for Java"
2. Install "TestNG for Java"
3. Open Command Palette (Ctrl+Shift+P)
4. Type "Java: Run Tests" or use the test runner in the sidebar

### Debugging:
1. Set breakpoints in test methods
2. Right-click and select "Debug Test"
3. Use VS Code's debugging features to step through code

## Success Criteria

The automation verifies:
✅ User can register successfully
✅ Product search works correctly  
✅ Product can be viewed and added to cart
✅ Cart contains the added product
✅ Checkout process completes
✅ Payment information is processed
✅ Order confirmation is displayed
✅ Success message contains "Congratulations"

## Notes

- Tests are designed to run independently
- Each test includes proper setup and cleanup
- Robust error handling and retry mechanisms
- Detailed logging for debugging
- Cross-browser compatible (Chrome default)

## Support

For issues or questions:
1. Check console output for detailed error messages
2. Verify all dependencies are installed correctly
3. Ensure website accessibility
4. Review test data in DataContainer.java