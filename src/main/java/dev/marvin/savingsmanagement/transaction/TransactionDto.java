package dev.marvin.savingsmanagement.transaction;

import java.math.BigDecimal;
import java.util.UUID;

public record TransactionDto(UUID accountId, BigDecimal amount, String transactionType) {
}
