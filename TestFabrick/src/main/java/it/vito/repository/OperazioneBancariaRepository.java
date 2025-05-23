package it.vito.repository;

import it.vito.model.entity.OperazioneBancaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OperazioneBancariaRepository extends JpaRepository<OperazioneBancaria, Integer> {

    List<OperazioneBancaria> findByTransactionId(@Param("transactionId") Integer transactionId);
}
