package dev.marvin.savingsmanagement.customer;

import dev.marvin.savingsmanagement.account.Account;
import dev.marvin.savingsmanagement.account.AccountDto;
import dev.marvin.savingsmanagement.account.AccountService;
import dev.marvin.savingsmanagement.account.AccountType;
import dev.marvin.savingsmanagement.exception.DuplicateResourceException;
import dev.marvin.savingsmanagement.exception.RequestValidationException;
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

    public Customer createCustomer(CustomerDto newCustomerRegistrationRequest) {
        if (customerDao.existsCustomerWithEmail(newCustomerRegistrationRequest.email())) {
            throw new DuplicateResourceException("email already taken");
        }
        Customer newCustomer = Customer.builder()
                .firstName(newCustomerRegistrationRequest.firstName())
                .lastName(newCustomerRegistrationRequest.lastName())
                .email(newCustomerRegistrationRequest.email())
                .nationalID(newCustomerRegistrationRequest.nationalID())
                .phoneNumber(newCustomerRegistrationRequest.phoneNumber())
                .address(newCustomerRegistrationRequest.address())
                .build();

        return customerDao.save(newCustomer);
    }

    public Customer updateCustomer(UUID id, CustomerDto customerUpdateRequest) {
        Customer existingCustomer = customerDao.findCustomerById(id);
        boolean changes = false;

        if (existingCustomer != null) {

            if (customerUpdateRequest.firstName() != null && !customerUpdateRequest.firstName().equalsIgnoreCase(existingCustomer.getFirstName())) {
                existingCustomer.setFirstName(customerUpdateRequest.firstName());
                changes = true;
            }

            if (customerUpdateRequest.lastName() != null && !customerUpdateRequest.lastName().equalsIgnoreCase(existingCustomer.getLastName())) {
                existingCustomer.setLastName(customerUpdateRequest.lastName());
                changes = true;
            }

            if (customerUpdateRequest.email() != null && !customerUpdateRequest.email().equalsIgnoreCase(existingCustomer.getEmail())) {
                if (customerDao.existsCustomerWithEmail(customerUpdateRequest.email())) {
                    throw new DuplicateResourceException("email already taken");
                }
                existingCustomer.setEmail(customerUpdateRequest.email());
                changes = true;
            }

            if (customerUpdateRequest.phoneNumber() != null && !customerUpdateRequest.phoneNumber().equals(existingCustomer.getPhoneNumber())) {
                existingCustomer.setPhoneNumber(customerUpdateRequest.phoneNumber());
                changes = true;
            }

            if (customerUpdateRequest.nationalID() != null && !customerUpdateRequest.nationalID().equals(existingCustomer.getNationalID())) {
                existingCustomer.setNationalID(customerUpdateRequest.nationalID());
                changes = true;
            }

            if (customerUpdateRequest.address() != null && !customerUpdateRequest.address().equals(existingCustomer.getAddress())) {
                existingCustomer.setAddress(customerUpdateRequest.address());
            }

            if (!changes) {
                throw new RequestValidationException("no data changes found");
            }

            return customerDao.save(existingCustomer);
        }
        throw new ResourceNotFoundException("customer with id [%s] not found".formatted(id));
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

    public Account createAccount(UUID customerId, AccountDto newAccountRegistrationRequest) {

        Customer customer = customerDao.findCustomerById(customerId);

        Account newAccount = Account.builder()
                .name(newAccountRegistrationRequest.name())
                .accountType(AccountType.valueOf(newAccountRegistrationRequest.accountType().toUpperCase()))
                .balance(BigDecimal.ZERO)
                .customer(customer)
                .build();

        return accountService.save(newAccount);
    }

}
