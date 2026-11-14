package br.uepb.sca.models;

import br.uepb.sca.models.enums.StatusModera;
import br.uepb.sca.models.enums.TipoMaterial;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "materiais_acervo")
public class MaterialAcervo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TipoMaterial tipo = TipoMaterial.SLIDE;

    @Column(name = "semestre_referencia", nullable = false, length = 20)
    private String semestreReferencia = "2024.2";

    @Column(length = 200)
    private String tags;

    @Column(name = "arquivo_url", length = 255)
    private String arquivoUrl;

    @Column(name = "votos_uteis", nullable = false)
    private int votosUteis = 0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private StatusModera status = StatusModera.PENDENTE;

    @Column(name = "motivo_moderacao", columnDefinition = "TEXT")
    private String motivoModeracao;

    @Column(name = "criado_em", nullable = false)
    private LocalDateTime criadoEm;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "disciplina_id", nullable = false)
    private Disciplina disciplina;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id", nullable = false)
    private Usuario autor;

    @OneToMany(mappedBy = "material", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DenunciaMaterial> denuncias = new ArrayList<>();

    public MaterialAcervo() {
        this.criadoEm = LocalDateTime.now();
        this.status = StatusModera.PENDENTE;
    }

    public MaterialAcervo(String titulo, TipoMaterial tipo, String semestreReferencia, String tags, String arquivoUrl, Disciplina disciplina, Usuario autor) {
        this.titulo = titulo;
        this.tipo = tipo;
        this.semestreReferencia = semestreReferencia;
        this.tags = tags;
        this.arquivoUrl = arquivoUrl;
        this.disciplina = disciplina;
        this.autor = autor;
        this.votosUteis = 0;
        this.status = StatusModera.PENDENTE;
        this.criadoEm = LocalDateTime.now();
    }

    public void computarVotoUtil() {
        this.votosUteis++;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public TipoMaterial getTipo() { return tipo; }
    public void setTipo(TipoMaterial tipo) { this.tipo = tipo; }

    public String getSemestreReferencia() { return semestreReferencia; }
    public void setSemestreReferencia(String semestreReferencia) { this.semestreReferencia = semestreReferencia; }

    public String getTags() { return tags; }
    public void setTags(String tags) { this.tags = tags; }

    public String getArquivoUrl() { return arquivoUrl; }
    public void setArquivoUrl(String arquivoUrl) { this.arquivoUrl = arquivoUrl; }

    public int getVotosUteis() { return votosUteis; }
    public void setVotosUteis(int votosUteis) { this.votosUteis = votosUteis; }

    public StatusModera getStatus() { return status; }
    public void setStatus(StatusModera status) { this.status = status; }

    public String getMotivoModeracao() { return motivoModeracao; }
    public void setMotivoModeracao(String motivoModeracao) { this.motivoModeracao = motivoModeracao; }

    public LocalDateTime getCriadoEm() { return criadoEm; }
    public void setCriadoEm(LocalDateTime criadoEm) { this.criadoEm = criadoEm; }

    public Disciplina getDisciplina() { return disciplina; }
    public void setDisciplina(Disciplina disciplina) { this.disciplina = disciplina; }

    public Usuario getAutor() { return autor; }
    public void setAutor(Usuario autor) { this.autor = autor; }

    public List<DenunciaMaterial> getDenuncias() { return denuncias; }
    public void setDenuncias(List<DenunciaMaterial> denuncias) { this.denuncias = denuncias; }
}
