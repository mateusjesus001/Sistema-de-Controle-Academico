package br.uepb.sca.models;

import br.uepb.sca.models.enums.StatusAgendamento;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "agendamentos_plantao")
public class AgendamentoPlantao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String assunto;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private StatusAgendamento status = StatusAgendamento.SOLICITADO;

    @Column(name = "data_agendada", nullable = false)
    private LocalDate dataAgendada;

    @Column(nullable = false)
    private boolean compareceu = false;

    @Column(name = "motivo_recusa", length = 255)
    private String motivoRecusa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aluno_id", nullable = false)
    private Aluno aluno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plantao_id", nullable = false)
    private PlantaoDuvida plantao;

    public AgendamentoPlantao() {}

    public AgendamentoPlantao(String assunto, LocalDate dataAgendada, Aluno aluno, PlantaoDuvida plantao) {
        this.assunto = assunto;
        this.dataAgendada = dataAgendada;
        this.aluno = aluno;
        this.plantao = plantao;
        this.status = StatusAgendamento.SOLICITADO;
        this.compareceu = false;
    }

    public void confirmar() {
        this.status = StatusAgendamento.CONFIRMADO;
    }

    public void recusar(String motivo) {
        this.status = StatusAgendamento.RECUSADO;
        this.motivoRecusa = motivo;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getAssunto() { return assunto; }
    public void setAssunto(String assunto) { this.assunto = assunto; }

    public StatusAgendamento getStatus() { return status; }
    public void setStatus(StatusAgendamento status) { this.status = status; }

    public LocalDate getDataAgendada() { return dataAgendada; }
    public void setDataAgendada(LocalDate dataAgendada) { this.dataAgendada = dataAgendada; }

    public boolean isCompareceu() { return compareceu; }
    public void setCompareceu(boolean compareceu) { this.compareceu = compareceu; }

    public String getMotivoRecusa() { return motivoRecusa; }
    public void setMotivoRecusa(String motivoRecusa) { this.motivoRecusa = motivoRecusa; }

    public Aluno getAluno() { return aluno; }
    public void setAluno(Aluno aluno) { this.aluno = aluno; }

    public PlantaoDuvida getPlantao() { return plantao; }
    public void setPlantao(PlantaoDuvida plantao) { this.plantao = plantao; }
}
