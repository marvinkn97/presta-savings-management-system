package dev.marvin.savingsmanagement.savingsaccount;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findAccountById(Long account);
    @Query(value = "SELECT a FROM Account a WHERE a.customer.id = :id")
    List<Account> findAccountsByCustomerId(@Param("id") Long customerId);
}
