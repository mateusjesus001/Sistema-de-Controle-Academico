package br.uepb.sca.models;

import br.uepb.sca.models.enums.PerfilUsuario;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(nullable = false, unique = true, length = 14)
    private String cpf;

    @Column(nullable = false, unique = true, length = 120)
    private String email;

    @Column(name = "senha_hash", nullable = false, length = 255)
    private String senhaHash;

    @Enumerated(EnumType.STRING)
    @Column(name = "perfil_principal", nullable = false, length = 30)
    private PerfilUsuario perfilPrincipal;

    @Column(nullable = false)
    private boolean ativo = true;

    @Column(name = "notif_email", nullable = false)
    private boolean notifEmail = true;

    @Column(name = "notif_sistema", nullable = false)
    private boolean notifSistema = true;

    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    public Usuario() {
        this.criadoEm = LocalDateTime.now();
    }

    public Usuario(String nome, String cpf, String email, String senhaHash, PerfilUsuario perfilPrincipal) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senhaHash = senhaHash;
        this.perfilPrincipal = perfilPrincipal;
        this.ativo = true;
        this.notifEmail = true;
        this.notifSistema = true;
        this.criadoEm = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenhaHash() { return senhaHash; }
    public void setSenhaHash(String senhaHash) { this.senhaHash = senhaHash; }

    public PerfilUsuario getPerfilPrincipal() { return perfilPrincipal; }
    public void setPerfilPrincipal(PerfilUsuario perfilPrincipal) { this.perfilPrincipal = perfilPrincipal; }

    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }

    public boolean isNotifEmail() { return notifEmail; }
    public void setNotifEmail(boolean notifEmail) { this.notifEmail = notifEmail; }

    public boolean isNotifSistema() { return notifSistema; }
    public void setNotifSistema(boolean notifSistema) { this.notifSistema = notifSistema; }

    public LocalDateTime getCriadoEm() { return criadoEm; }
    public void setCriadoEm(LocalDateTime criadoEm) { this.criadoEm = criadoEm; }
}
