package br.uepb.sca.models;

import br.uepb.sca.models.enums.PerfilUsuario;
import jakarta.persistence.*;

@Entity
@Table(name = "alunos")
@PrimaryKeyJoinColumn(name = "usuario_id")
public class Aluno extends Usuario {

    @Column(nullable = false, unique = true, length = 20)
    private String matricula;

    @Column(name = "coeficiente_rendimento", nullable = false)
    private double coeficienteRendimento = 0.0;

    @Column(name = "percentual_integralizacao", nullable = false)
    private double percentualIntegralizacao = 0.0;

    @Column(name = "horas_concluidas", nullable = false)
    private int horasConcluidas = 0;

    @Column(name = "horas_totais", nullable = false)
    private int horasTotais = 3200;

    @Column(name = "semestre_atual", length = 30)
    private String semestreAtual = "1º período";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")
    private Curso curso;

    public Aluno() {
        super();
        setPerfilPrincipal(PerfilUsuario.ALUNO);
    }

    public Aluno(String nome, String cpf, String email, String senhaHash, String matricula, Curso curso) {
        super(nome, cpf, email, senhaHash, PerfilUsuario.ALUNO);
        this.matricula = matricula;
        this.curso = curso;
    }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    public double getCoeficienteRendimento() { return coeficienteRendimento; }
    public void setCoeficienteRendimento(double coeficienteRendimento) { this.coeficienteRendimento = coeficienteRendimento; }

    public double getPercentualIntegralizacao() { return percentualIntegralizacao; }
    public void setPercentualIntegralizacao(double percentualIntegralizacao) { this.percentualIntegralizacao = percentualIntegralizacao; }

    public int getHorasConcluidas() { return horasConcluidas; }
    public void setHorasConcluidas(int horasConcluidas) { this.horasConcluidas = horasConcluidas; }

    public int getHorasTotais() { return horasTotais; }
    public void setHorasTotais(int horasTotais) { this.horasTotais = horasTotais; }

    public String getSemestreAtual() { return semestreAtual; }
    public void setSemestreAtual(String semestreAtual) { this.semestreAtual = semestreAtual; }

    public Curso getCurso() { return curso; }
    public void setCurso(Curso curso) { this.curso = curso; }
}
