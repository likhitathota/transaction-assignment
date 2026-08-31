
# AI Usage Disclosure

**Name:** Thota Likhita

**Project:** Customer Transaction Service

I used AI tools (ChatGpt)during the development of this project as a support for understanding requirements, checking my implementation and resolving development issues. I reviewed the suggestions and tested the final implementation myself before using it in the project.

## How I used AI

I used ChatGPT during the development of the project mainly for:

- Understanding the requirements of the transaction APIs.
- Getting a clearer idea of how the controller, service and repository work together.
- Checking validation conditions for transaction details.
- Understanding how transaction status changes should be handled.
- Preparing and checking test cases.
- Finding and correcting issues while testing the APIs using CMD.
- Improving the project documentation and README examples.

## How AI Helped

I used AI as a support while developing the project. It helped me with:

Understanding the assignment requirements.
Discussing the transaction service implementation.
Checking validation and exception handling.
Reviewing transaction status rules.
Checking and improving test cases.
Fixing issues during API testing.

## What I Changed and Verified

I followed the assignment requirements and used the AI suggestions according to the structure of my starter project.

I checked the suggested code before using it and made the required changes based on my project.
The main cases I checked were:

Creating a transaction successfully.
Getting a transaction using its ID.
Changing the transaction status.
Getting transactions using a customer ID.
Preventing duplicate transaction IDs.
Validating transaction amounts.
Handling transactions that are not found.
Preventing invalid status changes.

## Corrections During Development

During testing, I found an issue with the validation of transaction amounts. Invalid values such as zero or negative amounts were not being handled correctly.

I reviewed the validation logic and corrected it so that invalid transaction requests are rejected properly.

I tested the API again with both valid and invalid amounts to confirm the expected behaviour.
## Anything the AI Got Wrong

The AI initially suggested some validation and transaction-handling logic that did not fully match the requirements of the project.

I compared the suggestions with the existing code and assignment rules, then changed the logic where necessary. I also checked the implementation by running the test cases and fixed the parts that were not giving the expected results.
## Verification of the Final Solution

I verified the implementation by running the project through Eclipse → Run As → Maven test. The test execution completed successfully without any failures or errors.

I also ran the application locally and checked the implemented functionality to make sure the required transaction operations were working as expected.

The final test run completed successfully with:

```
    Tests run: 8
    Failures: 0
    Errors: 0
    Skipped: 0
```
Before submission, I reviewed the implementation to ensure that I can explain
the classes, business logic, validation rules, status transitions, exception
handling, API design and automated tests.

**THOTA LIKHITA**

This project was completed as part of the Toucan Payments engineering challenge.




