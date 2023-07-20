package dev.marvin.savingsmanagement.customer;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Primary
@Repository
public class CustomerDaoJpaImpl implements CustomerDao {

    private final CustomerRepository customerRepository;

    public CustomerDaoJpaImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer>  getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> getCustomerById(Long customerId) {
        return customerRepository.findCustomerById(customerId);
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public void deleteCustomerById(Long customerId) {
        customerRepository.deleteById(customerId);
    }

    @Override
    public boolean existsCustomerWithEmail(String email) {
        return customerRepository.existsCustomerByEmail(email);
    }
}
