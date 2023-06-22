package dev.marvin.savingsmanagement.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {

    Optional<Account> findAccountById(UUID id);
    @Query(value = "SELECT a FROM Account a WHERE a.customer.id = :id")
    List<Account> findAccountsByCustomerId(@Param("id") UUID id);
}
