package dev.marvin.savingsmanagement.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.marvin.savingsmanagement.customer.Customer;
import dev.marvin.savingsmanagement.transaction.Transaction;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
@Data
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue
    private Integer id;

    @NotNull(message = "Account Name is required")
    private String name;

    @Column(unique = true)
    private String accountNumber;

    @NotNull(message = "Account Type is required")
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    private BigDecimal balance = new BigDecimal(0);

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @JsonIgnore
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private Set<Transaction> transactionHistory = new HashSet<>();
}
