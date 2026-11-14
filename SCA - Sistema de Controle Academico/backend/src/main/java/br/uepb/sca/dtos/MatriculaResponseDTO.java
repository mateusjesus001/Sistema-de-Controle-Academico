package br.uepb.sca.dtos;

import java.util.ArrayList;
import java.util.List;

public class MatriculaResponseDTO {
    private boolean sucesso;
    private String mensagem;
    private String protocoloComprovante;
    private List<String> turmasMatriculadas = new ArrayList<>();
    private List<String> turmasFilaEspera = new ArrayList<>();
    private List<String> conflitosDetectados = new ArrayList<>();

    public MatriculaResponseDTO() {}

    public boolean isSucesso() { return sucesso; }
    public void setSucesso(boolean sucesso) { this.sucesso = sucesso; }

    public String getMensagem() { return mensagem; }
    public void setMensagem(String mensagem) { this.mensagem = mensagem; }

    public String getProtocoloComprovante() { return protocoloComprovante; }
    public void setProtocoloComprovante(String protocoloComprovante) { this.protocoloComprovante = protocoloComprovante; }

    public List<String> getTurmasMatriculadas() { return turmasMatriculadas; }
    public void setTurmasMatriculadas(List<String> turmasMatriculadas) { this.turmasMatriculadas = turmasMatriculadas; }

    public List<String> getTurmasFilaEspera() { return turmasFilaEspera; }
    public void setTurmasFilaEspera(List<String> turmasFilaEspera) { this.turmasFilaEspera = turmasFilaEspera; }

    public List<String> getConflitosDetectados() { return conflitosDetectados; }
    public void setConflitosDetectados(List<String> conflitosDetectados) { this.conflitosDetectados = conflitosDetectados; }
}
