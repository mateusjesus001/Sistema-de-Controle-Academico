package br.uepb.sca.dtos;

import br.uepb.sca.models.enums.PerfilUsuario;

public class LoginResponseDTO {
    private Long id;
    private String nome;
    private String email;
    private String cpf;
    private PerfilUsuario perfil;
    private String identificador;
    private String curso;
    private String semestreAtual;
    private Double coeficienteRendimento;
    private Double percentualIntegralizacao;
    private Integer horasConcluidas;
    private Integer horasTotais;
    private String token;

    public LoginResponseDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public PerfilUsuario getPerfil() { return perfil; }
    public void setPerfil(PerfilUsuario perfil) { this.perfil = perfil; }

    public String getIdentificador() { return identificador; }
    public void setIdentificador(String identificador) { this.identificador = identificador; }

    public String getCurso() { return curso; }
    public void setCurso(String curso) { this.curso = curso; }

    public String getSemestreAtual() { return semestreAtual; }
    public void setSemestreAtual(String semestreAtual) { this.semestreAtual = semestreAtual; }

    public Double getCoeficienteRendimento() { return coeficienteRendimento; }
    public void setCoeficienteRendimento(Double coeficienteRendimento) { this.coeficienteRendimento = coeficienteRendimento; }

    public Double getPercentualIntegralizacao() { return percentualIntegralizacao; }
    public void setPercentualIntegralizacao(Double percentualIntegralizacao) { this.percentualIntegralizacao = percentualIntegralizacao; }

    public Integer getHorasConcluidas() { return horasConcluidas; }
    public void setHorasConcluidas(Integer horasConcluidas) { this.horasConcluidas = horasConcluidas; }

    public Integer getHorasTotais() { return horasTotais; }
    public void setHorasTotais(Integer horasTotais) { this.horasTotais = horasTotais; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
}
