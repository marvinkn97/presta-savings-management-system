package dev.marvin.savingsmanagement.customer;

import dev.marvin.savingsmanagement.savingsaccount.Account;
import dev.marvin.savingsmanagement.savingsaccount.AccountDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Tag(name = "Customer API")
@RestController
@RequestMapping(value = "/api/v1/customers", produces = "application/json")
public class CustomerController {

    private final CustomerService customerService;

    @Operation(summary = "Get All Customers")
    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @Operation(summary = "Get Customer by ID")
    @GetMapping("/{customerId}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("customerId") Long customerId) {
        Customer customer = customerService.getCustomerById(customerId);
            return ResponseEntity.ok(customer);
    }

    @Operation(summary = "Create New Customer [ Unique : Email && National ID ]")
    @PostMapping
    public ResponseEntity<Customer> createNewCustomer(@Validated @RequestBody CustomerDto newCustomerRegistrationRequest) {
        Customer createdCustomer = customerService.createCustomer(newCustomerRegistrationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
    }

    @PutMapping("/{customerId}")
    @Operation(summary = "Update Customer by ID")
    public ResponseEntity<Customer> updateCustomer(@PathVariable("customerId") Long customerId, @Validated @RequestBody CustomerDto customerDto) {
        Customer updatedCustomer = customerService.updateCustomer(customerId, customerDto);
        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping("/{customerId}")
    @Operation(summary = "Delete Customer by ID")
    public ResponseEntity<Customer> deleteCustomerById(@PathVariable("customerId") Long customerId) {
        customerService.deleteCustomerById(customerId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Create New Account By Customer ID [ AccountType : EDUCATION || PERSONAL || VACATION ]")
    @PostMapping("/{customerId}/accounts/create")
    public ResponseEntity<Account> createAccount(@PathVariable("customerId") Long customerId, @RequestBody AccountDto newAccountRegistrationRequest) {
        Account createdAccount = customerService.createAccount(customerId, newAccountRegistrationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount);
    }

    @GetMapping("/{customerId}/savings")
    @Operation(summary = "Track the total savings amount By Customer ID")
    public ResponseEntity<BigDecimal> getCustomerTotalSavings(@PathVariable("customerId") Long customerId) {

        BigDecimal totalSavings = customerService.getCustomerTotalSavings(customerId);
        return ResponseEntity.ok(totalSavings);
    }

    @GetMapping("/total")
    @Operation(summary = "Track the total savings amount across all Customers")
    public ResponseEntity<BigDecimal> getCustomersTotalSavings() {

        BigDecimal total = customerService.getCustomersTotalSavings();
        return ResponseEntity.ok(total);
    }
}
