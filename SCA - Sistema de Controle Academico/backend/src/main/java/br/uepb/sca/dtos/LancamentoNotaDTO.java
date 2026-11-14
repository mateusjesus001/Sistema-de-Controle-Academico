package br.uepb.sca.dtos;

public class LancamentoNotaDTO {
    private Long matriculaId;
    private int avaliacaoNumero; // 1, 2, 3 ou 4 (Exame Final)
    private double nota;

    public LancamentoNotaDTO() {}

    public Long getMatriculaId() { return matriculaId; }
    public void setMatriculaId(Long matriculaId) { this.matriculaId = matriculaId; }

    public int getAvaliacaoNumero() { return avaliacaoNumero; }
    public void setAvaliacaoNumero(int avaliacaoNumero) { this.avaliacaoNumero = avaliacaoNumero; }

    public double getNota() { return nota; }
    public void setNota(double nota) { this.nota = nota; }
}
