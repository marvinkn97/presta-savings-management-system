package dev.marvin.savingsmanagement.transaction;

import dev.marvin.savingsmanagement.account.Account;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID customerId;

    @CreationTimestamp
    private LocalDate transactionDate;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;



}
