package com.example.transactionstarter.transaction;

import java.util.Optional;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction createTransaction(Transaction transaction) {

        if (transaction.getTransactionId() == null ||
                transaction.getTransactionId().isEmpty()) {

            throw new RuntimeException("Transaction ID is required");
        }

        if (transaction.getCustomerId() == null ||
                transaction.getCustomerId().isEmpty()) {

            throw new RuntimeException("Customer ID is required");
        }

        if (transaction.getAmount() == null ||
                transaction.getAmount() <= 0) {

            throw new RuntimeException("Amount must be greater than zero");
        }

        if (transaction.getCurrency() == null ||
                transaction.getCurrency().isEmpty()) {

            throw new RuntimeException("Currency is required");
        }

        if (transaction.getTransactionType() == null ||
                transaction.getTransactionType().isEmpty()) {

            throw new RuntimeException("Transaction type is required");
        }

        if (transactionRepository.existsById(transaction.getTransactionId())) {

            throw new RuntimeException("Transaction ID already exists");
        }

        transaction.setStatus("PENDING");

        return transactionRepository.save(transaction);
    }

    public Optional<Transaction> getTransaction(String transactionId) {

        return transactionRepository.findById(transactionId);

    }

    public Transaction updateStatus(String transactionId, String newStatus) {

        Optional<Transaction> transaction =
                transactionRepository.findById(transactionId);

        if (transaction.isEmpty()) {
            throw new RuntimeException("Transaction not found");
        }

        String currentStatus = transaction.get().getStatus();

        if (currentStatus.equals("PENDING")
                && newStatus.equals("PROCESSING")) {

            transaction.get().setStatus(newStatus);

        } else if (currentStatus.equals("PROCESSING")
                && (newStatus.equals("COMPLETED")
                || newStatus.equals("FAILED"))) {

            transaction.get().setStatus(newStatus);

        } else {
            throw new RuntimeException(
                    "Invalid status transition from "
                    + currentStatus + " to " + newStatus);
        }

        return transactionRepository.save(transaction.get());
    }

    // Operation D - Get all transactions for a customer
    public List<Transaction> getCustomerTransactions(String customerId) {

        return transactionRepository.findByCustomerId(customerId);
    }
}