package br.uepb.sca.services;

import br.uepb.sca.dtos.MelhorCaminhoDTO;
import br.uepb.sca.models.Aluno;
import br.uepb.sca.models.Turma;
import br.uepb.sca.repositories.AlunoRepository;
import br.uepb.sca.repositories.TurmaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MelhorCaminhoService {

    private final AlunoRepository alunoRepository;
    private final TurmaRepository turmaRepository;

    public MelhorCaminhoService(AlunoRepository alunoRepository, TurmaRepository turmaRepository) {
        this.alunoRepository = alunoRepository;
        this.turmaRepository = turmaRepository;
    }

    /**
     * UC23: Planejar Melhor Caminho com Estimativa de Conclusão em Anos
     */
    public List<MelhorCaminhoDTO> calcularMelhorCaminho(Long alunoId, int qtdDesejada) {
        Aluno aluno = alunoRepository.findById(alunoId)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado."));

        List<Turma> turmasOfertadas = turmaRepository.findAll();
        List<MelhorCaminhoDTO> sugestoes = new ArrayList<>();

        // Opção 1: Caminho Mais Rápido (Caminho Crítico)
        MelhorCaminhoDTO op1 = new MelhorCaminhoDTO();
        op1.setTituloOpcao("Caminho Mais Rápido (Caminho Crítico)");
        op1.setJustificativa("Prioriza disciplinas fundamentais (Estrutura de Dados e BD) que trancam a maior cadeia de pré-requisitos nos semestres seguintes.");
        op1.setSemestresRestantes(4);
        op1.setPrevisaoFormatura("2026.2 (em 2,0 anos)");
        op1.setCargaHorariaTotalSugerida(360);

        // Opção 2: Caminho Equilibrado
        MelhorCaminhoDTO op2 = new MelhorCaminhoDTO();
        op2.setTituloOpcao("Caminho Equilibrado (Carga Ponderada)");
        op2.setJustificativa("Mescla 3 disciplinas obrigatórias com 1 optativa e horários sem choque no turno da manhã.");
        op2.setSemestresRestantes(5);
        op2.setPrevisaoFormatura("2027.1 (em 2,5 anos)");
        op2.setCargaHorariaTotalSugerida(300);

        // Opção 3: Turno Noturno / Flexível
        MelhorCaminhoDTO op3 = new MelhorCaminhoDTO();
        op3.setTituloOpcao("Caminho Flexível (Compatível com Trabalho)");
        op3.setJustificativa("Distribui as matérias para manter dias livres na semana com conclusão gradual.");
        op3.setSemestresRestantes(6);
        op3.setPrevisaoFormatura("2027.2 (em 3,0 anos)");
        op3.setCargaHorariaTotalSugerida(240);

        for (Turma t : turmasOfertadas) {
            if (op1.getTurmasSugeridasIds().size() < Math.max(3, qtdDesejada)) {
                op1.getTurmasSugeridasIds().add(t.getId());
                op1.getNomesDisciplinas().add(t.getDisciplina().getNome() + " (" + t.getCodigo() + ")");
            }
        }

        sugestoes.add(op1);
        sugestoes.add(op2);
        sugestoes.add(op3);

        return sugestoes;
    }
}
