package dev.marvin.savingsmanagement.customer;

import dev.marvin.savingsmanagement.exception.DuplicateResourceException;
import dev.marvin.savingsmanagement.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CustomerService {
    private final CustomerDao customerDao;
//    private final AccountService accountService;

    public List<Customer> findAllCustomers() {
        return customerDao.findAll();
    }

    public Customer findCustomerById(int id) {
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

    public Customer updateCustomer(int id, CustomerDto customerDto) {
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

    public void deleteCustomerById(int id) {
        customerDao.deleteCustomerById(id);
    }

//    public BigDecimal getCustomerTotalSavings(int customerId) {
//        List<Account> customerAccounts = accountService.findAccountsByCustomerId(customerId);
//
//        BigDecimal totalSavings = BigDecimal.ZERO;
//        for (Account account : customerAccounts) {
//            totalSavings = totalSavings.add(account.getBalance());
//        }
//        return totalSavings;
//    }
//
//    public BigDecimal getCustomersTotalSavings() {
//        List<Account> allAccounts = accountService.findAllAccounts();
//
//        BigDecimal total = BigDecimal.ZERO;
//
//        for (Account account : allAccounts) {
//            total = total.add(account.getBalance());
//        }
//        return total;
//    }

}
