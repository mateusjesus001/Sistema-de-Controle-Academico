package br.uepb.sca.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "logs_auditoria")
public class LogAuditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    @Column(nullable = false, length = 100)
    private String acao;

    @Column(nullable = false, length = 100)
    private String entidade;

    @Column(name = "registro_id", nullable = false)
    private Long registroId;

    @Column(name = "valor_anterior_json", columnDefinition = "TEXT")
    private String valorAnteriorJson;

    @Column(name = "valor_novo_json", columnDefinition = "TEXT")
    private String valorNovoJson;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "operador_id")
    private Usuario operador;

    public LogAuditoria() {
        this.dataHora = LocalDateTime.now();
    }

    public LogAuditoria(String acao, String entidade, Long registroId, String valorAnteriorJson, String valorNovoJson, Usuario operador) {
        this.acao = acao;
        this.entidade = entidade;
        this.registroId = registroId;
        this.valorAnteriorJson = valorAnteriorJson;
        this.valorNovoJson = valorNovoJson;
        this.operador = operador;
        this.dataHora = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }

    public String getAcao() { return acao; }
    public void setAcao(String acao) { this.acao = acao; }

    public String getEntidade() { return entidade; }
    public void setEntidade(String entidade) { this.entidade = entidade; }

    public Long getRegistroId() { return registroId; }
    public void setRegistroId(Long registroId) { this.registroId = registroId; }

    public String getValorAnteriorJson() { return valorAnteriorJson; }
    public void setValorAnteriorJson(String valorAnteriorJson) { this.valorAnteriorJson = valorAnteriorJson; }

    public String getValorNovoJson() { return valorNovoJson; }
    public void setValorNovoJson(String valorNovoJson) { this.valorNovoJson = valorNovoJson; }

    public Usuario getOperador() { return operador; }
    public void setOperador(Usuario operador) { this.operador = operador; }
}
