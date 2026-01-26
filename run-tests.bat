@echo off
echo ========================================
echo    E-Commerce Automation Test Suite
echo ========================================

echo.
echo Select test suite to run:
echo 1. Simple Linear Flow (Recommended - No Loops)
echo 2. Real-World E-Commerce Flows
echo 3. Login First Then Shop Flow
echo 4. Shop First Then Login Flow  
echo 5. Quick Cart Test
echo 6. All Component Tests
echo 7. Specific Test Class
echo.
set /p choice="Enter your choice (1-7): "

echo.
echo Compiling project...
call mvn clean compile test-compile

if %ERRORLEVEL% NEQ 0 (
    echo ❌ Compilation failed!
    pause
    exit /b 1
)

echo.
echo ✅ Compilation successful!
echo.

if "%choice%"=="1" (
    echo Running Simple Linear Flow (No Loops)...
    call mvn test -DsuiteXmlFile=testng-simple.xml
) else if "%choice%"=="2" (
    echo Running Real-World E-Commerce Flows...
    call mvn test -DsuiteXmlFile=testng-realworld.xml
) else if "%choice%"=="3" (
    echo Running Login First Then Shop Flow...
    call mvn test -Dtest=ECommerceFlowTest
) else if "%choice%"=="4" (
    echo Running Shop First Then Login Flow...
    call mvn test -Dtest=ShopFirstLoginLaterTest
) else if "%choice%"=="5" (
    echo Running Quick Cart Test...
    call mvn test -DsuiteXmlFile=testng-quick.xml
) else if "%choice%"=="6" (
    echo Running All Component Tests...
    call mvn test -DsuiteXmlFile=testng-individual.xml
) else if "%choice%"=="7" (
    echo.
    echo Available test classes:
    echo - SimpleECommerceFlowTest (Linear flow - no loops)
    echo - ECommerceFlowTest (Login first flow)
    echo - ShopFirstLoginLaterTest (Shop first flow)
    echo - QuickTest (Quick cart test)
    echo - HomeTest, ProductTest, CartTest, etc.
    echo.
    set /p testclass="Enter test class name: "
    call mvn test -Dtest=!testclass!
) else (
    echo Invalid choice. Running Simple Linear Flow...
    call mvn test -DsuiteXmlFile=testng-simple.xml
)

if %ERRORLEVEL% NEQ 0 (
    echo ❌ Tests failed! Check the logs above.
) else (
    echo ✅ Tests completed successfully!
)

echo.
echo ✅ REPORTS GENERATED:
echo - Extent Reports: test-reports\ExtentReport_[timestamp].html
echo - Maven Reports: target\surefire-reports
echo.
echo ✅ SIMPLE FLOW FEATURES:
echo - Linear execution: Login → Shop → Order → Success → Logout
echo - No home page loops or product re-searching
echo - Automatic browser closure after order completion
echo - Clean order confirmation without navigation
echo.
pause