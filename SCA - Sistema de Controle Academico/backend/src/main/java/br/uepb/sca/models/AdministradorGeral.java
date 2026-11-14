package br.uepb.sca.models;

import br.uepb.sca.models.enums.PerfilUsuario;
import jakarta.persistence.*;

@Entity
@Table(name = "administradores_gerais")
@PrimaryKeyJoinColumn(name = "usuario_id")
public class AdministradorGeral extends Usuario {

    @Column(name = "privilegio_root", nullable = false)
    private boolean privilegioRoot = true;

    public AdministradorGeral() {
        super();
        setPerfilPrincipal(PerfilUsuario.ADMIN);
    }

    public AdministradorGeral(String nome, String cpf, String email, String senhaHash) {
        super(nome, cpf, email, senhaHash, PerfilUsuario.ADMIN);
        this.privilegioRoot = true;
    }

    public boolean isPrivilegioRoot() { return privilegioRoot; }
    public void setPrivilegioRoot(boolean privilegioRoot) { this.privilegioRoot = privilegioRoot; }
}
