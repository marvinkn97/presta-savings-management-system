package dev.marvin.savingsmanagement.transaction;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record TransactionDto(
        @NotNull
        @NotBlank
        UUID accountId,

        @NotNull
        @NotBlank
        BigDecimal amount,

        @NotNull
        @NotBlank
        String transactionType) {
}
