package dev.marvin.savingsmanagement.savingsaccount;

import dev.marvin.savingsmanagement.customer.Customer;
import dev.marvin.savingsmanagement.transaction.Transaction;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Data
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "account_number_unique", columnNames = "accountNumber"),
})
public class Account {
    @Id
    @SequenceGenerator(name = "account_id_sequence", sequenceName ="account_id_sequence" )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_id_sequence")
    @Column(updatable = false)
    private Long id;

    @Column(nullable = false)
    private String accountNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType accountType;

    @Column(nullable = false)
    private BigDecimal balance;

    @CreationTimestamp
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime updatedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "account")
    private Set<Transaction> transactionHistory = new HashSet<>();
}
