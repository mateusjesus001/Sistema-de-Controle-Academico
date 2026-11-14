package br.uepb.sca.dtos;

import java.util.List;

public class MatriculaRequestDTO {
    private Long alunoId;
    private List<Long> turmasIds;

    public MatriculaRequestDTO() {}

    public MatriculaRequestDTO(Long alunoId, List<Long> turmasIds) {
        this.alunoId = alunoId;
        this.turmasIds = turmasIds;
    }

    public Long getAlunoId() { return alunoId; }
    public void setAlunoId(Long alunoId) { this.alunoId = alunoId; }

    public List<Long> getTurmasIds() { return turmasIds; }
    public void setTurmasIds(List<Long> turmasIds) { this.turmasIds = turmasIds; }
}
