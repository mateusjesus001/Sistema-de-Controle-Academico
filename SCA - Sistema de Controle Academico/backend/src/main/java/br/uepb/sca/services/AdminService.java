package br.uepb.sca.services;

import br.uepb.sca.dtos.AlocarProfessorDTO;
import br.uepb.sca.dtos.AlunoAdminDTO;
import br.uepb.sca.dtos.CursoDTO;
import br.uepb.sca.dtos.DisciplinaAdminDTO;
import br.uepb.sca.dtos.ProfessorAdminDTO;
import br.uepb.sca.models.*;
import br.uepb.sca.models.enums.PerfilUsuario;
import br.uepb.sca.models.enums.StatusMatricula;
import br.uepb.sca.models.enums.StatusTurma;
import br.uepb.sca.models.enums.Turno;
import br.uepb.sca.repositories.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {

    private final CursoRepository cursoRepository;
    private final DisciplinaRepository disciplinaRepository;
    private final TurmaRepository turmaRepository;
    private final DiarioDeClasseRepository diarioRepository;
    private final AlunoRepository alunoRepository;
    private final ProfessorRepository professorRepository;
    private final UsuarioRepository usuarioRepository;
    private final MatriculaTurmaRepository matriculaTurmaRepository;
    private final PeriodoLetivoRepository periodoLetivoRepository;
    private final LogAuditoriaRepository logAuditoriaRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminService(CursoRepository cursoRepository,
                        DisciplinaRepository disciplinaRepository,
                        TurmaRepository turmaRepository,
                        DiarioDeClasseRepository diarioRepository,
                        AlunoRepository alunoRepository,
                        ProfessorRepository professorRepository,
                        UsuarioRepository usuarioRepository,
                        MatriculaTurmaRepository matriculaTurmaRepository,
                        PeriodoLetivoRepository periodoLetivoRepository,
                        LogAuditoriaRepository logAuditoriaRepository,
                        PasswordEncoder passwordEncoder) {
        this.cursoRepository = cursoRepository;
        this.disciplinaRepository = disciplinaRepository;
        this.turmaRepository = turmaRepository;
        this.diarioRepository = diarioRepository;
        this.alunoRepository = alunoRepository;
        this.professorRepository = professorRepository;
        this.usuarioRepository = usuarioRepository;
        this.matriculaTurmaRepository = matriculaTurmaRepository;
        this.periodoLetivoRepository = periodoLetivoRepository;
        this.logAuditoriaRepository = logAuditoriaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ==========================================
    // 1. GESTÃO DE CURSOS
    // ==========================================
    public List<CursoDTO> listarCursos() {
        List<Curso> cursos = cursoRepository.findAll();
        List<CursoDTO> dtos = new ArrayList<>();

        for (Curso c : cursos) {
            CursoDTO dto = new CursoDTO();
            dto.setId(c.getId());
            dto.setCodigo(c.getCodigo());
            dto.setNome(c.getNome());
            dto.setDepartamento(c.getDepartamento());
            dto.setTurno(c.getTurno());
            dto.setQtdDisciplinas(disciplinaRepository.findByCursoId(c.getId()).size());
            dto.setQtdAlunos(alunoRepository.findByCursoId(c.getId()).size());
            dtos.add(dto);
        }
        return dtos;
    }

    @Transactional
    public CursoDTO criarCurso(CursoDTO dto) {
        Curso curso = new Curso(dto.getCodigo(), dto.getNome(), dto.getDepartamento(), dto.getTurno() != null ? dto.getTurno() : Turno.INTEGRAL);
        curso = cursoRepository.save(curso);
        dto.setId(curso.getId());
        return dto;
    }

    @Transactional
    public void removerCurso(Long cursoId) {
        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado."));

        // 1. Desvincula alunos vinculados ao curso para não violar Foreign Key
        List<Aluno> alunosDoCurso = alunoRepository.findByCursoId(cursoId);
        for (Aluno a : alunosDoCurso) {
            a.setCurso(null);
            alunoRepository.save(a);
        }

        // 2. Remove disciplinas associadas ao curso com suas turmas
        List<Disciplina> disciplinasDoCurso = disciplinaRepository.findByCursoId(cursoId);
        for (Disciplina d : disciplinasDoCurso) {
            removerDisciplina(d.getId());
        }

        cursoRepository.delete(curso);
    }

    // ==========================================
    // 2. GESTÃO DE DISCIPLINAS
    // ==========================================
    public List<DisciplinaAdminDTO> listarDisciplinas(Long cursoId) {
        List<Disciplina> disciplinas = cursoId != null ?
                disciplinaRepository.findByCursoId(cursoId) :
                disciplinaRepository.findAll();

        List<DisciplinaAdminDTO> dtos = new ArrayList<>();
        for (Disciplina d : disciplinas) {
            DisciplinaAdminDTO dto = new DisciplinaAdminDTO();
            dto.setId(d.getId());
            dto.setCodigo(d.getCodigo());
            dto.setNome(d.getNome());
            dto.setCargaHoraria(d.getCargaHoraria());
            dto.setTipo(d.getTipo());
            dto.setEmenta(d.getEmenta());
            dto.setDepartamento(d.getDepartamento());

            if (d.getCurso() != null) {
                dto.setCursoId(d.getCurso().getId());
                dto.setNomeCurso(d.getCurso().getNome());
            }

            // Cada disciplina em oferta tem sua turma com um único professor responsável e lista de alunos
            List<Turma> turmas = turmaRepository.findByDisciplinaId(d.getId());
            if (!turmas.isEmpty()) {
                Turma t = turmas.get(0);
                dto.setTurmaId(t.getId());
                if (t.getProfessor() != null) {
                    dto.setProfessorId(t.getProfessor().getId());
                    dto.setProfessorNome(t.getProfessor().getNome());
                }
                dto.setQtdAlunosMatriculados(matriculaTurmaRepository.findByTurmaId(t.getId()).size());
            }

            dtos.add(dto);
        }
        return dtos;
    }

    @Transactional
    public DisciplinaAdminDTO criarDisciplina(DisciplinaAdminDTO dto) {
        Curso curso = null;
        if (dto.getCursoId() != null) {
            curso = cursoRepository.findById(dto.getCursoId())
                    .orElseThrow(() -> new IllegalArgumentException("Curso informado não encontrado."));
        }

        Disciplina d = new Disciplina(
                dto.getCodigo(),
                dto.getNome(),
                dto.getCargaHoraria() > 0 ? dto.getCargaHoraria() : 60,
                dto.getTipo(),
                dto.getEmenta(),
                dto.getDepartamento() != null ? dto.getDepartamento() : (curso != null ? curso.getDepartamento() : "DSC"),
                curso
        );
        d = disciplinaRepository.save(d);
        dto.setId(d.getId());

        // Se professor foi indicado, já cria a turma com esse professor único
        if (dto.getProfessorId() != null) {
            Professor prof = professorRepository.findById(dto.getProfessorId()).orElse(null);
            PeriodoLetivo periodo = periodoLetivoRepository.findByAtivoTrue().orElse(null);

            if (prof != null && periodo != null) {
                Turma turma = new Turma("T01", curso != null ? curso.getTurno() : Turno.MANHA, "Sala Padrão", 40, d, prof, periodo);
                turma = turmaRepository.save(turma);

                DiarioDeClasse diario = new DiarioDeClasse(turma);
                diarioRepository.save(diario);

                dto.setTurmaId(turma.getId());
                dto.setProfessorNome(prof.getNome());
            }
        }

        return dto;
    }

    @Transactional
    public void removerDisciplina(Long disciplinaId) {
        Disciplina disciplina = disciplinaRepository.findById(disciplinaId)
                .orElseThrow(() -> new IllegalArgumentException("Disciplina não encontrada."));

        // Remove turmas, matrículas e diários dependentes da disciplina
        List<Turma> turmas = turmaRepository.findByDisciplinaId(disciplinaId);
        for (Turma t : turmas) {
            List<MatriculaTurma> matriculas = matriculaTurmaRepository.findByTurmaId(t.getId());
            matriculaTurmaRepository.deleteAll(matriculas);

            diarioRepository.findByTurmaId(t.getId()).ifPresent(diarioRepository::delete);
            turmaRepository.delete(t);
        }

        disciplinaRepository.delete(disciplina);
    }

    // ==========================================
    // 3. GESTÃO DE ALUNOS
    // ==========================================
    public List<AlunoAdminDTO> listarAlunos(Long cursoId) {
        List<Aluno> alunos = cursoId != null ?
                alunoRepository.findByCursoId(cursoId) :
                alunoRepository.findAll();

        List<AlunoAdminDTO> dtos = new ArrayList<>();
        for (Aluno a : alunos) {
            AlunoAdminDTO dto = new AlunoAdminDTO();
            dto.setId(a.getId());
            dto.setNome(a.getNome());
            dto.setCpf(a.getCpf());
            dto.setEmail(a.getEmail());
            dto.setMatricula(a.getMatricula());
            dto.setSemestreAtual(a.getSemestreAtual());
            dto.setCoeficienteRendimento(a.getCoeficienteRendimento());
            dto.setPercentualIntegralizacao(a.getPercentualIntegralizacao());
            if (a.getCurso() != null) {
                dto.setCursoId(a.getCurso().getId());
                dto.setNomeCurso(a.getCurso().getNome());
            }
            dtos.add(dto);
        }
        return dtos;
    }

    @Transactional
    public AlunoAdminDTO criarAluno(AlunoAdminDTO dto) {
        Curso curso = null;
        if (dto.getCursoId() != null) {
            curso = cursoRepository.findById(dto.getCursoId())
                    .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado."));
        }

        String senha = dto.getSenha() != null && !dto.getSenha().isBlank() ? dto.getSenha() : "123456";
        String hash = passwordEncoder.encode(senha);

        Aluno aluno = new Aluno(dto.getNome(), dto.getCpf(), dto.getEmail(), hash, dto.getMatricula(), curso);
        if (dto.getSemestreAtual() != null) aluno.setSemestreAtual(dto.getSemestreAtual());
        aluno = alunoRepository.save(aluno);

        dto.setId(aluno.getId());
        return dto;
    }

    @Transactional
    public void removerAluno(Long alunoId) {
        Aluno aluno = alunoRepository.findById(alunoId)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado."));

        // Remove matrículas e devolve a vaga na turma
        List<MatriculaTurma> matriculas = matriculaTurmaRepository.findByAlunoId(alunoId);
        for (MatriculaTurma m : matriculas) {
            Turma t = m.getTurma();
            if (t != null) {
                t.liberarVaga();
                turmaRepository.save(t);
            }
        }
        matriculaTurmaRepository.deleteAll(matriculas);

        alunoRepository.delete(aluno);
    }

    // Matricular / Desmatricular Aluno de uma Turma
    @Transactional
    public void matricularAlunoNaTurma(Long turmaId, Long alunoId) {
        Turma turma = turmaRepository.findById(turmaId)
                .orElseThrow(() -> new IllegalArgumentException("Turma não encontrada."));
        Aluno aluno = alunoRepository.findById(alunoId)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado."));

        if (matriculaTurmaRepository.findByAlunoIdAndTurmaId(alunoId, turmaId).isPresent()) {
            throw new IllegalArgumentException("Aluno já matriculado nesta turma.");
        }

        if (turma.getVagasDisponiveis() <= 0) {
            throw new IllegalArgumentException("Turma sem vagas disponíveis.");
        }

        turma.reservarVaga();
        turmaRepository.save(turma);

        MatriculaTurma matricula = new MatriculaTurma(aluno, turma);
        matricula.setStatus(StatusMatricula.CONFIRMADA);
        matriculaTurmaRepository.save(matricula);
    }

    @Transactional
    public void removerAlunoDaTurma(Long turmaId, Long alunoId) {
        MatriculaTurma matricula = matriculaTurmaRepository.findByAlunoIdAndTurmaId(alunoId, turmaId)
                .orElseThrow(() -> new IllegalArgumentException("Matrícula não encontrada."));

        Turma turma = matricula.getTurma();
        turma.liberarVaga();
        turmaRepository.save(turma);

        matriculaTurmaRepository.delete(matricula);
    }

    // ==========================================
    // 4. ALOCAÇÃO E SUBSTITUIÇÃO DE PROFESSORES (REQUISITO EXPLÍCITO)
    // ==========================================
    @Transactional
    public void alocarOuSubstituirProfessor(AlocarProfessorDTO dto) {
        Turma turma = turmaRepository.findById(dto.getTurmaId())
                .orElseThrow(() -> new IllegalArgumentException("Turma não encontrada."));

        Professor novoProf = professorRepository.findById(dto.getProfessorId())
                .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado."));

        PeriodoLetivo periodo = turma.getPeriodoLetivo();
        boolean emInicioPeriodo = periodo != null && (periodo.estaEmJanelaMatricula() || periodo.estaEmJanelaAjuste());

        // Regra de Negócio:
        // "permitir adição de professores apenas em inicio de periodo e substituição de professores caso ele não possa mais ministrar a disciplina por algum motivo"
        if (!dto.isSubstituicao()) {
            // Tentativa de alocação inicial comum
            if (!emInicioPeriodo && turma.getProfessor() != null) {
                throw new IllegalStateException("O período letivo já está em andamento com docente ativo. A alteração só é permitida através do fluxo formal de SUBSTITUIÇÃO com justificativa.");
            }
            turma.setProfessor(novoProf);
            turmaRepository.save(turma);
        } else {
            // Fluxo de Substituição de Professor durante o período
            if (dto.getMotivoSubstituicao() == null || dto.getMotivoSubstituicao().trim().length() < 10) {
                throw new IllegalArgumentException("A substituição de professor exige uma justificativa formal com no mínimo 10 caracteres.");
            }

            Professor profAntigo = turma.getProfessor();
            String profAntigoNome = profAntigo != null ? profAntigo.getNome() : "Sem docente anterior";

            // Registra Auditoria (UC02 Fluxo A2 / UC03)
            LogAuditoria log = new LogAuditoria(
                    "SUBSTITUICAO_DOCENTE",
                    "Turma",
                    turma.getId(),
                    "{\"professorAnterior\": \"" + profAntigoNome + "\"}",
                    "{\"novoProfessor\": \"" + novoProf.getNome() + "\", \"justificativa\": \"" + dto.getMotivoSubstituicao() + "\"}",
                    novoProf
            );
            logAuditoriaRepository.save(log);

            // Aplica a substituição
            turma.setProfessor(novoProf);
            turmaRepository.save(turma);
        }
    }

    public List<Professor> listarProfessores() {
        return professorRepository.findAll();
    }

    // ==========================================
    // 5. CADASTRO E REMOÇÃO DE PROFESSORES (ADMIN GERAL)
    // ==========================================
    @Transactional
    public ProfessorAdminDTO criarProfessor(ProfessorAdminDTO dto) {
        if (dto.getSiape() == null || dto.getSiape().isBlank()) {
            throw new IllegalArgumentException("O SIAPE é obrigatório.");
        }
        if (professorRepository.findBySiape(dto.getSiape()).isPresent()) {
            throw new IllegalArgumentException("Já existe um professor cadastrado com este SIAPE.");
        }
        if (dto.getEmail() != null && usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Já existe um usuário cadastrado com este e-mail.");
        }
        if (dto.getCpf() != null && usuarioRepository.findByCpf(dto.getCpf()).isPresent()) {
            throw new IllegalArgumentException("Já existe um usuário cadastrado com este CPF.");
        }

        String senha = dto.getSenha() != null && !dto.getSenha().isBlank()
                ? dto.getSenha()
                : "123456";

        Professor prof = new Professor(
                dto.getNome(),
                dto.getCpf(),
                dto.getEmail(),
                passwordEncoder.encode(senha),
                dto.getSiape(),
                dto.getDepartamento()
        );
        prof = professorRepository.save(prof);

        dto.setId(prof.getId());
        return dto;
    }

    @Transactional
    public void removerProfessor(Long professorId) {
        Professor prof = professorRepository.findById(professorId)
                .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado."));

        // Verifica se o professor está alocado em alguma turma ativa
        List<Turma> turmasAlocadas = turmaRepository.findByProfessorId(professorId);
        if (!turmasAlocadas.isEmpty()) {
            throw new IllegalStateException(
                    "Não é possível remover o professor pois ele está alocado em "
                            + turmasAlocadas.size() + " turma(s). Remova a alocação primeiro."
            );
        }

        professorRepository.delete(prof);
    }
}
