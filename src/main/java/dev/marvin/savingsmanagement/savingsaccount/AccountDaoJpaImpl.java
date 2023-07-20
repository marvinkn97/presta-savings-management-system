package dev.marvin.savingsmanagement.savingsaccount;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AccountDaoJpaImpl implements AccountDao {

    private final AccountRepository accountRepository;

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Optional<Account> getAccountById(Long accountId) {
        return accountRepository.findAccountById(accountId);
    }

    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public void deleteAccountById(Long accountId) {
        accountRepository.deleteById(accountId);
    }

    @Override
    public List<Account> getAccountsByCustomerId(Long customerId) {
        return accountRepository.findAccountsByCustomerId(customerId);
    }
}
