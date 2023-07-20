package dev.marvin.savingsmanagement.transaction;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
@AllArgsConstructor
@Tag(name = "Transaction API")
public class TransactionController {
    private final TransactionService transactionService;

    @Operation(summary = "Get All Transactions")
    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/{transactionId}")
    @Operation(summary = "Get Transaction by ID")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable("transactionId") Long transactionId) {
        Transaction transaction = transactionService.getTransactionById(transactionId);
        return ResponseEntity.ok(transaction);
    }

    @GetMapping("/accounts/{accountId}")
    @Operation(summary = "Get Transactions by Account ID")
    public ResponseEntity<List<Transaction>> getTransactionsByAccountId(@PathVariable("accountId") Long accountId) {
        List<Transaction> transactions = transactionService.getTransactionsByAccountId(accountId);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/customers/{customerId}")
    @Operation(summary = "Get All Transactions by Customer ID")
    public ResponseEntity<List<Transaction>> getTransactionsByCustomerId(@PathVariable("customerId") Long customerId) {
        List<Transaction> transactions = transactionService.getTransactionsByCustomerId(customerId);
        return ResponseEntity.ok(transactions);
    }

    @PostMapping("/customers/{customerId}")
    @Operation(summary = "Create Transaction by Customer ID [ TransactionType: DEPOSIT, WITHDRAWAL]")
    public ResponseEntity<Transaction> createTransaction(@PathVariable("customerId") Long customerId, @Validated @RequestBody TransactionDto transactionDto) {
        Transaction transaction = transactionService.createTransaction(customerId, transactionDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
    }
}
