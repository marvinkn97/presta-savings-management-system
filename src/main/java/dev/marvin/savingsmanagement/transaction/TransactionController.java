package dev.marvin.savingsmanagement.transaction;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/transactions")
@AllArgsConstructor
@Tag(name = "Transaction API")
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping
    @Operation(summary = "Get All Transactions")
    public ResponseEntity<List<Transaction>> getAllTransactions() {

        List<Transaction> transactions = transactionService.findAllTransactions();

        if (transactions.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/{transactionId}")
    @Operation(summary = "Get Transaction by ID")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable("transactionId") UUID id) {
        Transaction transaction = transactionService.findTransactionById(id);
        return ResponseEntity.ok(transaction);
    }

    @GetMapping("/accounts/{accountId}")
    @Operation(summary = "Get Transactions by Account ID")
    public ResponseEntity<List<Transaction>> getTransactionsByAccountId(@PathVariable("accountId") UUID id) {

        List<Transaction> transactions = transactionService.findTransactionsByAccountId(id);
        if (transactions.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(transactions);
    }


    @GetMapping("/customers/{customerId}")
    @Operation(summary = "Get All Transactions by Customer ID")
    public ResponseEntity<List<Transaction>> getTransactionsByCustomerId(@PathVariable("customerId") UUID id) {

        List<Transaction> transactions = transactionService.findTransactionsByCustomerId(id);
        if (transactions.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(transactions);
    }


    @PostMapping("/customers/{customerId}")
    @Operation(summary = "Create Transaction by Customer ID [ TransactionType: DEPOSIT, WITHDRAWAL]")
    public ResponseEntity<Transaction> createTransaction(@PathVariable("customerId") UUID customerId, @RequestBody TransactionDto transactionDto) {
        Transaction transaction = transactionService.createTransaction(customerId, transactionDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
    }
}
