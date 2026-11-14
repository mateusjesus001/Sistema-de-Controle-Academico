package br.uepb.sca.models;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "registros_aula")
public class RegistroAula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_aula", nullable = false)
    private LocalDate dataAula;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String conteudo;

    @Column(name = "numero_horas", nullable = false)
    private int numeroHoras = 2;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diario_id", nullable = false)
    private DiarioDeClasse diario;

    @OneToMany(mappedBy = "aula", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RegistroFrequencia> frequencias = new ArrayList<>();

    public RegistroAula() {}

    public RegistroAula(LocalDate dataAula, String conteudo, int numeroHoras, DiarioDeClasse diario) {
        this.dataAula = dataAula;
        this.conteudo = conteudo;
        this.numeroHoras = numeroHoras;
        this.diario = diario;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getDataAula() { return dataAula; }
    public void setDataAula(LocalDate dataAula) { this.dataAula = dataAula; }

    public String getConteudo() { return conteudo; }
    public void setConteudo(String conteudo) { this.conteudo = conteudo; }

    public int getNumeroHoras() { return numeroHoras; }
    public void setNumeroHoras(int numeroHoras) { this.numeroHoras = numeroHoras; }

    public DiarioDeClasse getDiario() { return diario; }
    public void setDiario(DiarioDeClasse diario) { this.diario = diario; }

    public List<RegistroFrequencia> getFrequencias() { return frequencias; }
    public void setFrequencias(List<RegistroFrequencia> frequencias) { this.frequencias = frequencias; }
}
