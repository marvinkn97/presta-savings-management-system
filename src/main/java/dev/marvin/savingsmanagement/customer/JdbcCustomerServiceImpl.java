package dev.marvin.savingsmanagement.customer;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Qualifier(value = "JDBCServiceImpl")
@AllArgsConstructor
public class JdbcCustomerServiceImpl implements CustomerService {

    private final JdbcTemplate jdbcTemplate;

    //TODO: implement spring data jdbc

    private static final String FETCH_ALL_CUSTOMERS = """
            SELECT * FROM CUSTOMERS
            """;
    private static final String FETCH_CUSTOMER_BY_ID = """
                       
            """;
    private static final String SAVE_CUSTOMER = """
            """;

    private static final String DELETE_CUSTOMER = """
            """;

    @Override
    public List<Customer> findAll() {
        return null;
    }

    @Override
    public Customer findCustomerById(int id) {
        return null;
    }

    @Override
    public Customer save(Customer customer) {
        return null;
    }

    @Override
    public void deleteCustomerById(int id) {

    }
}
