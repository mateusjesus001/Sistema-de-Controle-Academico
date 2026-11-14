package br.uepb.sca.models;

import br.uepb.sca.models.enums.PerfilUsuario;
import br.uepb.sca.models.enums.TipoVinculoMonitoria;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "monitores")
@PrimaryKeyJoinColumn(name = "usuario_id")
public class Monitor extends Aluno {

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_vinculo", nullable = false, length = 20)
    private TipoVinculoMonitoria tipoVinculo = TipoVinculoMonitoria.BOLSISTA;

    @Column(name = "horas_cumpridas", nullable = false)
    private int horasCumpridas = 0;

    @Column(name = "vigencia_fim")
    private LocalDate vigenciaFim;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "disciplina_monitoria_id")
    private Disciplina disciplinaMonitoria;

    public Monitor() {
        super();
        setPerfilPrincipal(PerfilUsuario.MONITOR);
    }

    public Monitor(String nome, String cpf, String email, String senhaHash, String matricula, Curso curso, Disciplina disciplinaMonitoria, TipoVinculoMonitoria tipoVinculo) {
        super(nome, cpf, email, senhaHash, matricula, curso);
        setPerfilPrincipal(PerfilUsuario.MONITOR);
        this.disciplinaMonitoria = disciplinaMonitoria;
        this.tipoVinculo = tipoVinculo;
        this.horasCumpridas = 0;
        this.vigenciaFim = LocalDate.now().plusMonths(6);
    }

    public TipoVinculoMonitoria getTipoVinculo() { return tipoVinculo; }
    public void setTipoVinculo(TipoVinculoMonitoria tipoVinculo) { this.tipoVinculo = tipoVinculo; }

    public int getHorasCumpridas() { return horasCumpridas; }
    public void setHorasCumpridas(int horasCumpridas) { this.horasCumpridas = horasCumpridas; }

    public LocalDate getVigenciaFim() { return vigenciaFim; }
    public void setVigenciaFim(LocalDate vigenciaFim) { this.vigenciaFim = vigenciaFim; }

    public Disciplina getDisciplinaMonitoria() { return disciplinaMonitoria; }
    public void setDisciplinaMonitoria(Disciplina disciplinaMonitoria) { this.disciplinaMonitoria = disciplinaMonitoria; }
}
