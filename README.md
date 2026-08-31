# Customer Transaction Management API

## 1. Problem Understanding

This project is a Spring Boot REST API for managing customer transactions. It allows users to create transactions, retrieve transactions, update transaction status, and retrieve all transactions for a customer.

The main purpose of the application is to create and manage customer transactions.

Each transaction contains:

- Transaction ID
- Customer ID
- Amount
- Currency
- Transaction Type
- Transaction Status

I implemented the four operations mentioned in the assignment:

1. Create a transaction
2. Get a transaction using Transaction ID
3. Update the status of a transaction
4. Get all transactions for a customer

I focused on keeping the implementation simple and easy to understand while covering the requirements given in the assignment.


## 2. Technology Used

The project uses the following technologies:

- Java 17
- Spring Boot
- Maven
- Spring Web
- Spring Data JPA
- H2 Embedded Database
- JUnit
- Spring Boot Test

## 3. Application Design

The application follows a simple layered design.

```text
Controller
    |
    v
Service
    |
    v
Repository
    |
    v
H2 Database

```
## Controller

The controller handles the REST API requests.

It receives requests from the client and passes the required information to the service layer.
## Service

The service layer contains the main business logic.

It is responsible for:

- Creating transactions
- Validating transaction details
- Checking duplicate transaction IDs
- Getting transactions
- Updating transaction status
- Getting all transactions for a customer

## Repository

The repository is responsible for database operations.

Spring Data JPA is used to communicate with the H2 database.
## 4. Transaction Validation

The following validation rules are used when creating a transaction.
Transaction ID
- Transaction ID is required.
- Transaction ID cannot be empty.
- Transaction ID must be unique.

If a transaction with the same ID already exists, the request is rejected.

Example error:

```
Transaction ID already exists
```
## Customer ID
- Customer ID is required.
- Customer ID cannot be empty.
Example Command:

```
curl -X POST "http://localhost:8081/transactions" -H "Content-Type: application/json" -d "{\"transactionId\":\"T114\",\"customerId\":\"\",\"amount\":1000,\"currency\":\"USD\",\"transactionType\":\"PAYMENT\"}"
```
Output:

```
Customer ID is required
```

## Amount
- Amount is required.
- Amount must be greater than zero.
- Zero and negative amounts are rejected.

Example error:

```
Amount must be greater than zero
```
## Currency
- Currency is required.
- Currency cannot be empty.
Example:

```
curl -X POST "http://localhost:8081/transactions" -H "Content-Type: application/json" -d "{\"transactionId\":\"T116\",\"customerId\":\"C201\",\"amount\":1000,\"currency\":\"\",\"transactionType\":\"PAYMENT\"}"
```
Output:

```
Currency is required
```

## Transaction Type
- Transaction type is required.
- Transaction type cannot be empty.
Example:

```
curl -X POST "http://localhost:8081/transactions" -H "Content-Type: application/json" -d "{\"transactionId\":\"T117\",\"customerId\":\"C201\",\"amount\":1000,\"currency\":\"USD\",\"transactionType\":\"\"}"
```
Output:

```
Transaction type is required
```

## Initial Transaction Status

The client does not provide the initial status while creating a transaction.

When a transaction is successfully created, its initial status is automatically set to:

```
PENDING
```
This is handled by the application.

## 5. Transaction Status Rules

The transaction status can only be changed using the valid status transitions defined for the application.

The allowed transitions are:

```
PENDING → PROCESSING
PROCESSING → COMPLETED
PROCESSING → FAILED
```
Other status changes are rejected.

For example:

```
PENDING → COMPLETED
```
is not allowed because the transaction must first move to PROCESSING.

Once a transaction reaches COMPLETED or FAILED, it should not be changed again because it has reached its final state.

This keeps the transaction status flow simple and prevents invalid status changes.
## 6. API Endpoints

The application provides four main REST APIs.

## 6.1 Create Transaction
### POST /transactions
Creates a new transaction after validating the request.

The Transaction ID must be unique and the initial status is automatically set to PENDING.

Example:

```
curl -X POST "http://localhost:8081/transactions" -H "Content-Type: application/json" -d "{\"transactionId\":\"T111\",\"customerId\":\"C201\",\"amount\":1000000.0,\"currency\":\"US\",\"transactionType\":\"PAYMENT\"}"
```
Example request data:

```
{
  "transactionId": "T111",
  "customerId": "C201",
  "amount": 1000000.0,
  "currency": "US",
  "transactionType": "PAYMENT"
}
```
The created transaction starts with:

```
PENDING
```
## 6.2 Get Transaction

## GET/transactions/{transactionId}

Retrieves a transaction using its Transaction ID.

Example:

```
curl -X GET "http://localhost:8081/transactions/T111"
```
If the transaction exists, its details are returned.

If the transaction does not exist, the API returns a suitable not found response.

Example:

```
Transaction not found
```
## 6.3 Update Transaction Status

## PATCH/transactions/{transactionId}/status

Updates the status of an existing transaction.

Only valid status transitions are allowed.

Example:

```
curl -X PATCH "http://localhost:8081/transactions/T111/status" -H "Content-Type: text/plain" -d "PROCESSING"
```
Valid transitions:

```
PENDING → PROCESSING
PROCESSING → COMPLETED
PROCESSING → FAILED
```
An invalid status transition is rejected.

For example:

```
PENDING → COMPLETED
```
is rejected because the transaction cannot directly move from PENDING to COMPLETED.
## 6.4 Get Customer Transactions
## GET/transactions/customer/{customerId}

Retrieves all transactions belonging to a specific customer.

Example:

```
curl -X GET "http://localhost:8081/transactions/customer/C201"
```
This returns all transactions associated with the given Customer ID.
## 7. Error Handling

The application handles different invalid situations and returns suitable error messages.

## Duplicate Transaction ID

If a transaction with the same Transaction ID already exists, the request is rejected.

Example:

```
Transaction ID already exists
```
## Transaction Not Found

If a requested transaction does not exist, the API returns:


Example:

```
Transaction not found
```
## Invalid Amount

If the transaction amount is zero or negative, the request is rejected.

Example 

```
Amount must be greater than zero
```
## Invalid Status Transition

If an invalid status change is requested, the request is rejected with an appropriate error message.

For example, a transaction in PROCESSING status cannot be changed directly to PENDING.

Example:

```
PENDING → COMPLETED
```
is not allowed.

These validations help prevent invalid transaction data from being stored.
## 8. Testing

The project includes JUnit tests covering the main operations and important validation cases.

The tests cover:

- Creating a transaction successfully
- Getting a transaction successfully
- Getting a transaction that does not exist
- Updating transaction status successfully
- Getting all transactions for a customer
- Rejecting an invalid amount
- Rejecting a duplicate transaction ID
- Rejecting an invalid status transition

The complete test suite was executed successfully.

Test result:

```
Tests run: 8
Failures: 0
Errors: 0
Skipped: 0

BUILD SUCCESS
```
All 8 tests passed successfully.
## 9. Manual API Testing

The REST APIs were also tested manually using curl commands.

The following cases were checked:

- Creating a transaction successfully
- Getting an existing transaction
- Getting a transaction that does not exist
- Updating transaction status
- Getting all transactions for a customer
- Rejecting an invalid amount
- Rejecting a duplicate transaction ID
- Rejecting an invalid status transition

## Create Transaction
  
```
 curl -X POST "http://localhost:8081/transactions" -H "Content-Type: application/json" -d "{\"transactionId\":\"T111\",\"customerId\":\"C201\",\"amount\":1000000.0,\"currency\":\"US\",\"transactionType\":\"PAYMENT\"}"
 ```
 Output:
 
 ```
{"transactionId":"T111","customerId":"C201","amount":1000000.0,"currency":"US","transactionType":"PAYMENT","status":"PENDING"}
 ```
 The transaction was created successfully with PENDING status.
 
## Get Transaction

```
curl -X GET "http://localhost:8081/transactions/T111"
```
Output:

```
{"transactionId":"T111","customerId":"C201","amount":1000000.0,"currency":"US","transactionType":"PAYMENT","status":"PENDING"}
```
## Get Transaction That Does Not Exist

```
curl -X GET "http://localhost:8081/transactions/T112"
```
Output:

```
Transaction not found
```
## Update Transaction Status

```
curl -X PATCH "http://localhost:8081/transactions/T111/status" -H "Content-Type: text/plain" -d "PROCESSING"
```
Output:

```
{"transactionId":"T111","customerId":"C201","amount":1000000.0,"currency":"US","transactionType":"PAYMENT","status":"PROCESSING"}
```

## Get Customer Transactions

```
curl -X GET "http://localhost:8081/transactions/customer/C201"
```
Output:

```
[{"transactionId":"T111","customerId":"C201","amount":1000000.0,"currency":"US","transactionType":"PAYMENT","status":"PROCESSING"}]
```
## Invalid Amount

An amount of 0 was rejected.

```
curl -X POST "http://localhost:8081/transactions" -H "Content-Type: application/json" -d "{\"transactionId\":\"T113\",\"customerId\":\"C201\",\"amount\":0,\"currency\":\"US\",\"transactionType\":\"PAYMENT\"}"
```
Output:

```
Amount must be greater than zero
```
## Duplicate Transaction ID

A duplicate Transaction ID was rejected.

```
curl -X POST "http://localhost:8081/transactions" -H "Content-Type: application/json" -d "{\"transactionId\":\"T111\",\"customerId\":\"C201\",\"amount\":1000000.0,\"currency\":\"US\",\"transactionType\":\"PAYMENT\"}"
```
Output:

```
Transaction ID already exists
```
## Invalid Status Transition

An invalid status transition was rejected.

For example, trying to change a completed transaction back to pending

```
curl -X PATCH "http://localhost:8081/transactions/T201/status" -H "Content-Type: text/plain" -d "PENDING"
```
Output:

```
Invalid status transition from COMPLETED to PENDING
```

## 10. Database

The application uses the H2 embedded database.

H2 is used because it is simple to configure and does not require a separate database installation.

Spring Data JPA is used for database operations.

The database is mainly used to store transaction information during application execution.
## 11. Project Structure

The project follows a simple Spring Boot project structure.

```
src/main/java
│
├── Controller
│
├── Service
│
├── Repository
│
├── Entity
│
└── TransactionStarterApplication
```
The main responsibilities are divided between the different layers:

```
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```
The controller handles HTTP requests, the service contains the business logic, and the repository handles database operations.
## 12. How to Run the Project
Windows

To run the tests:

```
mvnw.cmd clean test
```
To start the application:

```
mvnw.cmd spring-boot:run
```
## Linux / macOS

To run the tests:

```
./mvnw clean test
```
To start the application:

```
./mvnw spring-boot:run
```
The application runs on:

```
http://localhost:8081
```
The APIs can then be tested using curl or another REST API testing tool.

## 13. Assumptions

The following assumptions were made while implementing the project:

- Transaction ID is unique.
- Transaction ID cannot be empty.
- Customer ID cannot be empty.
- Amount must be greater than zero.
- Currency is required.
- Transaction type is required.
- Every newly created transaction starts with PENDING.
- Only valid status transitions are allowed.
- COMPLETED and FAILED are final transaction states.
- Customer ID is used to retrieve all transactions belonging to a customer.

## 14. Known Limitations

This implementation is mainly focused on the requirements of the engineering exercise and is not intended to be a complete production-ready transaction system.

Some limitations are:

- The application uses the H2 embedded database.
- Authentication and authorization are not implemented.
- The error handling is kept simple.
- There is no separate global exception handler.
- Production-level monitoring and auditing are not implemented.
- The application is mainly focused on the requirements of this exercise.

## 15. Improvements With More Time

With more time, I would improve the application by:

- Adding a global exception handler.
- Improving the error response structure.
- Adding more detailed validation.
- Adding authentication and authorization.
- Improving the database setup for production use.
- Adding more test cases.
- Adding logging and monitoring.
- Adding API documentation using Swagger/OpenAPI.
- Adding better handling for large numbers of customer transactions.

These improvements would make the application more suitable for a production environment.

## 16. AI Assistance Disclosure

I used ChatGPT to understand the requirements, get help with the Spring Boot implementation, troubleshoot errors, and understand how to write and run the tests.

ChatGPT suggested code and testing approaches for the transaction service and controller. I reviewed the suggestions and changed them where needed to match the starter project and the given requirements.

During testing, I found that some suggested testing steps did not match my actual project setup, so I corrected them based on the project structure and requirements. I also checked and corrected the validation rules, status transitions, duplicate transaction ID handling, and other required operations.

I did not blindly use the suggested code. I checked the application behavior and modified the implementation when the suggested approach did not work correctly with my project.

I checked the final result by running the JUnit tests in Eclipse and manually testing the API using curl commands in the command prompt. The final test run showed 8 tests run, with 0 failures and 0 errors. I also verified the main success and error cases manually using the API.

## 17. Final Verification

The complete test suite was executed using:

```
mvnw.cmd clean test
```
The final result was:

```
Tests run: 8
Failures: 0
Errors: 0
Skipped: 0

BUILD SUCCESS
```
The four required operations were also verified:

- Create transaction
- Get transaction
- Update transaction status
- Get all transactions for a customer

The important validation cases were also checked:

- Invalid amount
- Duplicate transaction ID
- Invalid status transition
- Transaction not found

## 18. Conclusion

This project implements a simple Customer Transaction Service using Java, Spring Boot, Spring Data JPA and H2.

The application provides the required transaction operations and includes validation for important transaction fields and status changes.

The implementation was tested using both JUnit tests and manual curl requests.

All 8 automated tests passed successfully with:

```
Failures: 0
Errors: 0
```
The project focuses on keeping the implementation simple, readable and aligned with the requirements of the engineering challenge.
