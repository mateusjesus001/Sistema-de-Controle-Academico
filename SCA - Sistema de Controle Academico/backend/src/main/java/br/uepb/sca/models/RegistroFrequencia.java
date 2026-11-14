package br.uepb.sca.models;

import jakarta.persistence.*;

@Entity
@Table(name = "registros_frequencia")
public class RegistroFrequencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private boolean presenca = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aula_id", nullable = false)
    private RegistroAula aula;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matricula_turma_id", nullable = false)
    private MatriculaTurma matriculaTurma;

    public RegistroFrequencia() {}

    public RegistroFrequencia(boolean presenca, RegistroAula aula, MatriculaTurma matriculaTurma) {
        this.presenca = presenca;
        this.aula = aula;
        this.matriculaTurma = matriculaTurma;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public boolean isPresenca() { return presenca; }
    public void setPresenca(boolean presenca) { this.presenca = presenca; }

    public RegistroAula getAula() { return aula; }
    public void setAula(RegistroAula aula) { this.aula = aula; }

    public MatriculaTurma getMatriculaTurma() { return matriculaTurma; }
    public void setMatriculaTurma(MatriculaTurma matriculaTurma) { this.matriculaTurma = matriculaTurma; }
}
