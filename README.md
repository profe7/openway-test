# Openway assessment task

## Periplus Automated Testing (Shopping Cart)
This project was written to test the Periplus shopping cart feature. The automated test is performed using the Selenium Web Driver.

## Rev. 2 (30/05/2025)
1. Improved code reusability by moving to POM design pattern.
2. Improved separation of concerns between test code and page logic.
3. Improved book verification by checking both title and ISBN.
4. Improved user journey tracking through DSL.
5. Improved test cleanup by using AfterMethod

## Rev 2.1 (30/05/2025)
1. Added test more relevant to the shopping cart namely update cart, send as gift, continue shopping, and checkout.


## Test steps
1. Open the Periplus website.
2. Navigate to the login page.
3. Login with valid credentials.
4. Search for a book with the search bar.
5. Navigate to the book's details page.
6. Add the book to the cart.
7. Navigate to the cart page.
8. Remove the book from the cart.

## Requirements
1. JDK 22
2. Maven 
3. Google Chrome browser

## Setup
1. Clone the repository.
2. Open the project in your favorite IDE.
3. Remove the .example from the `.env.example` file and rename it to `.env`.
4. Fill in the `.env` file with your Periplus account credentials.
5. Open the terminal and navigate to the project directory.
6. Run the mvn clean install command to install the dependencies.
7. Run the mvn test command to run the tests.
