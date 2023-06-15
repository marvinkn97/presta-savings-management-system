package dev.marvin.savingsmanagement.transaction;

import java.math.BigDecimal;

public record TransactionDto(Integer accountId, BigDecimal amount, String transactionType) {
}
