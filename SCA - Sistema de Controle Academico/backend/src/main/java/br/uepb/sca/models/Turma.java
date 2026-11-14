package br.uepb.sca.models;

import br.uepb.sca.models.enums.StatusTurma;
import br.uepb.sca.models.enums.Turno;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "turmas")
public class Turma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String codigo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Turno turno = Turno.MANHA;

    @Column(length = 50)
    private String sala = "Sala 101 - Bloco A";

    @Column(name = "vagas_totais", nullable = false)
    private int vagasTotais = 40;

    @Column(name = "vagas_disponiveis", nullable = false)
    private int vagasDisponiveis = 40;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusTurma status = StatusTurma.ABERTA;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "disciplina_id", nullable = false)
    private Disciplina disciplina;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "professor_id", nullable = false)
    private Professor professor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "periodo_letivo_id", nullable = false)
    private PeriodoLetivo periodoLetivo;

    @OneToMany(mappedBy = "turma", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<HorarioTurma> horarios = new ArrayList<>();

    @OneToOne(mappedBy = "turma", cascade = CascadeType.ALL, orphanRemoval = true)
    private DiarioDeClasse diario;

    public Turma() {}

    public Turma(String codigo, Turno turno, String sala, int vagasTotais, Disciplina disciplina, Professor professor, PeriodoLetivo periodoLetivo) {
        this.codigo = codigo;
        this.turno = turno;
        this.sala = sala;
        this.vagasTotais = vagasTotais;
        this.vagasDisponiveis = vagasTotais;
        this.disciplina = disciplina;
        this.professor = professor;
        this.periodoLetivo = periodoLetivo;
        this.status = StatusTurma.ABERTA;
    }

    /**
     * Reserva atômica de vaga (RN09 / UC11).
     * Disparado dentro de transação com Lock Pessimista.
     */
    public synchronized boolean reservarVaga() {
        if (this.vagasDisponiveis > 0) {
            this.vagasDisponiveis--;
            if (this.vagasDisponiveis == 0) {
                this.status = StatusTurma.LOTADA;
            }
            return true;
        }
        return false;
    }

    public synchronized void liberarVaga() {
        if (this.vagasDisponiveis < this.vagasTotais) {
            this.vagasDisponiveis++;
            if (this.status == StatusTurma.LOTADA) {
                this.status = StatusTurma.ABERTA;
            }
        }
    }

    public boolean temChoqueHorarioCom(Turma outra) {
        if (outra == null || outra.getHorarios() == null || this.horarios == null) {
            return false;
        }
        for (HorarioTurma h1 : this.horarios) {
            for (HorarioTurma h2 : outra.getHorarios()) {
                if (h1.haSobreposicao(h2)) {
                    return true;
                }
            }
        }
        return false;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public Turno getTurno() { return turno; }
    public void setTurno(Turno turno) { this.turno = turno; }

    public String getSala() { return sala; }
    public void setSala(String sala) { this.sala = sala; }

    public int getVagasTotais() { return vagasTotais; }
    public void setVagasTotais(int vagasTotais) { this.vagasTotais = vagasTotais; }

    public int getVagasDisponiveis() { return vagasDisponiveis; }
    public void setVagasDisponiveis(int vagasDisponiveis) { this.vagasDisponiveis = vagasDisponiveis; }

    public StatusTurma getStatus() { return status; }
    public void setStatus(StatusTurma status) { this.status = status; }

    public Disciplina getDisciplina() { return disciplina; }
    public void setDisciplina(Disciplina disciplina) { this.disciplina = disciplina; }

    public Professor getProfessor() { return professor; }
    public void setProfessor(Professor professor) { this.professor = professor; }

    public PeriodoLetivo getPeriodoLetivo() { return periodoLetivo; }
    public void setPeriodoLetivo(PeriodoLetivo periodoLetivo) { this.periodoLetivo = periodoLetivo; }

    public List<HorarioTurma> getHorarios() { return horarios; }
    public void setHorarios(List<HorarioTurma> horarios) { this.horarios = horarios; }

    public DiarioDeClasse getDiario() { return diario; }
    public void setDiario(DiarioDeClasse diario) { this.diario = diario; }
}
