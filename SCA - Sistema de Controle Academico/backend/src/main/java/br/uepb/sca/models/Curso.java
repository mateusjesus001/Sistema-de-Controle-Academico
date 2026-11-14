package br.uepb.sca.models;

import br.uepb.sca.models.enums.Turno;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cursos")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String codigo;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length = 100)
    private String departamento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Turno turno = Turno.INTEGRAL;

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MatrizCurricular> matrizes = new ArrayList<>();

    public Curso() {}

    public Curso(String codigo, String nome, String departamento, Turno turno) {
        this.codigo = codigo;
        this.nome = nome;
        this.departamento = departamento;
        this.turno = turno;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }

    public Turno getTurno() { return turno; }
    public void setTurno(Turno turno) { this.turno = turno; }

    public List<MatrizCurricular> getMatrizes() { return matrizes; }
    public void setMatrizes(List<MatrizCurricular> matrizes) { this.matrizes = matrizes; }
}
