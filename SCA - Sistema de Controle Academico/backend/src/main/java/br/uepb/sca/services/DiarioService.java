package br.uepb.sca.services;

import br.uepb.sca.dtos.LancamentoNotaDTO;
import br.uepb.sca.models.DiarioDeClasse;
import br.uepb.sca.models.MatriculaTurma;
import br.uepb.sca.repositories.DiarioDeClasseRepository;
import br.uepb.sca.repositories.MatriculaTurmaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DiarioService {

    private final DiarioDeClasseRepository diarioRepository;
    private final MatriculaTurmaRepository matriculaTurmaRepository;

    public DiarioService(DiarioDeClasseRepository diarioRepository, MatriculaTurmaRepository matriculaTurmaRepository) {
        this.diarioRepository = diarioRepository;
        this.matriculaTurmaRepository = matriculaTurmaRepository;
    }

    @Transactional
    public void lancarNota(LancamentoNotaDTO dto) {
        MatriculaTurma matricula = matriculaTurmaRepository.findById(dto.getMatriculaId())
                .orElseThrow(() -> new IllegalArgumentException("Matrícula não encontrada."));

        switch (dto.getAvaliacaoNumero()) {
            case 1 -> matricula.setNota1(dto.getNota());
            case 2 -> matricula.setNota2(dto.getNota());
            case 3 -> matricula.setNota3(dto.getNota());
            case 4 -> matricula.setNotaFinalExame(dto.getNota());
            default -> throw new IllegalArgumentException("Número de avaliação inválido (1 a 4).");
        }

        matricula.calcularMediaESituacao();
        matriculaTurmaRepository.save(matricula);
    }

    @Transactional
    public boolean fecharDiario(Long diarioId) {
        DiarioDeClasse diario = diarioRepository.findById(diarioId)
                .orElseThrow(() -> new IllegalArgumentException("Diário de classe não encontrado."));

        boolean fechou = diario.fechar();
        if (fechou) {
            diarioRepository.save(diario);
        }
        return fechou;
    }
}
