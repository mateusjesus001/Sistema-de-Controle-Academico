package br.uepb.sca.models;

import br.uepb.sca.models.enums.MotivoDenuncia;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "denuncias_material")
public class DenunciaMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private MotivoDenuncia motivo = MotivoDenuncia.DIREITOS_AUTORAIS;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String descricao;

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    @Column(nullable = false)
    private boolean resolvida = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id", nullable = false)
    private MaterialAcervo material;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "denunciante_id")
    private Usuario denunciante;

    public DenunciaMaterial() {
        this.dataHora = LocalDateTime.now();
        this.resolvida = false;
    }

    public DenunciaMaterial(MotivoDenuncia motivo, String descricao, MaterialAcervo material, Usuario denunciante) {
        this.motivo = motivo;
        this.descricao = descricao;
        this.material = material;
        this.denunciante = denunciante;
        this.dataHora = LocalDateTime.now();
        this.resolvida = false;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public MotivoDenuncia getMotivo() { return motivo; }
    public void setMotivo(MotivoDenuncia motivo) { this.motivo = motivo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }

    public boolean isResolvida() { return resolvida; }
    public void setResolvida(boolean resolvida) { this.resolvida = resolvida; }

    public MaterialAcervo getMaterial() { return material; }
    public void setMaterial(MaterialAcervo material) { this.material = material; }

    public Usuario getDenunciante() { return denunciante; }
    public void setDenunciante(Usuario denunciante) { this.denunciante = denunciante; }
}
