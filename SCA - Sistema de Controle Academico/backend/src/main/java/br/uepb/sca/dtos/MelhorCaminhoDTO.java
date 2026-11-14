package br.uepb.sca.dtos;

import java.util.ArrayList;
import java.util.List;

public class MelhorCaminhoDTO {
    private String tituloOpcao; // Ex: "Caminho Mais Rápido (Caminho Crítico)"
    private String justificativa; // Ex: "Prioriza disciplinas que trancam a maior cadeia de pré-requisitos"
    private String previsaoFormatura; // Ex: "2027.1 (em 2 anos e meio)"
    private int semestresRestantes;
    private int cargaHorariaTotalSugerida;
    private List<Long> turmasSugeridasIds = new ArrayList<>();
    private List<String> nomesDisciplinas = new ArrayList<>();

    public MelhorCaminhoDTO() {}

    public String getTituloOpcao() { return tituloOpcao; }
    public void setTituloOpcao(String tituloOpcao) { this.tituloOpcao = tituloOpcao; }

    public String getJustificativa() { return justificativa; }
    public void setJustificativa(String justificativa) { this.justificativa = justificativa; }

    public String getPrevisaoFormatura() { return previsaoFormatura; }
    public void setPrevisaoFormatura(String previsaoFormatura) { this.previsaoFormatura = previsaoFormatura; }

    public int getSemestresRestantes() { return semestresRestantes; }
    public void setSemestresRestantes(int semestresRestantes) { this.semestresRestantes = semestresRestantes; }

    public int getCargaHorariaTotalSugerida() { return cargaHorariaTotalSugerida; }
    public void setCargaHorariaTotalSugerida(int cargaHorariaTotalSugerida) { this.cargaHorariaTotalSugerida = cargaHorariaTotalSugerida; }

    public List<Long> getTurmasSugeridasIds() { return turmasSugeridasIds; }
    public void setTurmasSugeridasIds(List<Long> turmasSugeridasIds) { this.turmasSugeridasIds = turmasSugeridasIds; }

    public List<String> getNomesDisciplinas() { return nomesDisciplinas; }
    public void setNomesDisciplinas(List<String> nomesDisciplinas) { this.nomesDisciplinas = nomesDisciplinas; }
}
