package br.uepb.sca.services;

import br.uepb.sca.models.*;
import br.uepb.sca.models.enums.*;
import br.uepb.sca.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class DataInitializerService implements CommandLineRunner {

    private final CursoRepository cursoRepository;
    private final DisciplinaRepository disciplinaRepository;
    private final PeriodoLetivoRepository periodoLetivoRepository;
    private final TurmaRepository turmaRepository;
    private final DiarioDeClasseRepository diarioRepository;
    private final AlunoRepository alunoRepository;
    private final ProfessorRepository professorRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializerService(CursoRepository cursoRepository,
                                  DisciplinaRepository disciplinaRepository,
                                  PeriodoLetivoRepository periodoLetivoRepository,
                                  TurmaRepository turmaRepository,
                                  DiarioDeClasseRepository diarioRepository,
                                  AlunoRepository alunoRepository,
                                  ProfessorRepository professorRepository,
                                  UsuarioRepository usuarioRepository,
                                  PasswordEncoder passwordEncoder) {
        this.cursoRepository = cursoRepository;
        this.disciplinaRepository = disciplinaRepository;
        this.periodoLetivoRepository = periodoLetivoRepository;
        this.turmaRepository = turmaRepository;
        this.diarioRepository = diarioRepository;
        this.alunoRepository = alunoRepository;
        this.professorRepository = professorRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (usuarioRepository.count() > 0) {
            return; // Já inicializado
        }

        String senhaHash = passwordEncoder.encode("123456");

        // 1. Cursos
        Curso cc = new Curso("CC.01", "Ciência da Computação", "Departamento de Sistemas e Computação", Turno.INTEGRAL);
        Curso esw = new Curso("ES.02", "Engenharia de Software", "Departamento de Sistemas e Computação", Turno.MANHA);
        Curso si = new Curso("SI.03", "Sistemas de Informação", "Departamento de Estatística e Informática", Turno.NOITE);
        cursoRepository.saveAll(List.of(cc, esw, si));

        // 2. Disciplinas vinculadas aos cursos
        Disciplina ed = new Disciplina("COMP.142", "Estruturas de Dados", 60, TipoDisciplina.OBRIGATORIA, "Filas, Pilhas, Listas, Árvores e Tabelas Hash.", "DSC", cc);
        Disciplina bd2 = new Disciplina("COMP.151", "Banco de Dados II", 60, TipoDisciplina.OBRIGATORIA, "Transações ACID, Lock Pessimista, Normalização e Tuning.", "DSC", cc);
        Disciplina calcNum = new Disciplina("MAT.108", "Cálculo Numérico", 60, TipoDisciplina.OBRIGATORIA, "Interpolação, Zeros de Funções e Métodos Iterativos.", "DM", cc);
        Disciplina es = new Disciplina("COMP.163", "Engenharia de Software", 60, TipoDisciplina.OBRIGATORIA, "UML, Padrões de Projeto e Processos Ágeis.", "DSC", cc);
        Disciplina ia = new Disciplina("COMP.201", "Inteligência Artificial", 60, TipoDisciplina.OPTATIVA, "Busca Heurística, Redes Neurais e Agentes.", "DSC", cc);

        Disciplina arqSoft = new Disciplina("ESW.201", "Arquitetura de Software", 60, TipoDisciplina.OBRIGATORIA, "Microsserviços, Clean Arch e Event-Driven.", "DSC", esw);
        Disciplina gestaoTI = new Disciplina("SI.301", "Governança e Gestão de TI", 60, TipoDisciplina.OBRIGATORIA, "ITIL, COBIT e Gestão Ágil.", "DEI", si);

        disciplinaRepository.saveAll(List.of(ed, bd2, calcNum, es, ia, arqSoft, gestaoTI));

        // 3. Período Letivo
        LocalDate hoje = LocalDate.now();
        PeriodoLetivo p20251 = new PeriodoLetivo(
                2025, 1,
                hoje.minusMonths(1), hoje.plusMonths(4),
                hoje.minusDays(10), hoje.plusDays(20)
        );
        periodoLetivoRepository.save(p20251);

        // 4. Usuários
        // Professores
        Professor profLucas = new Professor("Prof. Dr. Lucas Nóbrega", "345.678.901-22", "lucas.nobrega@servidor.uepb.edu.br", senhaHash, "1092831", "Departamento de Sistemas e Computação");
        Professor profCaio = new Professor("Prof. Caio Freire", "789.012.345-66", "caio.freire@servidor.uepb.edu.br", senhaHash, "1092832", "Departamento de Sistemas e Computação");
        Professor profaLivia = new Professor("Profa. Lívia Porto", "890.123.456-77", "livia.porto@servidor.uepb.edu.br", senhaHash, "1092833", "Departamento de Matemática");
        professorRepository.saveAll(List.of(profLucas, profCaio, profaLivia));

        // Coordenadora Helena
        CoordenadorCurso coordHelena = new CoordenadorCurso("Profa. Dra. Helena Moura", "456.789.012-33", "helena.moura@servidor.uepb.edu.br", senhaHash, "Portaria 042/2023", cc);
        usuarioRepository.save(coordHelena);

        // Secretário Carlos
        SecretarioAcademico secCarlos = new SecretarioAcademico("Carlos Eduardo Andrade", "567.890.123-44", "carlos.andrade@servidor.uepb.edu.br", senhaHash, "SEC-0041");
        usuarioRepository.save(secCarlos);

        // Aluno Mateus
        Aluno alunoMateus = new Aluno("Mateus de Jesus Costa", "123.456.789-00", "mateus.costa@aluno.uepb.edu.br", senhaHash, "2023101001", cc);
        alunoMateus.setCoeficienteRendimento(8.45);
        alunoMateus.setPercentualIntegralizacao(68.0);
        alunoMateus.setHorasConcluidas(2176);
        alunoMateus.setHorasTotais(3200);
        alunoMateus.setSemestreAtual("5º período");
        alunoRepository.save(alunoMateus);

        // Monitor Thiago
        Monitor monThiago = new Monitor("Thiago Silva (Monitor)", "234.567.890-11", "thiago.silva@aluno.uepb.edu.br", senhaHash, "2022101045", cc, ed, TipoVinculoMonitoria.BOLSISTA);
        monThiago.setCoeficienteRendimento(8.90);
        monThiago.setPercentualIntegralizacao(82.0);
        monThiago.setHorasConcluidas(2624);
        alunoRepository.save(monThiago);

        // Admin Root
        AdministradorGeral admin = new AdministradorGeral("Administrador Geral", "000.111.222-33", "admin.sca@uepb.edu.br", senhaHash);
        usuarioRepository.save(admin);

        // 5. Turmas Ofertadas
        Turma t1 = new Turma("T01", Turno.MANHA, "Lab 01 - Bloco A", 40, ed, profLucas, p20251);
        t1.getHorarios().add(new HorarioTurma(DiaSemana.SEGUNDA, LocalTime.of(8, 0), LocalTime.of(10, 0), t1));
        t1.getHorarios().add(new HorarioTurma(DiaSemana.QUARTA, LocalTime.of(8, 0), LocalTime.of(10, 0), t1));
        turmaRepository.save(t1);

        DiarioDeClasse d1 = new DiarioDeClasse(t1);
        diarioRepository.save(d1);

        Turma t2 = new Turma("T01", Turno.MANHA, "Lab 02 - Bloco A", 35, bd2, profLucas, p20251);
        t2.getHorarios().add(new HorarioTurma(DiaSemana.TERCA, LocalTime.of(10, 0), LocalTime.of(12, 0), t2));
        turmaRepository.save(t2);

        DiarioDeClasse d2 = new DiarioDeClasse(t2);
        diarioRepository.save(d2);

        Turma t3 = new Turma("T01", Turno.TARDE, "Sala 204 - Bloco B", 45, calcNum, profLucas, p20251);
        t3.getHorarios().add(new HorarioTurma(DiaSemana.QUARTA, LocalTime.of(14, 0), LocalTime.of(16, 0), t3));
        turmaRepository.save(t3);

        DiarioDeClasse d3 = new DiarioDeClasse(t3);
        diarioRepository.save(d3);

        Turma t4 = new Turma("T01", Turno.MANHA, "Lab 03 - Bloco A", 30, es, profLucas, p20251);
        t4.getHorarios().add(new HorarioTurma(DiaSemana.QUINTA, LocalTime.of(8, 0), LocalTime.of(10, 0), t4));
        turmaRepository.save(t4);

        DiarioDeClasse d4 = new DiarioDeClasse(t4);
        diarioRepository.save(d4);
    }
}
