package br.uepb.sca.dtos;

public class AlocarProfessorDTO {
    private Long turmaId;
    private Long professorId;
    private boolean substituicao;
    private String motivoSubstituicao;

    public AlocarProfessorDTO() {}

    public Long getTurmaId() { return turmaId; }
    public void setTurmaId(Long turmaId) { this.turmaId = turmaId; }

    public Long getProfessorId() { return professorId; }
    public void setProfessorId(Long professorId) { this.professorId = professorId; }

    public boolean isSubstituicao() { return substituicao; }
    public void setSubstituicao(boolean substituicao) { this.substituicao = substituicao; }

    public String getMotivoSubstituicao() { return motivoSubstituicao; }
    public void setMotivoSubstituicao(String motivoSubstituicao) { this.motivoSubstituicao = motivoSubstituicao; }
}
