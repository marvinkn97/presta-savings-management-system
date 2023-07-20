package dev.marvin.savingsmanagement.transaction;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TransactionDto(
        @NotNull @NotBlank Long accountId,

        @NotNull BigDecimal amount,

        @NotNull TransactionType transactionType) {
}
