package br.uepb.sca.controllers;

import br.uepb.sca.dtos.AlocarProfessorDTO;
import br.uepb.sca.dtos.AlunoAdminDTO;
import br.uepb.sca.dtos.CursoDTO;
import br.uepb.sca.dtos.DisciplinaAdminDTO;
import br.uepb.sca.dtos.ProfessorAdminDTO;
import br.uepb.sca.models.Professor;
import br.uepb.sca.services.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // ================= CURSOS =================
    @GetMapping("/cursos")
    public ResponseEntity<List<CursoDTO>> listarCursos() {
        return ResponseEntity.ok(adminService.listarCursos());
    }

    @PostMapping("/cursos")
    public ResponseEntity<CursoDTO> criarCurso(@RequestBody CursoDTO dto) {
        return ResponseEntity.ok(adminService.criarCurso(dto));
    }

    @DeleteMapping("/cursos/{id}")
    public ResponseEntity<Void> removerCurso(@PathVariable Long id) {
        adminService.removerCurso(id);
        return ResponseEntity.noContent().build();
    }

    // ================= DISCIPLINAS =================
    @GetMapping("/disciplinas")
    public ResponseEntity<List<DisciplinaAdminDTO>> listarDisciplinas(@RequestParam(required = false) Long cursoId) {
        return ResponseEntity.ok(adminService.listarDisciplinas(cursoId));
    }

    @PostMapping("/disciplinas")
    public ResponseEntity<DisciplinaAdminDTO> criarDisciplina(@RequestBody DisciplinaAdminDTO dto) {
        return ResponseEntity.ok(adminService.criarDisciplina(dto));
    }

    @DeleteMapping("/disciplinas/{id}")
    public ResponseEntity<Void> removerDisciplina(@PathVariable Long id) {
        adminService.removerDisciplina(id);
        return ResponseEntity.noContent().build();
    }

    // ================= ALUNOS =================
    @GetMapping("/alunos")
    public ResponseEntity<List<AlunoAdminDTO>> listarAlunos(@RequestParam(required = false) Long cursoId) {
        return ResponseEntity.ok(adminService.listarAlunos(cursoId));
    }

    @PostMapping("/alunos")
    public ResponseEntity<AlunoAdminDTO> criarAluno(@RequestBody AlunoAdminDTO dto) {
        return ResponseEntity.ok(adminService.criarAluno(dto));
    }

    @DeleteMapping("/alunos/{id}")
    public ResponseEntity<Void> removerAluno(@PathVariable Long id) {
        adminService.removerAluno(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/turmas/{turmaId}/matricular/{alunoId}")
    public ResponseEntity<String> matricularAlunoNaTurma(@PathVariable Long turmaId, @PathVariable Long alunoId) {
        adminService.matricularAlunoNaTurma(turmaId, alunoId);
        return ResponseEntity.ok("Aluno matriculado com sucesso na turma.");
    }

    @DeleteMapping("/turmas/{turmaId}/remover-aluno/{alunoId}")
    public ResponseEntity<String> removerAlunoDaTurma(@PathVariable Long turmaId, @PathVariable Long alunoId) {
        adminService.removerAlunoDaTurma(turmaId, alunoId);
        return ResponseEntity.ok("Aluno desmatriculado da turma.");
    }

    // ================= PROFESSORES & ALOCAÇÃO / SUBSTITUIÇÃO =================
    @GetMapping("/professores")
    public ResponseEntity<List<Professor>> listarProfessores() {
        return ResponseEntity.ok(adminService.listarProfessores());
    }

    @PostMapping("/professores")
    public ResponseEntity<ProfessorAdminDTO> criarProfessor(@RequestBody ProfessorAdminDTO dto) {
        return ResponseEntity.ok(adminService.criarProfessor(dto));
    }

    @DeleteMapping("/professores/{id}")
    public ResponseEntity<Void> removerProfessor(@PathVariable Long id) {
        adminService.removerProfessor(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/turmas/alocar-professor")
    public ResponseEntity<String> alocarOuSubstituirProfessor(@RequestBody AlocarProfessorDTO dto) {
        try {
            adminService.alocarOuSubstituirProfessor(dto);
            String msg = dto.isSubstituicao() ?
                    "Professor substituído com sucesso com registro em auditoria!" :
                    "Professor alocado com sucesso para a turma de início de período!";
            return ResponseEntity.ok(msg);
        } catch (IllegalStateException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
