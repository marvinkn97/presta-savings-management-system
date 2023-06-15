package dev.marvin.savingsmanagement.customer;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CustomerService {
    List<Customer> findAll();
    Customer findCustomerById(int id);
    Customer save(Customer customer);
    void deleteCustomerById(int id);
}
