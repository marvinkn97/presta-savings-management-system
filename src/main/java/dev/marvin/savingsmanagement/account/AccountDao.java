package dev.marvin.savingsmanagement.account;

import java.util.List;
import java.util.UUID;

public interface AccountDao {
    List<Account> findAllAccounts();
    Account findAccountById(UUID accountId);
    Account save(Account account);
    void deleteAccountById(UUID accountId);
    List<Account> findAccountsByCustomerId(UUID customerId);
}
