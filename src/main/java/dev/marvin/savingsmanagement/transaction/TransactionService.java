package dev.marvin.savingsmanagement.transaction;

import dev.marvin.savingsmanagement.exception.InsufficientAmountException;
import dev.marvin.savingsmanagement.exception.ResourceNotFoundException;
import dev.marvin.savingsmanagement.savingsaccount.Account;
import dev.marvin.savingsmanagement.savingsaccount.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class TransactionService {
    private final TransactionDao transactionDao;
    private final AccountService accountService;

    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = transactionDao.getAllTransactions();
        if (transactions.isEmpty()) {
            throw new ResourceNotFoundException("no transactions");
        }
        return transactions;
    }

    public Transaction getTransactionById(Long transactionId) {
        return transactionDao.getTransactionById(transactionId).orElseThrow(() -> new ResourceNotFoundException("transaction id [%s] not found".formatted(transactionId)));
    }

    public List<Transaction> getTransactionsByCustomerId(Long customerId) {
        return transactionDao.getTransactionByCustomerId(customerId);
    }

    public List<Transaction> getTransactionsByAccountId(Long accountId) {
        return transactionDao.getTransactionByAccount_Id(accountId);
    }

    public Transaction createTransaction(Long customerId, TransactionDto newTransactionRequest) {
        Account account = accountService.getAccountById(newTransactionRequest.accountId());
        BigDecimal amount = newTransactionRequest.amount();
        TransactionType transactionType = newTransactionRequest.transactionType();

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InsufficientAmountException("Amount must be greater than zero");
        }

        if (transactionType.equals(TransactionType.DEPOSIT)) {
            account.setBalance(account.getBalance().add(amount));
        } else if (transactionType.equals(TransactionType.WITHDRAWAL)) {
            if (account.getBalance().compareTo(amount) < 0) {
                throw new InsufficientAmountException("Insufficient balance");
            }
            account.setBalance(account.getBalance().subtract(amount));
        }
        accountService.save(account);

        Transaction transaction = new Transaction();
        transaction.setTransactionType(transactionType);
        transaction.setAccount(account);
        transaction.setAmount(amount);
        transaction.setCustomerId(customerId);

        return transactionDao.save(transaction);
    }

}
