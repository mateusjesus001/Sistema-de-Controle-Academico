package br.uepb.sca.models;

import br.uepb.sca.models.enums.DiaSemana;
import jakarta.persistence.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "plantoes_duvida")
public class PlantaoDuvida {

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

    @Column(name = "local_ou_link", nullable = false, length = 150)
    private String localOuLink = "Laboratório de Informática 3 / Meet";

    @Column(name = "vagas_totais", nullable = false)
    private int vagasTotais = 5;

    @Column(name = "vagas_disponiveis", nullable = false)
    private int vagasDisponiveis = 5;

    @Column(name = "aviso_plantao", length = 255)
    private String avisoPlantao;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "monitor_id", nullable = false)
    private Monitor monitor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "disciplina_id", nullable = false)
    private Disciplina disciplina;

    @OneToMany(mappedBy = "plantao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AgendamentoPlantao> agendamentos = new ArrayList<>();

    public PlantaoDuvida() {}

    public PlantaoDuvida(DiaSemana diaSemana, LocalTime horaInicio, LocalTime horaFim, String localOuLink, int vagasTotais, Monitor monitor, Disciplina disciplina) {
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.localOuLink = localOuLink;
        this.vagasTotais = vagasTotais;
        this.vagasDisponiveis = vagasTotais;
        this.monitor = monitor;
        this.disciplina = disciplina;
    }

    public synchronized boolean reservarVaga() {
        if (this.vagasDisponiveis > 0) {
            this.vagasDisponiveis--;
            return true;
        }
        return false;
    }

    public synchronized void liberarVaga() {
        if (this.vagasDisponiveis < this.vagasTotais) {
            this.vagasDisponiveis++;
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public DiaSemana getDiaSemana() { return diaSemana; }
    public void setDiaSemana(DiaSemana diaSemana) { this.diaSemana = diaSemana; }

    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }

    public LocalTime getHoraFim() { return horaFim; }
    public void setHoraFim(LocalTime horaFim) { this.horaFim = horaFim; }

    public String getLocalOuLink() { return localOuLink; }
    public void setLocalOuLink(String localOuLink) { this.localOuLink = localOuLink; }

    public int getVagasTotais() { return vagasTotais; }
    public void setVagasTotais(int vagasTotais) { this.vagasTotais = vagasTotais; }

    public int getVagasDisponiveis() { return vagasDisponiveis; }
    public void setVagasDisponiveis(int vagasDisponiveis) { this.vagasDisponiveis = vagasDisponiveis; }

    public String getAvisoPlantao() { return avisoPlantao; }
    public void setAvisoPlantao(String avisoPlantao) { this.avisoPlantao = avisoPlantao; }

    public Monitor getMonitor() { return monitor; }
    public void setMonitor(Monitor monitor) { this.monitor = monitor; }

    public Disciplina getDisciplina() { return disciplina; }
    public void setDisciplina(Disciplina disciplina) { this.disciplina = disciplina; }

    public List<AgendamentoPlantao> getAgendamentos() { return agendamentos; }
    public void setAgendamentos(List<AgendamentoPlantao> agendamentos) { this.agendamentos = agendamentos; }
}
