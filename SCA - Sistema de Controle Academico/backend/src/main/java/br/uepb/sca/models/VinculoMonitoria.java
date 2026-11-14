package br.uepb.sca.models;

import br.uepb.sca.models.enums.TipoVinculoMonitoria;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "vinculos_monitoria")
public class VinculoMonitoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoVinculoMonitoria tipo = TipoVinculoMonitoria.BOLSISTA;

    @Column(nullable = false, length = 20)
    private String semestre = "2025.1";

    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio = LocalDate.now();

    @Column(name = "data_fim", nullable = false)
    private LocalDate dataFim = LocalDate.now().plusMonths(6);

    @Column(nullable = false)
    private boolean ativo = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monitor_id", nullable = false)
    private Monitor monitor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "disciplina_id", nullable = false)
    private Disciplina disciplina;

    public VinculoMonitoria() {}

    public VinculoMonitoria(Monitor monitor, Disciplina disciplina, TipoVinculoMonitoria tipo, String semestre) {
        this.monitor = monitor;
        this.disciplina = disciplina;
        this.tipo = tipo;
        this.semestre = semestre;
        this.dataInicio = LocalDate.now();
        this.dataFim = LocalDate.now().plusMonths(6);
        this.ativo = true;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public TipoVinculoMonitoria getTipo() { return tipo; }
    public void setTipo(TipoVinculoMonitoria tipo) { this.tipo = tipo; }

    public String getSemestre() { return semestre; }
    public void setSemestre(String semestre) { this.semestre = semestre; }

    public LocalDate getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDate dataInicio) { this.dataInicio = dataInicio; }

    public LocalDate getDataFim() { return dataFim; }
    public void setDataFim(LocalDate dataFim) { this.dataFim = dataFim; }

    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }

    public Monitor getMonitor() { return monitor; }
    public void setMonitor(Monitor monitor) { this.monitor = monitor; }

    public Disciplina getDisciplina() { return disciplina; }
    public void setDisciplina(Disciplina disciplina) { this.disciplina = disciplina; }
}
