package dev.marvin.savingsmanagement.transaction;

import java.util.List;
import java.util.UUID;

public interface TransactionDao {

    List<Transaction> findAllTransactions();
    Transaction findTransactionById(UUID transactionId);

    Transaction save(Transaction transaction);

    List<Transaction> findTransactionByAccount_Id(UUID accountId);

    List<Transaction> findTransactionByCustomerId(UUID customerId);
}
