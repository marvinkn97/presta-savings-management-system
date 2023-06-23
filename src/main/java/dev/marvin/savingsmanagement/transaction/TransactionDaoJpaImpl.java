package dev.marvin.savingsmanagement.transaction;

import dev.marvin.savingsmanagement.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class TransactionDaoJpaImpl implements TransactionDao {
    private final TransactionRepository transactionRepository;

    @Override
    public List<Transaction> findAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public Transaction findTransactionById(UUID transactionId) {
        return transactionRepository.findTransactionById(transactionId).orElseThrow(() -> new ResourceNotFoundException("transaction id: " + transactionId + " not found"));
    }

    @Override
    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> findTransactionByAccount_Id(UUID accountId) {
        return transactionRepository.findTransactionByAccount_Id(accountId);
    }

    @Override
    public List<Transaction> findTransactionByCustomerId(UUID customerId) {
        return transactionRepository.findTransactionByCustomerId(customerId);
    }
}
