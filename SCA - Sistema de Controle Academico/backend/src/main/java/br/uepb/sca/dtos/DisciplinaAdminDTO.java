package br.uepb.sca.dtos;

import br.uepb.sca.models.enums.TipoDisciplina;

public class DisciplinaAdminDTO {
    private Long id;
    private String codigo;
    private String nome;
    private int cargaHoraria;
    private TipoDisciplina tipo;
    private String ementa;
    private String departamento;
    private Long cursoId;
    private String nomeCurso;
    private Long turmaId;
    private Long professorId;
    private String professorNome;
    private int qtdAlunosMatriculados;

    public DisciplinaAdminDTO() {}

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

    public Long getCursoId() { return cursoId; }
    public void setCursoId(Long cursoId) { this.cursoId = cursoId; }

    public String getNomeCurso() { return nomeCurso; }
    public void setNomeCurso(String nomeCurso) { this.nomeCurso = nomeCurso; }

    public Long getTurmaId() { return turmaId; }
    public void setTurmaId(Long turmaId) { this.turmaId = turmaId; }

    public Long getProfessorId() { return professorId; }
    public void setProfessorId(Long professorId) { this.professorId = professorId; }

    public String getProfessorNome() { return professorNome; }
    public void setProfessorNome(String professorNome) { this.professorNome = professorNome; }

    public int getQtdAlunosMatriculados() { return qtdAlunosMatriculados; }
    public void setQtdAlunosMatriculados(int qtdAlunosMatriculados) { this.qtdAlunosMatriculados = qtdAlunosMatriculados; }
}
