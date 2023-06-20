package dev.marvin.savingsmanagement.account;

import dev.marvin.savingsmanagement.customer.Customer;
import dev.marvin.savingsmanagement.customer.CustomerService;
import dev.marvin.savingsmanagement.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private CustomerService customerService;

    public List<Account> findAllAccounts(){
        return accountRepository.findAll();
    }

    public Account findAccountById(int id){
        return accountRepository
                .findAccountById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Account with ID: " + id + "not found"));
    }
    public Account saveAccount(Account account){
        return accountRepository.save(account);
    }

    public Account createAccount(int customerId, AccountDto accountDto){

        Customer customer = customerService.findCustomerById(customerId);

        Account newAccount = new Account();
        newAccount.setName(accountDto.name());
        newAccount.setAccountNumber(UUID.randomUUID().toString());
        newAccount.setAccountType(AccountType.valueOf(accountDto.accountType()));
        newAccount.setCustomer(customer);

        return accountRepository.save(newAccount);
    }

    public void deleteAccountById(int id){
        accountRepository.deleteById(id);
    }

    public List<Account> findAccountsByCustomerId(int id){
        return accountRepository.findAccountsByCustomerId(id);
    }



}
