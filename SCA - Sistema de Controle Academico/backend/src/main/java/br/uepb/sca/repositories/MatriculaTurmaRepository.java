package br.uepb.sca.repositories;

import br.uepb.sca.models.MatriculaTurma;
import br.uepb.sca.models.enums.StatusMatricula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatriculaTurmaRepository extends JpaRepository<MatriculaTurma, Long> {
    List<MatriculaTurma> findByAlunoId(Long alunoId);
    List<MatriculaTurma> findByTurmaId(Long turmaId);
    Optional<MatriculaTurma> findByAlunoIdAndTurmaId(Long alunoId, Long turmaId);
    List<MatriculaTurma> findByAlunoIdAndStatus(Long alunoId, StatusMatricula status);
}
