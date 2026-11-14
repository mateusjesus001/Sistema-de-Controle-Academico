package br.uepb.sca.repositories;

import br.uepb.sca.models.PlantaoDuvida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlantaoDuvidaRepository extends JpaRepository<PlantaoDuvida, Long> {
    List<PlantaoDuvida> findByMonitorId(Long monitorId);
    List<PlantaoDuvida> findByDisciplinaId(Long disciplinaId);
}
