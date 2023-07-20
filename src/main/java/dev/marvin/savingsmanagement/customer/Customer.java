package dev.marvin.savingsmanagement.customer;

import dev.marvin.savingsmanagement.savingsaccount.Account;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Data
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "customer_email_unique", columnNames = "email"),
        @UniqueConstraint(name = "customer_nationalId_unique", columnNames = "nationalId"),
})
public class Customer {
    @Id
    @SequenceGenerator(name = "customer_id_sequence", sequenceName = "customer_id_sequence" )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_id_sequence")
    @Column(updatable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String nationalId;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String address;

    @CreationTimestamp
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime updatedDate;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<Account> accounts = new HashSet<>();
}
