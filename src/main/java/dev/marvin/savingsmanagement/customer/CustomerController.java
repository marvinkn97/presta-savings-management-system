package dev.marvin.savingsmanagement.customer;

import dev.marvin.savingsmanagement.account.Account;
import dev.marvin.savingsmanagement.account.AccountDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Tag(name = "Customer API")
@RestController
@RequestMapping(value = "/api/v1/customers", produces = "application/json")
public class CustomerController {

    private final CustomerService customerService;

    @ResponseStatus(code = HttpStatus.OK)
    @Operation(summary = "Get All Customers")
    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.findAllCustomers();
    }

    @Operation(summary = "Get Customer by ID")
    @GetMapping("/{customerId}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("customerId") UUID id) {
        Customer customer = customerService.findCustomerById(id);

        if (customer != null) {
            return ResponseEntity.ok(customer);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Create New Customer")
    @PostMapping
    public ResponseEntity<Customer> registerCustomer(@Validated @RequestBody CustomerDto customerDto) {

        Customer createdCustomer = customerService.createCustomer(customerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
    }

    @PutMapping("/{customerId}")
    @Operation(summary = "Update Customer by ID")
    public ResponseEntity<Customer> updateCustomer(@PathVariable("customerId") UUID id, @Validated @RequestBody CustomerDto customerDto) {

        Customer updatedCustomer = customerService.updateCustomer(id, customerDto);
        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping("/{customerId}")
    @Operation(summary = "Delete Customer by ID")
    public ResponseEntity<Customer> deleteCustomerById(@PathVariable("customerId") UUID id) {
        customerService.deleteCustomerById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Create New Account By Customer ID [ AccountType : EDUCATION, PERSONAL, VACATION]")
    @PostMapping("/{customerId}/accounts/create")
    public ResponseEntity<Account> createAccount(@PathVariable("customerId") UUID customerId, @RequestBody AccountDto accountDto) {
        Account createdAccount = customerService.createAccount(customerId, accountDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount);
    }

    @GetMapping("/{customerId}/savings")
    @Operation(summary = "Track the total savings amount By Customer ID")
    public ResponseEntity<BigDecimal> getCustomerTotalSavings(@PathVariable("customerId") UUID customerId) {

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
