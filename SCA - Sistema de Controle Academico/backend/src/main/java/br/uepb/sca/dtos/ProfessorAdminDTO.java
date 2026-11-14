package br.uepb.sca.dtos;

public class ProfessorAdminDTO {
    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private String siape;
    private String departamento;
    private String senha;

    public ProfessorAdminDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSiape() { return siape; }
    public void setSiape(String siape) { this.siape = siape; }

    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
}
