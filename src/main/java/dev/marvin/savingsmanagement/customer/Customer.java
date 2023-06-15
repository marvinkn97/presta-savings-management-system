package dev.marvin.savingsmanagement.customer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.marvin.savingsmanagement.account.Account;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue
    private Integer id;

    @NotNull(message = "First Name is required")
    private String firstName;

    @NotNull(message = "Last Name is required")
    private String lastName;

    @Column(unique = true)
    @Email
    private String email;

    @NotNull(message = "Phone is required")
    private String phoneNumber;

    @NotNull(message = "National ID Number is required")
    @Column(name = "national_id_number", unique = true)
    private String nationalID;

    @NotNull(message = "Address is required")
    private String address;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @JsonIgnore
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<Account> accounts = new HashSet<>();
}
