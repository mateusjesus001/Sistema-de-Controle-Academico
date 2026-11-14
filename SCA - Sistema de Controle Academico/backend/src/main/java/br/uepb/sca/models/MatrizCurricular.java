package br.uepb.sca.models;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "matrizes_curriculares")
public class MatrizCurricular {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String versao;

    @Column(name = "ano_vigencia", nullable = false)
    private int anoVigencia;

    @Column(name = "semestre_vigencia", nullable = false)
    private int semestreVigencia;

    @Column(name = "ch_minima_obrigatoria", nullable = false)
    private int chMinimaObrigatoria = 2400;

    @Column(name = "ch_minima_optativa", nullable = false)
    private int chMinimaOptativa = 800;

    @Column(nullable = false)
    private boolean ativa = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;

    @OneToMany(mappedBy = "matriz", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemMatriz> itens = new ArrayList<>();

    public MatrizCurricular() {}

    public MatrizCurricular(String versao, int anoVigencia, int semestreVigencia, Curso curso) {
        this.versao = versao;
        this.anoVigencia = anoVigencia;
        this.semestreVigencia = semestreVigencia;
        this.curso = curso;
        this.ativa = true;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getVersao() { return versao; }
    public void setVersao(String versao) { this.versao = versao; }

    public int getAnoVigencia() { return anoVigencia; }
    public void setAnoVigencia(int anoVigencia) { this.anoVigencia = anoVigencia; }

    public int getSemestreVigencia() { return semestreVigencia; }
    public void setSemestreVigencia(int semestreVigencia) { this.semestreVigencia = semestreVigencia; }

    public int getChMinimaObrigatoria() { return chMinimaObrigatoria; }
    public void setChMinimaObrigatoria(int chMinimaObrigatoria) { this.chMinimaObrigatoria = chMinimaObrigatoria; }

    public int getChMinimaOptativa() { return chMinimaOptativa; }
    public void setChMinimaOptativa(int chMinimaOptativa) { this.chMinimaOptativa = chMinimaOptativa; }

    public boolean isAtiva() { return ativa; }
    public void setAtiva(boolean ativa) { this.ativa = ativa; }

    public Curso getCurso() { return curso; }
    public void setCurso(Curso curso) { this.curso = curso; }

    public List<ItemMatriz> getItens() { return itens; }
    public void setItens(List<ItemMatriz> itens) { this.itens = itens; }
}
