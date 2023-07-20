package dev.marvin.savingsmanagement.savingsaccount;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Savings Product API")
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {
    private final AccountService accountService;

    @Operation(summary = "Get All Accounts")
    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @Operation(summary = "Get Account By Account ID")
    @GetMapping("/{accountId}")
    public ResponseEntity<Account> getAccountById(@PathVariable("accountId") Long accountId) {
        Account account = accountService.getAccountById(accountId);
            return ResponseEntity.ok(account);
        }

    @GetMapping("/customers/{customerId}")
    @Operation(summary = "Get All Accounts By Customer ID")
    public List<Account> getAccountsByCustomerId(@PathVariable("customerId") Long accountId) {
        return accountService.getAccountsByCustomerId(accountId);
    }

    @DeleteMapping("/{accountId}")
    @Operation(summary = "Delete Account by ID")
    public ResponseEntity<Account> deleteAccountById(@PathVariable("accountId")  Long accountId) {
        accountService.deleteAccountById(accountId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
