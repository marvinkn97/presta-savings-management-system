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

@AllArgsConstructor
@Tag(name = "Customer API")
@RestController
@RequestMapping(value = "/api/v1/customers", produces = "application/json")
public class CustomerController {

    private final CustomerService customerService;
    private final AccountService accountService;

    @ResponseStatus(code = HttpStatus.OK)
    @Operation(summary = "Get All Customers")
    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.findAllCustomers();
    }

    @Operation(summary = "Get Customer by ID")
    @GetMapping("/{customerId}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("customerId") int id) {
        Customer customer = customerService.findCustomerById(id);

        if (customer != null) {
            return ResponseEntity.ok(customer);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Create New Customer")
    @PostMapping
    public ResponseEntity<Customer> registerCustomer(@RequestBody CustomerDto customerDto) {

        Customer createdCustomer = customerService.createCustomer(customerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
    }

    @PutMapping("/{customerId}")
    @Operation(summary = "Update Customer by ID")
    public ResponseEntity<Customer> updateCustomer(@PathVariable("customerId") int id, @RequestBody CustomerDto customerDto) {

        Customer updatedCustomer = customerService.updateCustomer(id, customerDto);
        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping("/{customerId}")
    @Operation(summary = "Delete Customer by ID")
    public ResponseEntity<String> deleteCustomerById(@PathVariable("customerId") int id) {
        customerService.deleteCustomerById(id);
        return ResponseEntity.ok("Customer deleted successfully");
    }

    @GetMapping("/{customerId}/savings")
    @Operation(summary = "Track the total savings amount By Customer ID")
    public ResponseEntity<BigDecimal> getCustomerTotalSavings(@PathVariable("customerId") int customerId) {

        List<Account> customerAccounts = accountService.findAccountsByCustomerId(customerId);

        BigDecimal totalSavings = BigDecimal.ZERO;
        for (Account account : customerAccounts) {
            totalSavings = totalSavings.add(account.getBalance());
        }

//        BigDecimal totalSavings = customerService.getCustomerTotalSavings(customerId);

        return ResponseEntity.ok(totalSavings);
    }

    @GetMapping("/total")
    @Operation(summary = "Track the total savings amount across all Customers")
    public ResponseEntity<BigDecimal> getCustomersTotalSavings() {

        List<Account> allAccounts = accountService.findAllAccounts();
        BigDecimal total = BigDecimal.ZERO;

        for (Account account : allAccounts) {
            total = total.add(account.getBalance());
        }

//        BigDecimal total = customerService.getCustomersTotalSavings();
        return ResponseEntity.ok(total);
    }
}
