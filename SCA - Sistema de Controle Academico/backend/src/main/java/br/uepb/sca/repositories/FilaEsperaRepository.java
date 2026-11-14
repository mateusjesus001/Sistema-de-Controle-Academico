package br.uepb.sca.repositories;

import br.uepb.sca.models.FilaEspera;
import br.uepb.sca.models.enums.StatusFila;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FilaEsperaRepository extends JpaRepository<FilaEspera, Long> {
    List<FilaEspera> findByTurmaIdOrderByPosicaoFilaAsc(Long turmaId);
    Optional<FilaEspera> findByAlunoIdAndTurmaId(Long alunoId, Long turmaId);
    List<FilaEspera> findByTurmaIdAndStatusOrderByPosicaoFilaAsc(Long turmaId, StatusFila status);
}
