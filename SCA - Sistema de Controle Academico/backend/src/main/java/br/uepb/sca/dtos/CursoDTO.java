package br.uepb.sca.dtos;

import br.uepb.sca.models.enums.Turno;

public class CursoDTO {
    private Long id;
    private String codigo;
    private String nome;
    private String departamento;
    private Turno turno;
    private int qtdDisciplinas;
    private int qtdAlunos;

    public CursoDTO() {}

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

    public int getQtdDisciplinas() { return qtdDisciplinas; }
    public void setQtdDisciplinas(int qtdDisciplinas) { this.qtdDisciplinas = qtdDisciplinas; }

    public int getQtdAlunos() { return qtdAlunos; }
    public void setQtdAlunos(int qtdAlunos) { this.qtdAlunos = qtdAlunos; }
}
