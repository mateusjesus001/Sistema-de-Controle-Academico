package br.uepb.sca.models;

import br.uepb.sca.models.enums.PerfilUsuario;
import jakarta.persistence.*;

@Entity
@Table(name = "secretarios_academicos")
@PrimaryKeyJoinColumn(name = "usuario_id")
public class SecretarioAcademico extends Usuario {

    @Column(name = "codigo_funcional", nullable = false, unique = true, length = 30)
    private String codigoFuncional;

    @Column(nullable = false, length = 100)
    private String setor = "Secretaria Geral de Cursos";

    public SecretarioAcademico() {
        super();
        setPerfilPrincipal(PerfilUsuario.SECRETARIO);
    }

    public SecretarioAcademico(String nome, String cpf, String email, String senhaHash, String codigoFuncional) {
        super(nome, cpf, email, senhaHash, PerfilUsuario.SECRETARIO);
        this.codigoFuncional = codigoFuncional;
    }

    public String getCodigoFuncional() { return codigoFuncional; }
    public void setCodigoFuncional(String codigoFuncional) { this.codigoFuncional = codigoFuncional; }

    public String getSetor() { return setor; }
    public void setSetor(String setor) { this.setor = setor; }
}
