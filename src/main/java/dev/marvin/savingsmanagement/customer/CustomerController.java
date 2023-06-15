package dev.marvin.savingsmanagement.customer;

import dev.marvin.savingsmanagement.account.Account;
import dev.marvin.savingsmanagement.account.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
@AllArgsConstructor
@Tag(name = "Customer API")
public class CustomerController {

    private final CustomerService customerService;
    private final AccountService accountService;


    @GetMapping
    @Operation(summary = "Get All Customers")
    public List<Customer> getAllCustomers() {
        return customerService.findAll();
    }

    @GetMapping("/{customerId}")
    @Operation(summary = "Get Customer by ID")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("customerId") int id) {
        Customer customer = customerService.findCustomerById(id);

        if (customer != null) {
            return ResponseEntity.ok(customer);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create Customer")
    public ResponseEntity<Customer> createCustomer(@RequestBody CustomerDto customerDto) {

        Customer newCustomer = new Customer();
        customerEntry(customerDto, newCustomer);

        Customer createdCustomer = customerService.save(newCustomer);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
    }

    @PutMapping("/{customerId}")
    @Operation(summary = "Update Customer by ID")
    public ResponseEntity<Customer> updateCustomer(@PathVariable("customerId") int id, @RequestBody CustomerDto customerDto) {
        Customer existingCustomer = customerService.findCustomerById(id);

        if (existingCustomer != null) {
            customerEntry(customerDto, existingCustomer);

            Customer updatedCustomer = customerService.save(existingCustomer);

            return ResponseEntity.ok(updatedCustomer);
        }
        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{customerId}")
    @Operation(summary = "Delete Customer by ID")
    public ResponseEntity<String> deleteCustomerById(@PathVariable("customerId") int id) {
        customerService.deleteCustomerById(id);
        return ResponseEntity.ok("Customer deleted successfully");
    }


    @GetMapping("/{customerId}/savings")
    @Operation(summary = "Track the total savings amount for each customer")
    public ResponseEntity<BigDecimal> getCustomerTotalSavings(@PathVariable("customerId") int id) {
        List<Account> customerAccounts = accountService.findAccountsByCustomerId(id);

        BigDecimal totalSavings = BigDecimal.ZERO;
        for (Account account : customerAccounts) {
            totalSavings = totalSavings.add(account.getBalance());

        }
        return ResponseEntity.ok(totalSavings);
    }

    @GetMapping("/total")
    @Operation(summary = "Track the total savings amount across all Customers" )
    public ResponseEntity<BigDecimal> getCustomersTotalSavings() {
        List<Account> allAccounts = accountService.findAllAccounts();

        BigDecimal total = BigDecimal.ZERO;

        for (Account account : allAccounts) {
            total = total.add(account.getBalance());
        }
        return ResponseEntity.ok(total);
    }

    private void customerEntry(@RequestBody CustomerDto customerDto, Customer customer) {
        customer.setFirstName(customerDto.firstName());
        customer.setLastName(customerDto.lastName());
        customer.setEmail(customerDto.email());
        customer.setPhoneNumber(customerDto.phoneNumber());
        customer.setNationalID(customerDto.nationalID());
        customer.setAddress(customerDto.address());
    }

}
