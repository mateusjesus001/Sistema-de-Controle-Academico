package br.uepb.sca.models;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "itens_matriz")
public class ItemMatriz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "periodo_sugerido", nullable = false)
    private int periodoSugerido;

    @Column(nullable = false)
    private boolean obrigatoria = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matriz_id", nullable = false)
    private MatrizCurricular matriz;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "disciplina_id", nullable = false)
    private Disciplina disciplina;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "prerequisitos_disciplina",
        joinColumns = @JoinColumn(name = "item_matriz_id"),
        inverseJoinColumns = @JoinColumn(name = "disciplina_prereq_id")
    )
    private List<Disciplina> preRequisitos = new ArrayList<>();

    public ItemMatriz() {}

    public ItemMatriz(MatrizCurricular matriz, Disciplina disciplina, int periodoSugerido, boolean obrigatoria) {
        this.matriz = matriz;
        this.disciplina = disciplina;
        this.periodoSugerido = periodoSugerido;
        this.obrigatoria = obrigatoria;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getPeriodoSugerido() { return periodoSugerido; }
    public void setPeriodoSugerido(int periodoSugerido) { this.periodoSugerido = periodoSugerido; }

    public boolean isObrigatoria() { return obrigatoria; }
    public void setObrigatoria(boolean obrigatoria) { this.obrigatoria = obrigatoria; }

    public MatrizCurricular getMatriz() { return matriz; }
    public void setMatriz(MatrizCurricular matriz) { this.matriz = matriz; }

    public Disciplina getDisciplina() { return disciplina; }
    public void setDisciplina(Disciplina disciplina) { this.disciplina = disciplina; }

    public List<Disciplina> getPreRequisitos() { return preRequisitos; }
    public void setPreRequisitos(List<Disciplina> preRequisitos) { this.preRequisitos = preRequisitos; }
}
