package dev.marvin.savingsmanagement.customer;

import dev.marvin.savingsmanagement.exception.DuplicateResourceException;
import dev.marvin.savingsmanagement.exception.ResourceNotFoundException;
import dev.marvin.savingsmanagement.savingsaccount.Account;
import dev.marvin.savingsmanagement.savingsaccount.AccountDto;
import dev.marvin.savingsmanagement.savingsaccount.AccountService;
import dev.marvin.savingsmanagement.savingsaccount.AccountType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CustomerService {

    private final CustomerDao customerDao;
    private final AccountService accountService;


    public List<Customer> getAllCustomers() {
        List<Customer> customers = customerDao.getAllCustomers();
        if (customers.isEmpty()) {
            throw new ResourceNotFoundException("no customer found");
        }
        return customers;
    }

    public Customer getCustomerById(Long customerId) {
        return customerDao.getCustomerById(customerId).orElseThrow(() -> new ResourceNotFoundException("Customer with id [%s] not found".formatted(customerId)));
    }

    public Customer createCustomer(CustomerDto newCustomerRegistrationRequest) {
        if (customerDao.existsCustomerWithEmail(newCustomerRegistrationRequest.email())) {
            throw new DuplicateResourceException("email already taken");
        }

        Customer newCustomer = new Customer();
        newCustomer.setName(newCustomerRegistrationRequest.name());
        newCustomer.setEmail(newCustomerRegistrationRequest.email());
        newCustomer.setNationalId(newCustomerRegistrationRequest.nationalID());
        newCustomer.setPhoneNumber(newCustomerRegistrationRequest.phoneNumber());
        newCustomer.setAddress(newCustomerRegistrationRequest.address());

        return customerDao.save(newCustomer);
    }

    public Customer updateCustomer(Long customerId, CustomerDto customerUpdateRequest) {
        Customer existingCustomer = customerDao.getCustomerById(customerId).orElseThrow(() -> new ResourceNotFoundException("Customer with id [%s] not found".formatted(customerId)));

        existingCustomer.setName(customerUpdateRequest.name());
        existingCustomer.setEmail(customerUpdateRequest.email());
        existingCustomer.setNationalId(customerUpdateRequest.nationalID());
        existingCustomer.setPhoneNumber(customerUpdateRequest.phoneNumber());
        existingCustomer.setAddress(customerUpdateRequest.address());

        return customerDao.save(existingCustomer);
    }

    public void deleteCustomerById(Long customerId) {
        customerDao.deleteCustomerById(customerId);
    }

    public BigDecimal getCustomerTotalSavings(Long customerId) {
        List<Account> customerAccounts = accountService.getAccountsByCustomerId(customerId);

        BigDecimal totalSavings = BigDecimal.ZERO;
        for (Account account : customerAccounts) {
            totalSavings = totalSavings.add(account.getBalance());
        }
        return totalSavings;
    }

    public BigDecimal getCustomersTotalSavings() {
        List<Account> allAccounts = accountService.getAllAccounts();

        BigDecimal total = BigDecimal.ZERO;

        for (Account account : allAccounts) {
            total = total.add(account.getBalance());
        }
        return total;
    }

    public Account createAccount(Long customerId, AccountDto newAccountRegistrationRequest) {

        Customer customer = customerDao.getCustomerById(customerId).orElseThrow(() -> new ResourceNotFoundException("Customer with id [%s] not found".formatted(customerId)));

        Account newAccount = new Account();
        newAccount.setAccountNumber(UUID.randomUUID().toString());
        newAccount.setAccountType(AccountType.valueOf(newAccountRegistrationRequest.accountType()));
        newAccount.setBalance(BigDecimal.ZERO);
        newAccount.setCustomer(customer);

        return accountService.save(newAccount);
    }

}
