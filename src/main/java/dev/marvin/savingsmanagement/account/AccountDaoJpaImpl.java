package dev.marvin.savingsmanagement.account;

import dev.marvin.savingsmanagement.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class AccountDaoJpaImpl implements AccountDao {

    private final AccountRepository accountRepository;

    @Override
    public List<Account> findAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Account findAccountById(UUID accountId) {
        return accountRepository.findAccountById(accountId).orElseThrow(() -> new ResourceNotFoundException("account with id " + accountId + " not found"));
    }

    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public void deleteAccountById(UUID accountId) {
        accountRepository.deleteById(accountId);
    }

    @Override
    public List<Account> findAccountsByCustomerId(UUID customerId) {
        return accountRepository.findAccountsByCustomerId(customerId);
    }
}
