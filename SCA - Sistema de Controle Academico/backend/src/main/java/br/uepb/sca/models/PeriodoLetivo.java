package br.uepb.sca.models;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "periodos_letivos")
public class PeriodoLetivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int ano;

    @Column(nullable = false)
    private int periodo;

    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "data_fim", nullable = false)
    private LocalDate dataFim;

    @Column(name = "inicio_matricula", nullable = false)
    private LocalDate inicioMatricula;

    @Column(name = "fim_matricula", nullable = false)
    private LocalDate fimMatricula;

    @Column(name = "inicio_ajuste", nullable = false)
    private LocalDate inicioAjuste;

    @Column(name = "fim_ajuste", nullable = false)
    private LocalDate fimAjuste;

    @Column(name = "data_limite_trancamento", nullable = false)
    private LocalDate dataLimiteTrancamento;

    @Column(name = "data_limite_fechamento_diario", nullable = false)
    private LocalDate dataLimiteFechamentoDiario;

    @Column(nullable = false)
    private boolean ativo = true;

    public PeriodoLetivo() {}

    public PeriodoLetivo(int ano, int periodo, LocalDate dataInicio, LocalDate dataFim, LocalDate inicioMatricula, LocalDate fimMatricula) {
        this.ano = ano;
        this.periodo = periodo;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.inicioMatricula = inicioMatricula;
        this.fimMatricula = fimMatricula;
        this.inicioAjuste = fimMatricula.plusDays(1);
        this.fimAjuste = this.inicioAjuste.plusDays(7);
        this.dataLimiteTrancamento = dataFim.minusMonths(1);
        this.dataLimiteFechamentoDiario = dataFim.plusDays(5);
        this.ativo = true;
    }

    public boolean estaEmJanelaMatricula() {
        LocalDate hoje = LocalDate.now();
        return !hoje.isBefore(inicioMatricula) && !hoje.isAfter(fimMatricula);
    }

    public boolean estaEmJanelaAjuste() {
        LocalDate hoje = LocalDate.now();
        return !hoje.isBefore(inicioAjuste) && !hoje.isAfter(fimAjuste);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getAno() { return ano; }
    public void setAno(int ano) { this.ano = ano; }

    public int getPeriodo() { return periodo; }
    public void setPeriodo(int periodo) { this.periodo = periodo; }

    public LocalDate getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDate dataInicio) { this.dataInicio = dataInicio; }

    public LocalDate getDataFim() { return dataFim; }
    public void setDataFim(LocalDate dataFim) { this.dataFim = dataFim; }

    public LocalDate getInicioMatricula() { return inicioMatricula; }
    public void setInicioMatricula(LocalDate inicioMatricula) { this.inicioMatricula = inicioMatricula; }

    public LocalDate getFimMatricula() { return fimMatricula; }
    public void setFimMatricula(LocalDate fimMatricula) { this.fimMatricula = fimMatricula; }

    public LocalDate getInicioAjuste() { return inicioAjuste; }
    public void setInicioAjuste(LocalDate inicioAjuste) { this.inicioAjuste = inicioAjuste; }

    public LocalDate getFimAjuste() { return fimAjuste; }
    public void setFimAjuste(LocalDate fimAjuste) { this.fimAjuste = fimAjuste; }

    public LocalDate getDataLimiteTrancamento() { return dataLimiteTrancamento; }
    public void setDataLimiteTrancamento(LocalDate dataLimiteTrancamento) { this.dataLimiteTrancamento = dataLimiteTrancamento; }

    public LocalDate getDataLimiteFechamentoDiario() { return dataLimiteFechamentoDiario; }
    public void setDataLimiteFechamentoDiario(LocalDate dataLimiteFechamentoDiario) { this.dataLimiteFechamentoDiario = dataLimiteFechamentoDiario; }

    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
}
