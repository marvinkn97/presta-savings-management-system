package dev.marvin.savingsmanagement.savingsaccount;

import dev.marvin.savingsmanagement.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AccountService {

    private final AccountDao accountDao;

    public List<Account> getAllAccounts() {
        List<Account> accounts = accountDao.getAllAccounts();
        if (accounts.isEmpty()){
            throw new ResourceNotFoundException("accounts not found");
        }
        return accounts;
    }

    public Account getAccountById(Long accountId) {
        return accountDao.getAccountById(accountId).orElseThrow(() -> new ResourceNotFoundException("account with id [%s] not found".formatted(accountId)));
    }

    public Account save(Account account){
        return accountDao.save(account);
    }

    public void deleteAccountById(Long accountId) {
        accountDao.deleteAccountById(accountId);
    }

    public List<Account> getAccountsByCustomerId(Long customerId) {
        return accountDao.getAccountsByCustomerId(customerId);
    }

}
