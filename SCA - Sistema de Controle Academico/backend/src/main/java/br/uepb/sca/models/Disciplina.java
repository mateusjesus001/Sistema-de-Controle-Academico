package br.uepb.sca.models;

import br.uepb.sca.models.enums.TipoDisciplina;
import jakarta.persistence.*;

@Entity
@Table(name = "disciplinas")
public class Disciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String codigo;

    @Column(nullable = false, length = 120)
    private String nome;

    @Column(name = "carga_horaria", nullable = false)
    private int cargaHoraria;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TipoDisciplina tipo = TipoDisciplina.OBRIGATORIA;

    @Column(columnDefinition = "TEXT")
    private String ementa;

    @Column(nullable = false, length = 100)
    private String departamento;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "curso_id")
    private Curso curso;

    public Disciplina() {}

    public Disciplina(String codigo, String nome, int cargaHoraria, TipoDisciplina tipo, String ementa, String departamento, Curso curso) {
        this.codigo = codigo;
        this.nome = nome;
        this.cargaHoraria = cargaHoraria;
        this.tipo = tipo;
        this.ementa = ementa;
        this.departamento = departamento;
        this.curso = curso;
    }

    public Disciplina(String codigo, String nome, int cargaHoraria, TipoDisciplina tipo, String ementa, String departamento) {
        this(codigo, nome, cargaHoraria, tipo, ementa, departamento, null);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getCargaHoraria() { return cargaHoraria; }
    public void setCargaHoraria(int cargaHoraria) { this.cargaHoraria = cargaHoraria; }

    public TipoDisciplina getTipo() { return tipo; }
    public void setTipo(TipoDisciplina tipo) { this.tipo = tipo; }

    public String getEmenta() { return ementa; }
    public void setEmenta(String ementa) { this.ementa = ementa; }

    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }

    public Curso getCurso() { return curso; }
    public void setCurso(Curso curso) { this.curso = curso; }
}
