package dev.marvin.savingsmanagement.transaction;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class TransactionDaoJpaImpl implements TransactionDao {
    private final TransactionRepository transactionRepository;

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Optional<Transaction> getTransactionById(Long transactionId) {
        return transactionRepository.findTransactionById(transactionId);
    }

    @Override
    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> getTransactionByAccount_Id(Long accountId) {
        return transactionRepository.findTransactionByAccount_Id(accountId);
    }

    @Override
    public List<Transaction> getTransactionByCustomerId(Long customerId) {
        return transactionRepository.findTransactionByCustomerId(customerId);
    }
}
