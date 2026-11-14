package br.uepb.sca.models;

import br.uepb.sca.models.enums.DiaSemana;
import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "horarios_turma")
public class HorarioTurma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "dia_semana", nullable = false, length = 20)
    private DiaSemana diaSemana;

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "hora_fim", nullable = false)
    private LocalTime horaFim;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "turma_id", nullable = false)
    private Turma turma;

    public HorarioTurma() {}

    public HorarioTurma(DiaSemana diaSemana, LocalTime horaInicio, LocalTime horaFim, Turma turma) {
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.turma = turma;
    }

    public boolean haSobreposicao(HorarioTurma outro) {
        if (outro == null || this.diaSemana != outro.getDiaSemana()) {
            return false;
        }
        return this.horaInicio.isBefore(outro.getHoraFim()) && outro.getHoraInicio().isBefore(this.horaFim);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public DiaSemana getDiaSemana() { return diaSemana; }
    public void setDiaSemana(DiaSemana diaSemana) { this.diaSemana = diaSemana; }

    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }

    public LocalTime getHoraFim() { return horaFim; }
    public void setHoraFim(LocalTime horaFim) { this.horaFim = horaFim; }

    public Turma getTurma() { return turma; }
    public void setTurma(Turma turma) { this.turma = turma; }
}
