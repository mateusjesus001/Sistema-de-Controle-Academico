package br.uepb.sca.models;

import br.uepb.sca.models.enums.SituacaoAcademica;
import br.uepb.sca.models.enums.StatusMatricula;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "matriculas_turma")
public class MatriculaTurma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_solicitacao", nullable = false)
    private LocalDateTime dataSolicitacao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private StatusMatricula status = StatusMatricula.CONFIRMADA;

    private Double nota1;
    private Double nota2;
    private Double nota3;

    @Column(name = "nota_final_exame")
    private Double notaFinalExame;

    @Column(name = "media_final")
    private Double mediaFinal;

    @Column(name = "percentual_faltas", nullable = false)
    private double percentualFaltas = 0.0;

    @Enumerated(EnumType.STRING)
    @Column(name = "situacao_final", nullable = false, length = 30)
    private SituacaoAcademica situacaoFinal = SituacaoAcademica.CURSANDO;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aluno_id", nullable = false)
    private Aluno aluno;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "turma_id", nullable = false)
    private Turma turma;

    public MatriculaTurma() {
        this.dataSolicitacao = LocalDateTime.now();
        this.status = StatusMatricula.CONFIRMADA;
        this.situacaoFinal = SituacaoAcademica.CURSANDO;
    }

    public MatriculaTurma(Aluno aluno, Turma turma) {
        this.aluno = aluno;
        this.turma = turma;
        this.dataSolicitacao = LocalDateTime.now();
        this.status = StatusMatricula.CONFIRMADA;
        this.situacaoFinal = SituacaoAcademica.CURSANDO;
    }

    /**
     * Aplica RN13 e RN14 de cálculo de médias da UEPB.
     */
    public void calcularMediaESituacao() {
        if (this.percentualFaltas > 25.0) {
            this.situacaoFinal = SituacaoAcademica.REPROVADO_POR_FALTA;
            return;
        }

        if (nota1 != null && nota2 != null && nota3 != null) {
            double media = (nota1 + nota2 + nota3) / 3.0;
            this.mediaFinal = Math.round(media * 100.0) / 100.0;

            if (this.mediaFinal >= 7.0) {
                this.situacaoFinal = SituacaoAcademica.APROVADO;
            } else if (this.mediaFinal < 4.0) {
                this.situacaoFinal = SituacaoAcademica.REPROVADO_POR_NOTA;
            } else if (notaFinalExame != null) {
                double mediaFinalComExame = (this.mediaFinal + notaFinalExame) / 2.0;
                this.mediaFinal = Math.round(mediaFinalComExame * 100.0) / 100.0;
                if (this.mediaFinal >= 5.0) {
                    this.situacaoFinal = SituacaoAcademica.APROVADO;
                } else {
                    this.situacaoFinal = SituacaoAcademica.REPROVADO_POR_NOTA;
                }
            } else {
                this.situacaoFinal = SituacaoAcademica.CURSANDO;
            }
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getDataSolicitacao() { return dataSolicitacao; }
    public void setDataSolicitacao(LocalDateTime dataSolicitacao) { this.dataSolicitacao = dataSolicitacao; }

    public StatusMatricula getStatus() { return status; }
    public void setStatus(StatusMatricula status) { this.status = status; }

    public Double getNota1() { return nota1; }
    public void setNota1(Double nota1) { this.nota1 = nota1; }

    public Double getNota2() { return nota2; }
    public void setNota2(Double nota2) { this.nota2 = nota2; }

    public Double getNota3() { return nota3; }
    public void setNota3(Double nota3) { this.nota3 = nota3; }

    public Double getNotaFinalExame() { return notaFinalExame; }
    public void setNotaFinalExame(Double notaFinalExame) { this.notaFinalExame = notaFinalExame; }

    public Double getMediaFinal() { return mediaFinal; }
    public void setMediaFinal(Double mediaFinal) { this.mediaFinal = mediaFinal; }

    public double getPercentualFaltas() { return percentualFaltas; }
    public void setPercentualFaltas(double percentualFaltas) { this.percentualFaltas = percentualFaltas; }

    public SituacaoAcademica getSituacaoFinal() { return situacaoFinal; }
    public void setSituacaoFinal(SituacaoAcademica situacaoFinal) { this.situacaoFinal = situacaoFinal; }

    public Aluno getAluno() { return aluno; }
    public void setAluno(Aluno aluno) { this.aluno = aluno; }

    public Turma getTurma() { return turma; }
    public void setTurma(Turma turma) { this.turma = turma; }
}
