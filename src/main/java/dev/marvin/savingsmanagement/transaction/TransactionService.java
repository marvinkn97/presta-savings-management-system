package dev.marvin.savingsmanagement.transaction;

import dev.marvin.savingsmanagement.account.Account;
import dev.marvin.savingsmanagement.account.AccountService;
import dev.marvin.savingsmanagement.exception.InsufficientAmountException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TransactionService {
    private final TransactionDao transactionDao;
    private final AccountService accountService;

    public List<Transaction> findAllTransactions() {
        return transactionDao.findAllTransactions();
    }

    public Transaction findTransactionById(UUID transactionId) {
        return transactionDao.findTransactionById(transactionId);
    }

    public List<Transaction> findTransactionsByCustomerId(UUID customerId) {
        return transactionDao.findTransactionByCustomerId(customerId);
    }

    public List<Transaction> findTransactionsByAccountId(UUID accountId) {
        return transactionDao.findTransactionByAccount_Id(accountId);
    }

    public Transaction createTransaction(UUID customerId, TransactionDto transactionDto) {
        Account account = accountService.findAccountById(transactionDto.accountId());
        BigDecimal amount = transactionDto.amount();
        TransactionType transactionType = TransactionType.valueOf(transactionDto.transactionType());

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

        Transaction transaction = Transaction.builder()
                .transactionType(transactionType)
                .account(account)
                .amount(amount)
                .customerId(customerId)
                .build();

        return transactionDao.save(transaction);

    }

}
