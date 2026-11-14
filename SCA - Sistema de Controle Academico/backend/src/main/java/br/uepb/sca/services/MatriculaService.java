package br.uepb.sca.services;

import br.uepb.sca.dtos.MatriculaRequestDTO;
import br.uepb.sca.dtos.MatriculaResponseDTO;
import br.uepb.sca.models.*;
import br.uepb.sca.models.enums.StatusMatricula;
import br.uepb.sca.repositories.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class MatriculaService {

    private final AlunoRepository alunoRepository;
    private final TurmaRepository turmaRepository;
    private final MatriculaTurmaRepository matriculaTurmaRepository;
    private final FilaEsperaRepository filaEsperaRepository;

    public MatriculaService(AlunoRepository alunoRepository,
                            TurmaRepository turmaRepository,
                            MatriculaTurmaRepository matriculaTurmaRepository,
                            FilaEsperaRepository filaEsperaRepository) {
        this.alunoRepository = alunoRepository;
        this.turmaRepository = turmaRepository;
        this.matriculaTurmaRepository = matriculaTurmaRepository;
        this.filaEsperaRepository = filaEsperaRepository;
    }

    /**
     * UC09, UC10, UC11: Solicitar Matrícula em Turmas com Lock Pessimista
     * Garante que não haja race condition na disputa da última vaga disponível.
     */
    @Transactional
    public MatriculaResponseDTO solicitarMatricula(MatriculaRequestDTO request) {
        Aluno aluno = alunoRepository.findById(request.getAlunoId())
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado."));

        MatriculaResponseDTO response = new MatriculaResponseDTO();
        List<Turma> turmasAprovadas = new ArrayList<>();

        for (Long turmaId : request.getTurmasIds()) {
            // 1. Busca com Lock Pessimista (SELECT FOR UPDATE)
            Turma turma = turmaRepository.findByIdWithLock(turmaId)
                    .orElseThrow(() -> new IllegalArgumentException("Turma " + turmaId + " não encontrada."));

            // 2. Valida se o aluno já está matriculado
            if (matriculaTurmaRepository.findByAlunoIdAndTurmaId(aluno.getId(), turma.getId()).isPresent()) {
                response.getConflitosDetectados().add("Aluno já matriculado na turma: " + turma.getDisciplina().getNome());
                continue;
            }

            // 3. Valida choque de horário com as demais turmas aprovadas
            boolean temChoque = false;
            for (Turma outra : turmasAprovadas) {
                if (turma.temChoqueHorarioCom(outra)) {
                    temChoque = true;
                    response.getConflitosDetectados().add("Choque de horário entre " + turma.getDisciplina().getNome() + " e " + outra.getDisciplina().getNome());
                    break;
                }
            }

            if (temChoque) {
                continue;
            }

            // 4. Concorrência Crítica (RN09 / UC11): Tenta reservar vaga com método sincronizado
            boolean vagaReservada = turma.reservarVaga();

            if (vagaReservada) {
                // Sucesso: Persiste turma com vaga decrementada e cria a inscrição
                turmaRepository.save(turma);

                MatriculaTurma matricula = new MatriculaTurma(aluno, turma);
                matricula.setStatus(StatusMatricula.CONFIRMADA);
                matriculaTurmaRepository.save(matricula);

                turmasAprovadas.add(turma);
                response.getTurmasMatriculadas().add(turma.getDisciplina().getCodigo() + " - " + turma.getDisciplina().getNome());
            } else {
                // Sem vaga: Insere na lista de espera (FilaEspera / UC12)
                int novaPosicao = filaEsperaRepository.findByTurmaIdOrderByPosicaoFilaAsc(turma.getId()).size() + 1;
                FilaEspera fila = new FilaEspera(novaPosicao, aluno, turma);
                filaEsperaRepository.save(fila);

                response.getTurmasFilaEspera().add(turma.getDisciplina().getNome() + " (Posição #" + novaPosicao + " na Fila de Espera)");
            }
        }

        if (!response.getTurmasMatriculadas().isEmpty()) {
            response.setSucesso(true);
            response.setMensagem("Matrícula processada com sucesso!");
            response.setProtocoloComprovante("SCA-UEPB-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        } else {
            response.setSucesso(false);
            response.setMensagem("Nenhuma vaga pôde ser reservada. Verifique conflitos ou posições em fila de espera.");
        }

        return response;
    }
}
