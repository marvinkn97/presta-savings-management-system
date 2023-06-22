package dev.marvin.savingsmanagement.customer;

import java.util.List;
import java.util.UUID;

public interface CustomerDao {

    List<Customer> findAllCustomers();
    Customer findCustomerById(UUID id);
    Customer save(Customer customer);
    void deleteCustomerById(UUID id);
    boolean existsCustomerWithEmail(String email);
}
