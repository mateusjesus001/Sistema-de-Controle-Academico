package br.uepb.sca.repositories;

import br.uepb.sca.models.LogAuditoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogAuditoriaRepository extends JpaRepository<LogAuditoria, Long> {
    List<LogAuditoria> findByEntidadeOrderByDataHoraDesc(String entidade);
    List<LogAuditoria> findByRegistroIdOrderByDataHoraDesc(Long registroId);
}
