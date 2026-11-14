package br.uepb.sca.repositories;

import br.uepb.sca.models.MaterialAcervo;
import br.uepb.sca.models.enums.StatusModera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialAcervoRepository extends JpaRepository<MaterialAcervo, Long> {
    List<MaterialAcervo> findByStatus(StatusModera status);
    List<MaterialAcervo> findByDisciplinaIdAndStatus(Long disciplinaId, StatusModera status);
    List<MaterialAcervo> findByAutorId(Long autorId);
}
