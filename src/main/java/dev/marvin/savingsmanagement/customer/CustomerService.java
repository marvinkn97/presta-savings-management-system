package dev.marvin.savingsmanagement.customer;

import dev.marvin.savingsmanagement.account.Account;
import dev.marvin.savingsmanagement.account.AccountDto;
import dev.marvin.savingsmanagement.account.AccountService;
import dev.marvin.savingsmanagement.account.AccountType;
import dev.marvin.savingsmanagement.exception.DuplicateResourceException;
import dev.marvin.savingsmanagement.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerDao customerDao;
    private final AccountService accountService;


    public List<Customer> findAllCustomers() {
        return customerDao.findAllCustomers();
    }

    public Customer findCustomerById(UUID id) {
        return customerDao.findCustomerById(id);
    }

    public Customer createCustomer(CustomerDto customerDto) {

        if (customerDao.existsCustomerWithEmail(customerDto.email())) {
            throw new DuplicateResourceException("email already taken");
        }

        Customer newCustomer = Customer.builder()
                .firstName(customerDto.firstName())
                .lastName(customerDto.lastName())
                .email(customerDto.email())
                .nationalID(customerDto.nationalID())
                .phoneNumber(customerDto.phoneNumber())
                .address(customerDto.address())
                .build();

        return customerDao.save(newCustomer);
    }

    public Customer updateCustomer(UUID id, CustomerDto customerDto) {
        Customer existingCustomer = customerDao.findCustomerById(id);

        if (existingCustomer != null) {
            existingCustomer.setFirstName(customerDto.firstName());
            existingCustomer.setLastName(customerDto.lastName());
            existingCustomer.setEmail(customerDto.email());
            existingCustomer.setPhoneNumber(customerDto.phoneNumber());
            existingCustomer.setNationalID(customerDto.nationalID());
            existingCustomer.setAddress(customerDto.address());

            return customerDao.save(existingCustomer);
        }
        throw new ResourceNotFoundException("customer with id " + id + " not found");
    }

    public void deleteCustomerById(UUID id) {
        customerDao.deleteCustomerById(id);
    }

    public BigDecimal getCustomerTotalSavings(UUID customerId) {
        List<Account> customerAccounts = accountService.findAccountsByCustomerId(customerId);

        BigDecimal totalSavings = BigDecimal.ZERO;
        for (Account account : customerAccounts) {
            totalSavings = totalSavings.add(account.getBalance());
        }
        return totalSavings;
    }

    public BigDecimal getCustomersTotalSavings() {
        List<Account> allAccounts = accountService.findAllAccounts();

        BigDecimal total = BigDecimal.ZERO;

        for (Account account : allAccounts) {
            total = total.add(account.getBalance());
        }
        return total;
    }

    public Account createAccount(UUID customerId, AccountDto accountDto) {

        Customer customer = customerDao.findCustomerById(customerId);

        Account newAccount = Account.builder()
                .name(accountDto.name())
                .accountType(AccountType.valueOf(accountDto.accountType()))
                .balance(BigDecimal.ZERO)
                .customer(customer)
                .build();

        return accountService.save(newAccount);

    }

}
