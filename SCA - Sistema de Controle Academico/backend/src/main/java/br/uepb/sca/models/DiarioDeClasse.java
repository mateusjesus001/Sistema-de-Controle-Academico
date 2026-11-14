package br.uepb.sca.models;

import br.uepb.sca.models.enums.SituacaoDiario;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "diarios_classe")
public class DiarioDeClasse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private SituacaoDiario situacao = SituacaoDiario.ABERTO;

    @Column(name = "qtd_avaliacoes", nullable = false)
    private int qtdAvaliacoes = 3;

    @Column(name = "media_minima", nullable = false)
    private double mediaMinima = 7.0;

    @Column(name = "nota_minima_final", nullable = false)
    private double notaMinimaFinal = 5.0;

    @Column(name = "data_fechamento")
    private LocalDateTime dataFechamento;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "turma_id", nullable = false, unique = true)
    private Turma turma;

    @OneToMany(mappedBy = "diario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RegistroAula> aulas = new ArrayList<>();

    public DiarioDeClasse() {}

    public DiarioDeClasse(Turma turma) {
        this.turma = turma;
        this.situacao = SituacaoDiario.ABERTO;
        this.qtdAvaliacoes = 3;
        this.mediaMinima = 7.0;
        this.notaMinimaFinal = 5.0;
    }

    public boolean fechar() {
        if (this.situacao == SituacaoDiario.ABERTO || this.situacao == SituacaoDiario.REABERTO_TEMPORARIO) {
            this.situacao = SituacaoDiario.FECHADO;
            this.dataFechamento = LocalDateTime.now();
            return true;
        }
        return false;
    }

    public void reabrir() {
        this.situacao = SituacaoDiario.REABERTO_TEMPORARIO;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public SituacaoDiario getSituacao() { return situacao; }
    public void setSituacao(SituacaoDiario situacao) { this.situacao = situacao; }

    public int getQtdAvaliacoes() { return qtdAvaliacoes; }
    public void setQtdAvaliacoes(int qtdAvaliacoes) { this.qtdAvaliacoes = qtdAvaliacoes; }

    public double getMediaMinima() { return mediaMinima; }
    public void setMediaMinima(double mediaMinima) { this.mediaMinima = mediaMinima; }

    public double getNotaMinimaFinal() { return notaMinimaFinal; }
    public void setNotaMinimaFinal(double notaMinimaFinal) { this.notaMinimaFinal = notaMinimaFinal; }

    public LocalDateTime getDataFechamento() { return dataFechamento; }
    public void setDataFechamento(LocalDateTime dataFechamento) { this.dataFechamento = dataFechamento; }

    public Turma getTurma() { return turma; }
    public void setTurma(Turma turma) { this.turma = turma; }

    public List<RegistroAula> getAulas() { return aulas; }
    public void setAulas(List<RegistroAula> aulas) { this.aulas = aulas; }
}
