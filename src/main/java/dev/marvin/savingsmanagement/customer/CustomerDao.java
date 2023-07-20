package dev.marvin.savingsmanagement.customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDao {
    List<Customer> getAllCustomers();
    Optional<Customer> getCustomerById(Long customerId);
    Customer save(Customer customer);
    void deleteCustomerById(Long customerId);
    boolean existsCustomerWithEmail(String email);
}
