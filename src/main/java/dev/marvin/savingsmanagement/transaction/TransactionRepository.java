package dev.marvin.savingsmanagement.transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query(value = "SELECT t FROM Transaction t WHERE t.id = :id")
    Optional<Transaction> findTransactionById(@Param("id") Long transactionId);

    @Query(value = "SELECT t FROM Transaction t where t.account.id = :id")
    List<Transaction> findTransactionByAccount_Id(@Param("id") Long accountId);

    @Query(value = "SELECT t FROM Transaction t where t.customerId = :id")
    List<Transaction> findTransactionByCustomerId(@Param("id") Long customerId);
}
