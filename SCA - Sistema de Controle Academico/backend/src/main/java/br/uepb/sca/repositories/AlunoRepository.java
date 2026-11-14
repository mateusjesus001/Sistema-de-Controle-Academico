package br.uepb.sca.repositories;

import br.uepb.sca.models.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    Optional<Aluno> findByMatricula(String matricula);
    Optional<Aluno> findByEmail(String email);
    List<Aluno> findByCursoId(Long cursoId);
}
