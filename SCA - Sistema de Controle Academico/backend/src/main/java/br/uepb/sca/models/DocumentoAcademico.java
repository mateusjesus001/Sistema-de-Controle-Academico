package br.uepb.sca.models;

import br.uepb.sca.models.enums.TipoDocumento;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "documentos_academicos")
public class DocumentoAcademico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TipoDocumento tipo;

    @Column(name = "codigo_verificacao", nullable = false, unique = true, length = 64)
    private String codigoVerificacao;

    @Column(name = "data_emissao", nullable = false)
    private LocalDateTime dataEmissao;

    @Column(name = "assinado_digitalmente", nullable = false)
    private boolean assinadoDigitalmente = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aluno_id", nullable = false)
    private Aluno aluno;

    public DocumentoAcademico() {
        this.dataEmissao = LocalDateTime.now();
        this.assinadoDigitalmente = true;
    }

    public DocumentoAcademico(TipoDocumento tipo, String codigoVerificacao, Aluno aluno) {
        this.tipo = tipo;
        this.codigoVerificacao = codigoVerificacao;
        this.aluno = aluno;
        this.dataEmissao = LocalDateTime.now();
        this.assinadoDigitalmente = true;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public TipoDocumento getTipo() { return tipo; }
    public void setTipo(TipoDocumento tipo) { this.tipo = tipo; }

    public String getCodigoVerificacao() { return codigoVerificacao; }
    public void setCodigoVerificacao(String codigoVerificacao) { this.codigoVerificacao = codigoVerificacao; }

    public LocalDateTime getDataEmissao() { return dataEmissao; }
    public void setDataEmissao(LocalDateTime dataEmissao) { this.dataEmissao = dataEmissao; }

    public boolean isAssinadoDigitalmente() { return assinadoDigitalmente; }
    public void setAssinadoDigitalmente(boolean assinadoDigitalmente) { this.assinadoDigitalmente = assinadoDigitalmente; }

    public Aluno getAluno() { return aluno; }
    public void setAluno(Aluno aluno) { this.aluno = aluno; }
}
