package dev.marvin.savingsmanagement.savingsaccount;

import java.util.List;
import java.util.Optional;

public interface AccountDao {
    List<Account> getAllAccounts();
    Optional<Account> getAccountById(Long accountId);
    Account save(Account account);
    void deleteAccountById(Long accountId);
    List<Account> getAccountsByCustomerId(Long customerId);
}
