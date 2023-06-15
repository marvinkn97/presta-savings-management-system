package dev.marvin.savingsmanagement.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    Optional<Account> findAccountById(int id);
    @Query(value = "SELECT a FROM Account a WHERE a.customer.id = :id")
    List<Account> findAccountsByCustomerId(@Param("id") int id);
}
