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
        return customerService.findAll();
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
    public ResponseEntity<Customer> createCustomer(@RequestBody CustomerDto customerDto) {

        Customer newCustomer = Customer.builder()
                .firstName(customerDto.firstName())
                .lastName(customerDto.lastName())
                .email(customerDto.email())
                .nationalID(customerDto.nationalID())
                .phoneNumber(customerDto.phoneNumber())
                .address(customerDto.address())
                .build();

        Customer createdCustomer = customerService.save(newCustomer);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
    }

    @PutMapping("/{customerId}")
    @Operation(summary = "Update Customer by ID")
    public ResponseEntity<Customer> updateCustomer(@PathVariable("customerId") int id, @RequestBody CustomerDto customerDto) {
        Customer existingCustomer = customerService.findCustomerById(id);

        if (existingCustomer != null) {
            existingCustomer.setFirstName(customerDto.firstName());
            existingCustomer.setLastName(customerDto.lastName());
            existingCustomer.setEmail(customerDto.email());
            existingCustomer.setPhoneNumber(customerDto.phoneNumber());
            existingCustomer.setNationalID(customerDto.nationalID());
            existingCustomer.setAddress(customerDto.address());

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
    @Operation(summary = "Track the total savings amount By Customer ID")
    public ResponseEntity<BigDecimal> getCustomerTotalSavings(@PathVariable("customerId") int id) {
        List<Account> customerAccounts = accountService.findAccountsByCustomerId(id);

        BigDecimal totalSavings = BigDecimal.ZERO;
        for (Account account : customerAccounts) {
            totalSavings = totalSavings.add(account.getBalance());

        }
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
        return ResponseEntity.ok(total);
    }
}
