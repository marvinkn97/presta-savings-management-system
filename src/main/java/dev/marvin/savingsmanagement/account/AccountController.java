package dev.marvin.savingsmanagement.account;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/accounts")
@Tag(name = "Savings Product API")
public class AccountController {
    private final AccountService accountService;

    @GetMapping
    @Operation(summary = "Get All Accounts")
    public List<Account> getAllAccounts() {
        return accountService.findAllAccounts();
    }

    @GetMapping("/{accountId}")
    @Operation(summary = "Get Account By Account ID")
    public ResponseEntity<Account> getAccountById(@PathVariable("accountId") UUID id) {
        Account existingAccount = accountService.findAccountById(id);
        if (existingAccount != null) {
            return ResponseEntity.ok(existingAccount);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/customers/{customerId}")
    @Operation(summary = "Get All Accounts By Customer ID")
    public List<Account> getAccountsByCustomerId(@PathVariable("customerId") UUID id) {
        return accountService.findAccountsByCustomerId(id);
    }

    @PutMapping("/{accountId}")
    @Operation(summary = "Update Account by Account ID")
    public ResponseEntity<Account> updateAccount(@PathVariable("accountId") UUID id, @Validated @RequestBody AccountDto accountDto) {

        Account account = accountService.findAccountById(id);

        if (account != null) {
            account.setName(accountDto.name());
            account.setAccountType(AccountType.valueOf(accountDto.accountType()));
            Account updatedAccount = accountService.save(account);
            return ResponseEntity.ok(updatedAccount);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{accountId}")
    @Operation(summary = "Delete Account by ID")
    public ResponseEntity<String> deleteAccountById(@PathVariable("accountId") UUID id) {
        accountService.deleteAccountById(id);
        return ResponseEntity.ok("Account deleted successfully");
    }

}
