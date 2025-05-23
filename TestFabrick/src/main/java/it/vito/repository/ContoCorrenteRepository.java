package it.vito.repository;

import it.vito.model.entity.ContoCorrente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContoCorrenteRepository extends JpaRepository<ContoCorrente, Integer> {

    Optional<ContoCorrente> findByidAccount(@Param("idAccount") Integer idAccount);


}
