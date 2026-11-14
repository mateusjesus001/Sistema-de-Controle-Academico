package br.uepb.sca.controllers;

import br.uepb.sca.dtos.MatriculaRequestDTO;
import br.uepb.sca.dtos.MatriculaResponseDTO;
import br.uepb.sca.models.MatriculaTurma;
import br.uepb.sca.repositories.MatriculaTurmaRepository;
import br.uepb.sca.services.MatriculaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matriculas")
public class MatriculaController {

    private final MatriculaService matriculaService;
    private final MatriculaTurmaRepository matriculaTurmaRepository;

    public MatriculaController(MatriculaService matriculaService, MatriculaTurmaRepository matriculaTurmaRepository) {
        this.matriculaService = matriculaService;
        this.matriculaTurmaRepository = matriculaTurmaRepository;
    }

    /**
     * UC09, UC11: Solicitação atômica de matrícula com concorrência e fila de espera.
     */
    @PostMapping
    public ResponseEntity<MatriculaResponseDTO> solicitarMatricula(@RequestBody MatriculaRequestDTO request) {
        MatriculaResponseDTO response = matriculaService.solicitarMatricula(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/aluno/{alunoId}")
    public ResponseEntity<List<MatriculaTurma>> listarPorAluno(@PathVariable Long alunoId) {
        return ResponseEntity.ok(matriculaTurmaRepository.findByAlunoId(alunoId));
    }
}
