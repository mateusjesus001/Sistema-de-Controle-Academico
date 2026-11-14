package br.uepb.sca.repositories;

import br.uepb.sca.models.DiarioDeClasse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiarioDeClasseRepository extends JpaRepository<DiarioDeClasse, Long> {
    Optional<DiarioDeClasse> findByTurmaId(Long turmaId);
}
