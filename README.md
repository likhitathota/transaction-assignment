# Transaction Starter Project

This is the starter project for the Customer Transactions exercise.

## Before you start

The first thing you should do after cloning the repository is:

### Linux / macOS

```bash
./mvnw clean test
```

### Windows

```bat
mvnw.cmd clean test
```

The sample test should pass before you begin implementing the exercise.

## What is already provided

- Java 17
- Spring Boot
- Maven wrapper
- Spring Web
- Spring Data JPA
- H2 embedded database
- JUnit / Spring Boot Test
- A sample REST endpoint: `GET /api/sample`
- A sample test that loads the Spring context


## Exercise

Implement these four operations:

1. Create transaction
2. Get transaction
3. Update transaction status
4. Get all transactions for a customer


You may change the surrounding design if you believe your solution is better.

## Transaction fields

Every transaction contains:

- Transaction ID
- Customer ID
- Amount
- Currency
- Transaction Type
- Transaction Status

### Validation rules

Define what makes a transaction valid. At minimum, consider:

- Transaction ID
- Customer ID
- Amount
- Currency
- Transaction type
- Initial status




The following validation rules are used when creating a transaction:

- Transaction ID is required and cannot be empty.
- Customer ID is required and cannot be empty.
- Amount is required and must be greater than zero.
- Currency is required and cannot be empty.
- Transaction type is required and cannot be empty.
- Transaction ID must be unique.

When a transaction is successfully created, its initial status is set to `PENDING`.

### Status transition rules

- PENDING → PROCESSING
- PROCESSING → COMPLETED
- PROCESSING → FAILED

Other status changes are rejected.



Reasoning: A new transaction starts in PENDING. It can move to PROCESSING when processing begins. From PROCESSING, it can either complete successfully or fail. Once a transaction is COMPLETED or FAILED, it should not change again because it has reached a final state.


Also explain any business validation you add beyond the annotations already supplied.

## API skeleton

### Create

Creates a new transaction after validating the request. The transaction ID must be unique and the initial status is set to PENDING.


Example:

```
curl -X POST "http://localhost:8081/transactions" -H "Content-Type: application/json" -d "{\"transactionId\":\"T111\",\"customerId\":\"C201\",\"amount\":1000000.0,\"currency\":\"US\",\"transactionType\":\"PAYMENT\"}"
```

### Get

Retrieves a transaction using its Transaction ID. If the transaction does not exist, the API returns a suitable not found response.

Example:

```
curl -X GET "http://localhost:8081/transactions/T111"
```

### Update status


Updates the status of an existing transaction. Only valid status transitions are allowed.

Valid transitions:
- PENDING → PROCESSING
- PROCESSING → COMPLETED
- PROCESSING → FAILED


Example:

```
curl -X PATCH "http://localhost:8081/transactions/T111/status" -H "Content-Type: text/plain" -d "PROCESSING"
```

### Get customer transactions

Retrieves all transactions for a specific customer.

Example:


```
curl -X GET "http://localhost:8081/transactions/customer/C201"

```

## Testing

The project includes 8 JUnit tests covering successful operations and important validation/error cases.

The tests cover:

- Creating a transaction successfully
- Getting a transaction successfully
- Getting a transaction that does not exist
- Updating transaction status successfully
- Getting all transactions for a customer
- Rejecting an invalid amount
- Rejecting a duplicate transaction ID
- Rejecting an invalid status transition

All 8 tests pass successfully.



### Manual API testing

The error handling was also checked using curl requests.

- Invalid amount (`0`) was rejected with "Amount must be greater than zero".
- A duplicate transaction ID was rejected with "Transaction ID already exists".
- An invalid status transition was rejected with the appropriate error message.
-  A request for a transaction that does not exist was rejected with "Transaction not found".

These checks confirmed that invalid requests are handled correctly.



## Documentation

### Understanding of the problem

This project is a simple transaction management API. It allows us to create a transaction, get a transaction using its ID, update its status, and get all transactions for a customer.

### Assumptions

- Transaction ID is unique.
- Amount must be greater than zero.
- A newly created transaction starts with PENDING status.
- Only the defined status transitions are allowed.
- Customer ID is used to find all transactions belonging to a customer.

### Known limitations

- The application uses simple error responses and does not have a separate global exception handler.
- Authentication and authorization are not implemented.
- The current implementation is mainly focused on the requirements of this exercise and is not designed as a production-ready system.

### Improvements with more time

With more time, I would improve the error responses by adding a global exception handler. I would also add authentication and authorization and improve the database setup for production use.



## AI Assistance Disclosure

I used ChatGPT to understand the requirements, help with the Spring Boot implementation, troubleshoot errors, and understand how to write and run the tests.

ChatGPT suggested code and testing approaches for the transaction service and controller. I reviewed the suggestions and made the changes needed to fit the starter project and the given requirements.

During testing, I found some issues with the suggested testing approach and corrected them based on the actual project setup and requirements. I also verified the status transitions, validation, duplicate transaction ID handling, and other required operations.

I checked the final result by running the JUnit tests in Eclipse and also testing the API using curl commands in the command prompt. The final test run showed 8 tests run, with 0 failures and 0 errors.




## Test Run Output

The complete test suite was run using:

mvnw.cmd clean test

Result:

Tests run: 8, Failures: 0, Errors: 0, Skipped: 0

BUILD SUCCESS

