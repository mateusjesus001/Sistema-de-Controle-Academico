package br.uepb.sca.repositories;

import br.uepb.sca.models.PeriodoLetivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PeriodoLetivoRepository extends JpaRepository<PeriodoLetivo, Long> {
    Optional<PeriodoLetivo> findByAtivoTrue();
    Optional<PeriodoLetivo> findByAnoAndPeriodo(int ano, int periodo);
}
