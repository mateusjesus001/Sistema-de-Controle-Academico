package br.uepb.sca.models;

import br.uepb.sca.models.enums.StatusFila;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "filas_espera")
public class FilaEspera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "posicao_fila", nullable = false)
    private int posicaoFila;

    @Column(name = "data_entrada", nullable = false)
    private LocalDateTime dataEntrada;

    @Column(name = "prazo_confirmacao")
    private LocalDateTime prazoConfirmacao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private StatusFila status = StatusFila.AGUARDANDO;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aluno_id", nullable = false)
    private Aluno aluno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "turma_id", nullable = false)
    private Turma turma;

    public FilaEspera() {
        this.dataEntrada = LocalDateTime.now();
        this.status = StatusFila.AGUARDANDO;
    }

    public FilaEspera(int posicaoFila, Aluno aluno, Turma turma) {
        this.posicaoFila = posicaoFila;
        this.aluno = aluno;
        this.turma = turma;
        this.dataEntrada = LocalDateTime.now();
        this.status = StatusFila.AGUARDANDO;
    }

    public void convocar() {
        this.status = StatusFila.CONVOCADO;
        this.prazoConfirmacao = LocalDateTime.now().plusHours(24);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getPosicaoFila() { return posicaoFila; }
    public void setPosicaoFila(int posicaoFila) { this.posicaoFila = posicaoFila; }

    public LocalDateTime getDataEntrada() { return dataEntrada; }
    public void setDataEntrada(LocalDateTime dataEntrada) { this.dataEntrada = dataEntrada; }

    public LocalDateTime getPrazoConfirmacao() { return prazoConfirmacao; }
    public void setPrazoConfirmacao(LocalDateTime prazoConfirmacao) { this.prazoConfirmacao = prazoConfirmacao; }

    public StatusFila getStatus() { return status; }
    public void setStatus(StatusFila status) { this.status = status; }

    public Aluno getAluno() { return aluno; }
    public void setAluno(Aluno aluno) { this.aluno = aluno; }

    public Turma getTurma() { return turma; }
    public void setTurma(Turma turma) { this.turma = turma; }
}
