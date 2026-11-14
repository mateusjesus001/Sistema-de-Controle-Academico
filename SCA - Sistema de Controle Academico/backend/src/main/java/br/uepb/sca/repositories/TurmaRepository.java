package br.uepb.sca.repositories;

import br.uepb.sca.models.Turma;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TurmaRepository extends JpaRepository<Turma, Long> {

    List<Turma> findByPeriodoLetivoId(Long periodoLetivoId);

    List<Turma> findByDisciplinaId(Long disciplinaId);

    List<Turma> findByProfessorId(Long professorId);

    /**
     * RN09 e RN27 / UC11: Bloqueio pessimista (SELECT FOR UPDATE)
     * Garante concorrência atômica segura na disputa da última vaga.
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT t FROM Turma t WHERE t.id = :id")
    Optional<Turma> findByIdWithLock(@Param("id") Long id);
}
