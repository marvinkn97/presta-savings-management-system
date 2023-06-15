package dev.marvin.savingsmanagement.transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    @Query(value = "SELECT t FROM Transaction t WHERE t.id = :id")
    Optional<Transaction> findTransactionById(@Param("id") int id);

    @Query(value = "SELECT t FROM Transaction t where t.account.id = :id")
    List<Transaction> findTransactionByAccount_Id(@Param("id") int id);

    @Query(value = "SELECT t FROM Transaction t where t.customerId = :id")
    List<Transaction> findTransactionByCustomerId(int id);
}
