package br.uepb.sca.dtos;

public class LoginRequestDTO {
    private String login; // Matrícula, SIAPE ou E-mail
    private String senha;

    public LoginRequestDTO() {}

    public LoginRequestDTO(String login, String senha) {
        this.login = login;
        this.senha = senha;
    }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
}
