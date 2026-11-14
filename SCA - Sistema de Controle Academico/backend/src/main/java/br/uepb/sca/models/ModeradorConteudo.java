package br.uepb.sca.models;

import br.uepb.sca.models.enums.PerfilUsuario;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "moderadores_conteudo")
@PrimaryKeyJoinColumn(name = "usuario_id")
public class ModeradorConteudo extends Usuario {

    @Column(name = "data_designacao")
    private LocalDate dataDesignacao = LocalDate.now();

    public ModeradorConteudo() {
        super();
        setPerfilPrincipal(PerfilUsuario.COORDENADOR);
    }

    public ModeradorConteudo(String nome, String cpf, String email, String senhaHash, PerfilUsuario perfil) {
        super(nome, cpf, email, senhaHash, perfil);
        this.dataDesignacao = LocalDate.now();
    }

    public LocalDate getDataDesignacao() { return dataDesignacao; }
    public void setDataDesignacao(LocalDate dataDesignacao) { this.dataDesignacao = dataDesignacao; }
}
