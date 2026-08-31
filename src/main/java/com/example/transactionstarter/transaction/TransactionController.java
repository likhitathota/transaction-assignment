package com.example.transactionstarter.transaction;

import java.util.Optional;
import java.util.List;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    // Operation A - Create transaction
    @PostMapping
    public ResponseEntity<?> createTransaction(@RequestBody Transaction transaction) {

        try {

            Transaction createdTransaction =
                    transactionService.createTransaction(transaction);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(createdTransaction);

        } catch (RuntimeException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    // Operation B - Get transaction by transaction ID
    @GetMapping("/{transactionId}")
    public ResponseEntity<?> getTransaction(
            @PathVariable String transactionId) {

        Optional<Transaction> transaction =
                transactionService.getTransaction(transactionId);

        if (transaction.isPresent()) {

            return ResponseEntity.ok(transaction.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Transaction not found");
    }

    // Operation C - Update transaction status
    @PatchMapping("/{transactionId}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable String transactionId,
            @RequestBody String newStatus) {

        try {

            Transaction updatedTransaction =
                    transactionService.updateStatus(transactionId, newStatus);

            return ResponseEntity.ok(updatedTransaction);

        } catch (RuntimeException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    // Operation D - Get all transactions for a customer
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<?> getCustomerTransactions(
            @PathVariable String customerId) {

        List<Transaction> transactions =
                transactionService.getCustomerTransactions(customerId);

        return ResponseEntity.ok(transactions);
    }
}