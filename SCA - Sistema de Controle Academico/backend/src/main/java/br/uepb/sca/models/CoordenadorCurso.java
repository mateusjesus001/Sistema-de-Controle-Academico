package br.uepb.sca.models;

import br.uepb.sca.models.enums.PerfilUsuario;
import jakarta.persistence.*;

@Entity
@Table(name = "coordenadores_curso")
@PrimaryKeyJoinColumn(name = "usuario_id")
public class CoordenadorCurso extends ModeradorConteudo {

    @Column(name = "portaria_nomeacao", length = 50)
    private String portariaNomeacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")
    private Curso curso;

    public CoordenadorCurso() {
        super();
        setPerfilPrincipal(PerfilUsuario.COORDENADOR);
    }

    public CoordenadorCurso(String nome, String cpf, String email, String senhaHash, String portariaNomeacao, Curso curso) {
        super(nome, cpf, email, senhaHash, PerfilUsuario.COORDENADOR);
        this.portariaNomeacao = portariaNomeacao;
        this.curso = curso;
    }

    public String getPortariaNomeacao() { return portariaNomeacao; }
    public void setPortariaNomeacao(String portariaNomeacao) { this.portariaNomeacao = portariaNomeacao; }

    public Curso getCurso() { return curso; }
    public void setCurso(Curso curso) { this.curso = curso; }
}
