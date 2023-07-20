package dev.marvin.savingsmanagement.transaction;

import dev.marvin.savingsmanagement.savingsaccount.Account;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@Data
@Entity
public class Transaction {
    @Id
    @SequenceGenerator(name = "transaction_id_sequence", sequenceName = "transaction_id_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_id_sequence" )
    @Column(updatable = false)
    private Long id;

    @Column(nullable = false)
    private Long customerId;

    @CreationTimestamp
    private LocalDate transactionDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType transactionType;

    @Column(nullable = false)
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

}
