package dev.marvin.savingsmanagement.transaction;

import java.util.List;
import java.util.Optional;

public interface TransactionDao {
    List<Transaction> getAllTransactions();
    Optional<Transaction> getTransactionById(Long transactionId);
    Transaction save(Transaction transaction);
    List<Transaction> getTransactionByAccount_Id(Long accountId);
    List<Transaction> getTransactionByCustomerId(Long customerId);
}
