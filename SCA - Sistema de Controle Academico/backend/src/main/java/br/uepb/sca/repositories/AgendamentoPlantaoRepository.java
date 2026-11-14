package br.uepb.sca.repositories;

import br.uepb.sca.models.AgendamentoPlantao;
import br.uepb.sca.models.enums.StatusAgendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgendamentoPlantaoRepository extends JpaRepository<AgendamentoPlantao, Long> {
    List<AgendamentoPlantao> findByAlunoId(Long alunoId);
    List<AgendamentoPlantao> findByPlantaoId(Long plantaoId);
    List<AgendamentoPlantao> findByPlantaoMonitorId(Long monitorId);
    List<AgendamentoPlantao> findByPlantaoMonitorIdAndStatus(Long monitorId, StatusAgendamento status);
}
