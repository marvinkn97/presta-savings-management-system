package dev.marvin.savingsmanagement.transaction;

import dev.marvin.savingsmanagement.account.Account;
import dev.marvin.savingsmanagement.account.AccountService;
import dev.marvin.savingsmanagement.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountService accountService;

    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    public Transaction findTransactionById(int id) {
        return transactionRepository.findTransactionById(id).orElseThrow(() -> new ResourceNotFoundException("Transaction with id: " + id + " not found"));
    }

    public List<Transaction> findTransactionsByCustomerId(int id) {
        return transactionRepository.findTransactionByCustomerId(id);
    }

    public List<Transaction> findTransactionsByAccountId(int id) {
        return transactionRepository.findTransactionByAccount_Id(id);
    }


    public Transaction createTransaction(int customerId, TransactionDto transactionDto) {
        Account account = accountService.findAccountById(transactionDto.accountId());
        BigDecimal amount = transactionDto.amount();
        TransactionType transactionType = TransactionType.valueOf(transactionDto.transactionType());

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

        if (transactionType.equals(TransactionType.DEPOSIT)) {
            account.setBalance(account.getBalance().add(amount));
        } else if (transactionType.equals(TransactionType.WITHDRAWAL)) {
            if (account.getBalance().compareTo(amount) < 0) {
                throw new IllegalArgumentException("Insufficient balance");
            }
            account.setBalance(account.getBalance().subtract(amount));
        }
        accountService.saveAccount(account);

        Transaction transaction = new Transaction();
        transaction.setTransactionType(transactionType);
        transaction.setAccount(account);
        transaction.setAmount(amount);
        transaction.setCustomerId(customerId);

        return transactionRepository.save(transaction);

    }

}
