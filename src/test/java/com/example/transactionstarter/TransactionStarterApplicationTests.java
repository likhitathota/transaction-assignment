package com.example.transactionstarter;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.transactionstarter.transaction.Transaction;
import com.example.transactionstarter.transaction.TransactionRepository;
import com.example.transactionstarter.transaction.TransactionService;

@ExtendWith(MockitoExtension.class)
class TransactionStarterApplicationTests {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    // Test 1 - Create transaction
    @Test
    void createTransactionSuccessfully() {

        Transaction transaction = new Transaction();

        transaction.setTransactionId("T111");
        transaction.setCustomerId("C201");
        transaction.setAmount(1000.0);
        transaction.setCurrency("INR");
        transaction.setTransactionType("PAYMENT");

        when(transactionRepository.existsById("T111")).thenReturn(false);
        when(transactionRepository.save(transaction)).thenReturn(transaction);

        Transaction result = transactionService.createTransaction(transaction);

        assertEquals("T111", result.getTransactionId());
        assertEquals("C201", result.getCustomerId());
        assertEquals("PENDING", result.getStatus());

        verify(transactionRepository).save(transaction);
    }

    // Test 2 - Get transaction
    @Test
    void getTransactionSuccessfully() {

        Transaction transaction = new Transaction();

        transaction.setTransactionId("T111");
        transaction.setCustomerId("C201");
        transaction.setAmount(1000.0);
        transaction.setCurrency("INR");
        transaction.setTransactionType("PAYMENT");
        transaction.setStatus("PENDING");

        when(transactionRepository.findById("T111"))
                .thenReturn(Optional.of(transaction));

        Optional<Transaction> result =
                transactionService.getTransaction("T111");

        assertTrue(result.isPresent());
        assertEquals("T111", result.get().getTransactionId());
        assertEquals("C201", result.get().getCustomerId());
    }

    // Test 3 - Update transaction status
    @Test
    void updateTransactionStatusSuccessfully() {

        Transaction transaction = new Transaction();

        transaction.setTransactionId("T111");
        transaction.setCustomerId("C201");
        transaction.setAmount(1000.0);
        transaction.setCurrency("INR");
        transaction.setTransactionType("PAYMENT");
        transaction.setStatus("PENDING");

        when(transactionRepository.findById("T111"))
                .thenReturn(Optional.of(transaction));

        when(transactionRepository.save(transaction))
                .thenReturn(transaction);

        Transaction result =
                transactionService.updateStatus("T111", "PROCESSING");

        assertEquals("PROCESSING", result.getStatus());

        verify(transactionRepository).save(transaction);
    }

    // Test 4 - Get all transactions for a customer
    @Test
    void getCustomerTransactionsSuccessfully() {

        Transaction transaction1 = new Transaction();
        transaction1.setTransactionId("T111");
        transaction1.setCustomerId("C201");

        Transaction transaction2 = new Transaction();
        transaction2.setTransactionId("T112");
        transaction2.setCustomerId("C201");

        when(transactionRepository.findByCustomerId("C201"))
                .thenReturn(Arrays.asList(transaction1, transaction2));

        var result =
                transactionService.getCustomerTransactions("C201");

        assertEquals(2, result.size());
        assertEquals("T111", result.get(0).getTransactionId());
        assertEquals("T112", result.get(1).getTransactionId());

        verify(transactionRepository).findByCustomerId("C201");
    }
 // Test 5 - Invalid amount

    @Test
    void createTransactionWithInvalidAmount() {

        Transaction transaction = new Transaction();

        transaction.setTransactionId("T113");
        transaction.setCustomerId("C201");
        transaction.setAmount(0.0);
        transaction.setCurrency("INR");
        transaction.setTransactionType("PAYMENT");

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> transactionService.createTransaction(transaction)
        );

        assertEquals("Amount must be greater than zero",
                exception.getMessage());
    }
 // Test 6 - Duplicate transaction ID

    @Test
    void createTransactionWithDuplicateId() {

        Transaction transaction = new Transaction();

        transaction.setTransactionId("T111");
        transaction.setCustomerId("C201");
        transaction.setAmount(1000.0);
        transaction.setCurrency("INR");
        transaction.setTransactionType("PAYMENT");

        when(transactionRepository.existsById("T111"))
                .thenReturn(true);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> transactionService.createTransaction(transaction)
        );

        assertEquals("Transaction ID already exists",
                exception.getMessage());
    }
 // Test 7 - Invalid status transition

    @Test
    void updateTransactionWithInvalidStatus() {

        Transaction transaction = new Transaction();

        transaction.setTransactionId("T111");
        transaction.setCustomerId("C201");
        transaction.setAmount(1000.0);
        transaction.setCurrency("INR");
        transaction.setTransactionType("PAYMENT");
        transaction.setStatus("PENDING");

        when(transactionRepository.findById("T111"))
                .thenReturn(Optional.of(transaction));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> transactionService.updateStatus("T111", "COMPLETED")
        );

        assertEquals(
                "Invalid status transition from PENDING to COMPLETED",
                exception.getMessage());
    }
 // Test 8 - Get transaction that does not exist

    @Test
    void getTransactionNotFound() {

        when(transactionRepository.findById("T999"))
                .thenReturn(Optional.empty());

        Optional<Transaction> result =
                transactionService.getTransaction("T999");

        assertTrue(result.isEmpty());

        verify(transactionRepository).findById("T999");
    }
}