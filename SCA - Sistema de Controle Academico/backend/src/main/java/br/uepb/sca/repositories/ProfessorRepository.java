package br.uepb.sca.repositories;

import br.uepb.sca.models.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    Optional<Professor> findBySiape(String siape);
    Optional<Professor> findByEmail(String email);
}
