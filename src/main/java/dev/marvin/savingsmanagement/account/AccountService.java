package dev.marvin.savingsmanagement.account;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class AccountService {

    private final AccountDao accountDao;

    public List<Account> findAllAccounts() {
        return accountDao.findAllAccounts();
    }

    public Account findAccountById(UUID accountId) {
        return accountDao.findAccountById(accountId);
    }

    public Account save(Account account){
        return accountDao.save(account);
    }

    public void deleteAccountById(UUID accountId) {
        accountDao.deleteAccountById(accountId);
    }

    public List<Account> findAccountsByCustomerId(UUID customerId) {
        return accountDao.findAccountsByCustomerId(customerId);
    }

}
