package dev.marvin.savingsmanagement.customer;

import java.util.List;

public interface CustomerDao {

    List<Customer> findAll();
    Customer findCustomerById(int id);
    Customer save(Customer customer);
    void deleteCustomerById(int id);
    boolean existsCustomerWithEmail(String email);
}
