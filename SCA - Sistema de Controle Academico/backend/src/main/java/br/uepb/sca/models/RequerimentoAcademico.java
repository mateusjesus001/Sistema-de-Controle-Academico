package br.uepb.sca.models;

import br.uepb.sca.models.enums.StatusRequerimento;
import br.uepb.sca.models.enums.TipoRequerimento;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "requerimentos_academicos")
public class RequerimentoAcademico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 30)
    private String protocolo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private TipoRequerimento tipo;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String justificativa;

    @Column(name = "documento_anexo_url", length = 255)
    private String documentoAnexoUrl;

    @Column(name = "data_abertura", nullable = false)
    private LocalDateTime dataAbertura;

    @Column(name = "prazo_resposta")
    private LocalDate prazoResposta;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private StatusRequerimento status = StatusRequerimento.ABERTO;

    @Column(columnDefinition = "TEXT")
    private String parecer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aluno_id", nullable = false)
    private Aluno aluno;

    public RequerimentoAcademico() {
        this.dataAbertura = LocalDateTime.now();
        this.status = StatusRequerimento.ABERTO;
        this.prazoResposta = LocalDate.now().plusDays(10);
    }

    public RequerimentoAcademico(String protocolo, TipoRequerimento tipo, String justificativa, String documentoAnexoUrl, Aluno aluno) {
        this.protocolo = protocolo;
        this.tipo = tipo;
        this.justificativa = justificativa;
        this.documentoAnexoUrl = documentoAnexoUrl;
        this.aluno = aluno;
        this.dataAbertura = LocalDateTime.now();
        this.prazoResposta = LocalDate.now().plusDays(10);
        this.status = StatusRequerimento.ABERTO;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getProtocolo() { return protocolo; }
    public void setProtocolo(String protocolo) { this.protocolo = protocolo; }

    public TipoRequerimento getTipo() { return tipo; }
    public void setTipo(TipoRequerimento tipo) { this.tipo = tipo; }

    public String getJustificativa() { return justificativa; }
    public void setJustificativa(String justificativa) { this.justificativa = justificativa; }

    public String getDocumentoAnexoUrl() { return documentoAnexoUrl; }
    public void setDocumentoAnexoUrl(String documentoAnexoUrl) { this.documentoAnexoUrl = documentoAnexoUrl; }

    public LocalDateTime getDataAbertura() { return dataAbertura; }
    public void setDataAbertura(LocalDateTime dataAbertura) { this.dataAbertura = dataAbertura; }

    public LocalDate getPrazoResposta() { return prazoResposta; }
    public void setPrazoResposta(LocalDate prazoResposta) { this.prazoResposta = prazoResposta; }

    public StatusRequerimento getStatus() { return status; }
    public void setStatus(StatusRequerimento status) { this.status = status; }

    public String getParecer() { return parecer; }
    public void setParecer(String parecer) { this.parecer = parecer; }

    public Aluno getAluno() { return aluno; }
    public void setAluno(Aluno aluno) { this.aluno = aluno; }
}
