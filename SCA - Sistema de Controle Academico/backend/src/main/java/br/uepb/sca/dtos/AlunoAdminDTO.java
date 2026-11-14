package br.uepb.sca.dtos;

public class AlunoAdminDTO {
    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private String matricula;
    private Long cursoId;
    private String nomeCurso;
    private String semestreAtual;
    private double coeficienteRendimento;
    private double percentualIntegralizacao;
    private String senha;

    public AlunoAdminDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    public Long getCursoId() { return cursoId; }
    public void setCursoId(Long cursoId) { this.cursoId = cursoId; }

    public String getNomeCurso() { return nomeCurso; }
    public void setNomeCurso(String nomeCurso) { this.nomeCurso = nomeCurso; }

    public String getSemestreAtual() { return semestreAtual; }
    public void setSemestreAtual(String semestreAtual) { this.semestreAtual = semestreAtual; }

    public double getCoeficienteRendimento() { return coeficienteRendimento; }
    public void setCoeficienteRendimento(double coeficienteRendimento) { this.coeficienteRendimento = coeficienteRendimento; }

    public double getPercentualIntegralizacao() { return percentualIntegralizacao; }
    public void setPercentualIntegralizacao(double percentualIntegralizacao) { this.percentualIntegralizacao = percentualIntegralizacao; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
}
