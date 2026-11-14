package br.uepb.sca.models;

import br.uepb.sca.models.enums.PerfilUsuario;
import jakarta.persistence.*;

@Entity
@Table(name = "professores")
@PrimaryKeyJoinColumn(name = "usuario_id")
public class Professor extends Usuario {

    @Column(nullable = false, unique = true, length = 30)
    private String siape;

    @Column(nullable = false, length = 100)
    private String departamento;

    public Professor() {
        super();
        setPerfilPrincipal(PerfilUsuario.PROFESSOR);
    }

    public Professor(String nome, String cpf, String email, String senhaHash, String siape, String departamento) {
        super(nome, cpf, email, senhaHash, PerfilUsuario.PROFESSOR);
        this.siape = siape;
        this.departamento = departamento;
    }

    public String getSiape() { return siape; }
    public void setSiape(String siape) { this.siape = siape; }

    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }
}
