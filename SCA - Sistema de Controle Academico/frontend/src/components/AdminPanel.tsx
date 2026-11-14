import { useState, useEffect } from "react";
import { apiFetch } from "../services/api";

interface Curso {
  id: number;
  codigo: string;
  nome: string;
  departamento: string;
  turno: "MANHA" | "TARDE" | "NOITE" | "INTEGRAL";
  qtdDisciplinas: number;
  qtdAlunos: number;
}

interface Disciplina {
  id: number;
  codigo: string;
  nome: string;
  cargaHoraria: number;
  tipo: "OBRIGATORIA" | "OPTATIVA" | "ATIVIDADE_COMPLEMENTAR";
  ementa?: string;
  departamento: string;
  cursoId?: number;
  nomeCurso?: string;
  turmaId?: number;
  professorId?: number;
  professorNome?: string;
  qtdAlunosMatriculados: number;
}

interface Aluno {
  id: number;
  nome: string;
  cpf: string;
  email: string;
  matricula: string;
  cursoId?: number;
  nomeCurso?: string;
  semestreAtual?: string;
  coeficienteRendimento: number;
  percentualIntegralizacao: number;
}

interface Professor {
  id: number;
  nome: string;
  siape: string;
  departamento: string;
}

export function AdminPanel({ toast }: { toast: (msg: string) => void }) {
  const [tab, setTab] = useState<"cursos" | "disciplinas" | "alunos" | "professores">("cursos");

  // Dados
  const [cursos, setCursos] = useState<Curso[]>([]);
  const [disciplinas, setDisciplinas] = useState<Disciplina[]>([]);
  const [alunos, setAlunos] = useState<Aluno[]>([]);
  const [professores, setProfessores] = useState<Professor[]>([]);
  const [loading, setLoading] = useState(false);

  // Modais de Cadastro
  const [modalCursoOpen, setModalCursoOpen] = useState(false);
  const [modalDiscOpen, setModalDiscOpen] = useState(false);
  const [modalAlunoOpen, setModalAlunoOpen] = useState(false);
  const [modalSubstOpen, setModalSubstOpen] = useState(false);

  // Filtros
  const [cursoFiltro, setCursoFiltro] = useState<number | "ALL">("ALL");

  // Formulário Curso
  const [formCurso, setFormCurso] = useState({
    codigo: "",
    nome: "",
    departamento: "Departamento de Sistemas e Computação",
    turno: "INTEGRAL" as "MANHA" | "TARDE" | "NOITE" | "INTEGRAL",
  });

  // Formulário Disciplina
  const [formDisc, setFormDisc] = useState({
    codigo: "",
    nome: "",
    cargaHoraria: 60,
    tipo: "OBRIGATORIA" as "OBRIGATORIA" | "OPTATIVA",
    ementa: "",
    departamento: "DSC",
    cursoId: 0,
    professorId: 0,
  });

  // Formulário Aluno
  const [formAluno, setFormAluno] = useState({
    nome: "",
    cpf: "",
    email: "",
    matricula: "",
    cursoId: 0,
    semestreAtual: "1º período",
  });

  // Formulário Substituição de Professor
  const [substTurmaId, setSubstTurmaId] = useState<number | null>(null);
  const [substTurmaNome, setSubstTurmaNome] = useState("");
  const [substNovoProfId, setSubstNovoProfId] = useState<number | null>(null);
  const [substMotivo, setSubstMotivo] = useState("");
  const [isSubstituicaoFormal, setIsSubstituicaoFormal] = useState(true);

  // Formulário Matrícula Direta
  const [matrTurmaId, setMatrTurmaId] = useState<number | null>(null);
  const [matrAlunoId, setMatrAlunoId] = useState<number | null>(null);

  // Carrega dados iniciais da API (backend obrigatório)
  async function carregarDados() {
    setLoading(true);
    try {
      const dataCursos = await apiFetch<Curso[]>("/admin/cursos");
      setCursos(dataCursos);
    } catch (err: any) {
      toast(`Erro ao carregar cursos: ${err.message}`);
    }

    try {
      const dataDisc = await apiFetch<Disciplina[]>("/admin/disciplinas");
      setDisciplinas(dataDisc);
    } catch (err: any) {
      toast(`Erro ao carregar disciplinas: ${err.message}`);
    }

    try {
      const dataAlunos = await apiFetch<Aluno[]>("/admin/alunos");
      setAlunos(dataAlunos);
    } catch (err: any) {
      toast(`Erro ao carregar alunos: ${err.message}`);
    }

    try {
      const dataProfs = await apiFetch<Professor[]>("/admin/professores");
      setProfessores(dataProfs);
    } catch (err: any) {
      toast(`Erro ao carregar professores: ${err.message}`);
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    carregarDados();
  }, []);

  // Handlers Cursos
  async function handleSalvarCurso(e: React.FormEvent) {
    e.preventDefault();
    if (!formCurso.codigo || !formCurso.nome) return;
    try {
      const novo = await apiFetch<Curso>("/admin/cursos", {
        method: "POST",
        body: JSON.stringify(formCurso),
      });
      setCursos([...cursos, novo]);
      toast(`Curso ${novo.nome} adicionado com sucesso!`);
    } catch (err: any) {
      toast(`Erro ao criar curso: ${err.message}`);
    }
    setModalCursoOpen(false);
    setFormCurso({ codigo: "", nome: "", departamento: "DSC", turno: "INTEGRAL" });
  }

  async function handleRemoverCurso(id: number) {
    if (!confirm("Tem certeza que deseja remover este curso institucional?")) return;
    try {
      await apiFetch(`/admin/cursos/${id}`, { method: "DELETE" });
    } catch (err: any) {
      toast(`Erro ao remover curso: ${err.message}`);
      return;
    }
    setCursos(cursos.filter((c) => c.id !== id));
    toast("Curso removido da instituição.");
  }

  // Handlers Disciplinas
  async function handleSalvarDisciplina(e: React.FormEvent) {
    e.preventDefault();
    if (!formDisc.codigo || !formDisc.nome) return;
    try {
      const nova = await apiFetch<Disciplina>("/admin/disciplinas", {
        method: "POST",
        body: JSON.stringify(formDisc),
      });
      setDisciplinas([...disciplinas, nova]);
      toast(`Disciplina ${nova.nome} cadastrada!`);
    } catch (err: any) {
      toast(`Erro ao criar disciplina: ${err.message}`);
    }
    setModalDiscOpen(false);
    setFormDisc({ codigo: "", nome: "", cargaHoraria: 60, tipo: "OBRIGATORIA", ementa: "", departamento: "DSC", cursoId: 0, professorId: 0 });
  }

  async function handleRemoverDisciplina(id: number) {
    if (!confirm("Deseja remover esta disciplina e sua respectiva turma?")) return;
    try {
      await apiFetch(`/admin/disciplinas/${id}`, { method: "DELETE" });
    } catch (err: any) {
      toast(`Erro ao remover disciplina: ${err.message}`);
      return;
    }
    setDisciplinas(disciplinas.filter((d) => d.id !== id));
    toast("Disciplina removida com sucesso.");
  }

  // Handlers Alunos
  async function handleSalvarAluno(e: React.FormEvent) {
    e.preventDefault();
    if (!formAluno.nome || !formAluno.matricula) return;
    try {
      const novo = await apiFetch<Aluno>("/admin/alunos", {
        method: "POST",
        body: JSON.stringify(formAluno),
      });
      setAlunos([...alunos, novo]);
      toast(`Aluno ${novo.nome} matriculado no sistema!`);
    } catch (err: any) {
      toast(`Erro ao criar aluno: ${err.message}`);
    }
    setModalAlunoOpen(false);
    setFormAluno({ nome: "", cpf: "", email: "", matricula: "", cursoId: 0, semestreAtual: "1º período" });
  }

  async function handleRemoverAluno(id: number) {
    if (!confirm("Deseja desativar o registro deste aluno?")) return;
    try {
      await apiFetch(`/admin/alunos/${id}`, { method: "DELETE" });
    } catch (err: any) {
      toast(`Erro ao remover aluno: ${err.message}`);
      return;
    }
    setAlunos(alunos.filter((a) => a.id !== id));
    toast("Aluno removido.");
  }

  // Matrícula Direta
  async function handleMatricularAlunoTurma() {
    if (!matrTurmaId || !matrAlunoId) {
      toast("Selecione a turma e o aluno.");
      return;
    }
    try {
      await apiFetch(`/admin/turmas/${matrTurmaId}/matricular/${matrAlunoId}`, { method: "POST" });
      toast("Aluno matriculado com sucesso na turma!");
      carregarDados();
    } catch (err: any) {
      toast(`Erro na matrícula: ${err.message}`);
    }
    setMatrTurmaId(null);
    setMatrAlunoId(null);
  }

  // Alocação / Substituição de Professor
  function abrirModalSubstituicao(turmaId: number, discNome: string, ehSubst: boolean) {
    setSubstTurmaId(turmaId);
    setSubstTurmaNome(discNome);
    setIsSubstituicaoFormal(ehSubst);
    setSubstNovoProfId(null);
    setSubstMotivo("");
    setModalSubstOpen(true);
  }

  async function handleConfirmarSubstituicao(e: React.FormEvent) {
    e.preventDefault();
    if (!substTurmaId || !substNovoProfId) {
      toast("Selecione o novo docente.");
      return;
    }
    if (isSubstituicaoFormal && (!substMotivo || substMotivo.length < 10)) {
      toast("A justificativa de substituição deve ter no mínimo 10 caracteres.");
      return;
    }

    try {
      const resp = await apiFetch<string>("/admin/turmas/alocar-professor", {
        method: "POST",
        body: JSON.stringify({
          turmaId: substTurmaId,
          professorId: substNovoProfId,
          substituicao: isSubstituicaoFormal,
          motivoSubstituicao: substMotivo,
        }),
      });
      toast(typeof resp === "string" ? resp : "Operação concluída com sucesso!");
    } catch (err: any) {
      toast(`Erro ao alocar/substituir professor: ${err.message}`);
    }

    const profNome = professores.find((p) => p.id === Number(substNovoProfId))?.nome || "";
    setDisciplinas(
      disciplinas.map((d) =>
        d.turmaId === substTurmaId ? { ...d, professorId: substNovoProfId, professorNome: profNome } : d
      )
    );

    setModalSubstOpen(false);
  }

  const disciplinasExibidas =
    cursoFiltro === "ALL"
      ? disciplinas
      : disciplinas.filter((d) => d.cursoId === Number(cursoFiltro));

  const alunosExibidos =
    cursoFiltro === "ALL"
      ? alunos
      : alunos.filter((a) => a.cursoId === Number(cursoFiltro));

  return (
    <div className="admin-container">
      {/* Header Institucional de Gestão */}
      <div className="page-header">
        <div>
          <p className="eyebrow">ADMINISTRAÇÃO GERAL DO SISTEMA (UC02 / UC03)</p>
          <h1>Painel de Controle Institucional</h1>
          <p>
            Gestão hierárquica completa de múltiplos cursos, disciplinas com professor único responsável, lista de alunos e controle de substituição docente.
          </p>
        </div>
      </div>

      {/* Tabs Administrativas */}
      <div className="admin-tabs-bar">
        <button className={tab === "cursos" ? "tab-active" : ""} onClick={() => setTab("cursos")}>
          🏛️ 1. Cursos Institucionais ({cursos.length})
        </button>
        <button className={tab === "disciplinas" ? "tab-active" : ""} onClick={() => setTab("disciplinas")}>
          📚 2. Disciplinas & Turmas ({disciplinas.length})
        </button>
        <button className={tab === "alunos" ? "tab-active" : ""} onClick={() => setTab("alunos")}>
          🎓 3. Lista de Alunos ({alunos.length})
        </button>
        <button className={tab === "professores" ? "tab-active" : ""} onClick={() => setTab("professores")}>
          👨‍🏫 4. Alocação e Substituição Docente
        </button>
      </div>

      {/* =========================================================
          ABA 1: GESTÃO DE CURSOS
          ========================================================= */}
      {tab === "cursos" && (
        <div className="admin-section">
          <div className="section-toolbar">
            <div>
              <h3>Cursos Oferecidos na Universidade</h3>
              <p>O SCA permite a administração multi-curso. Adicione ou remova cursos do catálogo.</p>
            </div>
            <button className="btn btn-primary" onClick={() => setModalCursoOpen(true)}>
              + Adicionar Novo Curso
            </button>
          </div>

          <div className="admin-table-card">
            <table className="admin-table">
              <thead>
                <tr>
                  <th>CÓDIGO</th>
                  <th>NOME DO CURSO</th>
                  <th>DEPARTAMENTO</th>
                  <th>TURNO</th>
                  <th>DISCIPLINAS</th>
                  <th>ALUNOS ATIVOS</th>
                  <th style={{ textAlign: "right" }}>AÇÃO</th>
                </tr>
              </thead>
              <tbody>
                {cursos.map((c) => (
                  <tr key={c.id}>
                    <td><b>{c.codigo}</b></td>
                    <td><span className="course-title">{c.nome}</span></td>
                    <td>{c.departamento}</td>
                    <td><span className="badge badge-blue">{c.turno}</span></td>
                    <td><b>{c.qtdDisciplinas}</b> disciplinas</td>
                    <td><b>{c.qtdAlunos}</b> alunos</td>
                    <td style={{ textAlign: "right" }}>
                      <button className="btn-table-action danger" onClick={() => handleRemoverCurso(c.id)}>
                        Remover
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      )}

      {/* =========================================================
          ABA 2: GESTÃO DE DISCIPLINAS POR CURSO
          ========================================================= */}
      {tab === "disciplinas" && (
        <div className="admin-section">
          <div className="section-toolbar">
            <div>
              <h3>Disciplinas Ofertadas por Curso</h3>
              <p>Cada disciplina possui turmas onde atuam <b>exatamente 1 professor responsável</b> e sua lista de alunos.</p>
            </div>
            <div className="toolbar-actions">
              <select
                className="admin-select"
                value={cursoFiltro}
                onChange={(e) => setCursoFiltro(e.target.value === "ALL" ? "ALL" : Number(e.target.value))}
              >
                <option value="ALL">Todos os Cursos</option>
                {cursos.map((c) => (
                  <option key={c.id} value={c.id}>{c.nome}</option>
                ))}
              </select>
              <button className="btn btn-primary" onClick={() => setModalDiscOpen(true)}>
                + Adicionar Disciplina
              </button>
            </div>
          </div>

          <div className="admin-table-card">
            <table className="admin-table">
              <thead>
                <tr>
                  <th>CÓDIGO</th>
                  <th>DISCIPLINA</th>
                  <th>CURSO</th>
                  <th>CARGA</th>
                  <th>TIPO</th>
                  <th>PROFESSOR RESPONSÁVEL (ÚNICO)</th>
                  <th>ALUNOS MATRICULADOS</th>
                  <th style={{ textAlign: "right" }}>AÇÃO</th>
                </tr>
              </thead>
              <tbody>
                {disciplinasExibidas.map((d) => (
                  <tr key={d.id}>
                    <td><b>{d.codigo}</b></td>
                    <td>
                      <b>{d.nome}</b>
                      {d.ementa && <small className="table-sub">{d.ementa.slice(0, 45)}...</small>}
                    </td>
                    <td><span className="badge badge-gray">{d.nomeCurso || "Sem curso"}</span></td>
                    <td>{d.cargaHoraria}h</td>
                    <td>
                      <span className={`badge ${d.tipo === "OBRIGATORIA" ? "badge-blue" : "badge-amber"}`}>
                        {d.tipo}
                      </span>
                    </td>
                    <td>
                      <div className="prof-cell">
                        <b>{d.professorNome || "A definir"}</b>
                        <button
                          className="btn-link"
                          onClick={() => abrirModalSubstituicao(d.turmaId || 1, d.nome, true)}
                        >
                          Substituir docente
                        </button>
                      </div>
                    </td>
                    <td><b>{d.qtdAlunosMatriculados}</b> matriculados</td>
                    <td style={{ textAlign: "right" }}>
                      <button className="btn-table-action danger" onClick={() => handleRemoverDisciplina(d.id)}>
                        Remover
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      )}

      {/* =========================================================
          ABA 3: GESTÃO DE ALUNOS
          ========================================================= */}
      {tab === "alunos" && (
        <div className="admin-section">
          <div className="section-toolbar">
            <div>
              <h3>Controle e Cadastro de Alunos</h3>
              <p>Gestão discente com matrícula institucional e vinculação em turmas.</p>
            </div>
            <div className="toolbar-actions">
              <select
                className="admin-select"
                value={cursoFiltro}
                onChange={(e) => setCursoFiltro(e.target.value === "ALL" ? "ALL" : Number(e.target.value))}
              >
                <option value="ALL">Todos os Cursos</option>
                {cursos.map((c) => (
                  <option key={c.id} value={c.id}>{c.nome}</option>
                ))}
              </select>
              <button className="btn btn-primary" onClick={() => setModalAlunoOpen(true)}>
                + Cadastrar Novo Aluno
              </button>
            </div>
          </div>

          <div className="admin-table-card">
            <table className="admin-table">
              <thead>
                <tr>
                  <th>MATRÍCULA</th>
                  <th>NOME COMPLETO</th>
                  <th>E-MAIL INSTITUCIONAL</th>
                  <th>CURSO</th>
                  <th>SEMESTRE</th>
                  <th>CR</th>
                  <th>INTEGRALIZAÇÃO</th>
                  <th style={{ textAlign: "right" }}>AÇÃO</th>
                </tr>
              </thead>
              <tbody>
                {alunosExibidos.map((a) => (
                  <tr key={a.id}>
                    <td><b>{a.matricula}</b></td>
                    <td><b>{a.nome}</b></td>
                    <td>{a.email}</td>
                    <td><span className="badge badge-blue">{a.nomeCurso || "Ciência da Computação"}</span></td>
                    <td>{a.semestreAtual || "1º período"}</td>
                    <td><b>{a.coeficienteRendimento ? a.coeficienteRendimento.toFixed(2) : "0,00"}</b></td>
                    <td>{a.percentualIntegralizacao}%</td>
                    <td style={{ textAlign: "right" }}>
                      <button className="btn-table-action danger" onClick={() => handleRemoverAluno(a.id)}>
                        Remover
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>

          {/* Matrícula Direta em Turma */}
          <div className="admin-card-box">
            <h4>Matrícula Direta de Aluno em Disciplina / Turma (UC14)</h4>
            <p>O Administrador Geral pode associar diretamente um discente a uma disciplina específica:</p>
            <div className="direct-enroll-form">
              <select className="admin-select" value={matrAlunoId || ""} onChange={(e) => setMatrAlunoId(Number(e.target.value))}>
                <option value="">Selecione o Aluno...</option>
                {alunos.map((a) => (
                  <option key={a.id} value={a.id}>{a.matricula} - {a.nome}</option>
                ))}
              </select>

              <select className="admin-select" value={matrTurmaId || ""} onChange={(e) => setMatrTurmaId(Number(e.target.value))}>
                <option value="">Selecione a Disciplina / Turma...</option>
                {disciplinas.map((d) => (
                  <option key={d.id} value={d.turmaId || d.id}>{d.codigo} - {d.nome} (Prof. {d.professorNome})</option>
                ))}
              </select>

              <button className="btn btn-primary" onClick={handleMatricularAlunoTurma}>
                Matricular Aluno
              </button>
            </div>
          </div>
        </div>
      )}

      {/* =========================================================
          ABA 4: ALOCAÇÃO E SUBSTITUIÇÃO DE PROFESSORES (REGRA DE NEGÓCIO)
          ========================================================= */}
      {tab === "professores" && (
        <div className="admin-section">
          {/* Banner de Regra de Negócio */}
          <div className="rule-alert-box">
            <div className="rule-icon">🚨</div>
            <div>
              <b>Regra Institucional de Alocação e Substituição de Professores:</b>
              <p>
                1. <b>Adição em Início de Período:</b> A alocação ordinária de professores para ministrar turmas é permitida apenas nas janelas de abertura/ajuste de período.<br/>
                2. <b>Substituição Excepcional:</b> Durante o semestre letivo, caso o professor não possa mais ministrar a disciplina (licença médica, afastamento formal, etc.), o sistema exige <b>justificativa formal obrigatória</b> e registra a alteração imutável na trilha de auditoria.
              </p>
            </div>
          </div>

          <div className="admin-table-card">
            <table className="admin-table">
              <thead>
                <tr>
                  <th>TURMA</th>
                  <th>DISCIPLINA</th>
                  <th>DEPARTAMENTO</th>
                  <th>DOCENTE RESPONSÁVEL ATUAL</th>
                  <th>STATUS DO PERÍODO</th>
                  <th style={{ textAlign: "right" }}>OPERAÇÕES DOCENTES</th>
                </tr>
              </thead>
              <tbody>
                {disciplinas.map((d) => (
                  <tr key={d.id}>
                    <td><b>T01</b></td>
                    <td><b>{d.nome}</b> ({d.codigo})</td>
                    <td>{d.departamento}</td>
                    <td>
                      <span className="prof-tag">👨‍🏫 {d.professorNome || "Docente não alocado"}</span>
                    </td>
                    <td><span className="badge badge-green">INÍCIO DO PERÍODO 2025.1</span></td>
                    <td style={{ textAlign: "right" }}>
                      <div className="action-buttons-group">
                        <button
                          className="btn-table-action"
                          onClick={() => abrirModalSubstituicao(d.turmaId || 1, d.nome, false)}
                        >
                          Alocar Docente
                        </button>
                        <button
                          className="btn-table-action warning"
                          onClick={() => abrirModalSubstituicao(d.turmaId || 1, d.nome, true)}
                        >
                          Substituir Docente
                        </button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      )}

      {/* =========================================================
          MODAIS DE CADASTRO
          ========================================================= */}

      {/* Modal Criar Curso */}
      {modalCursoOpen && (
        <div className="login-modal-overlay" onClick={() => setModalCursoOpen(false)}>
          <div className="login-modal-card" onClick={(e) => e.stopPropagation()}>
            <div className="modal-header">
              <h3>Adicionar Novo Curso Institucional</h3>
              <button onClick={() => setModalCursoOpen(false)}>✕</button>
            </div>
            <form onSubmit={handleSalvarCurso} className="modal-form">
              <div className="input-field">
                <label>Código do Curso</label>
                <input
                  type="text"
                  placeholder="Ex: CC.01 ou ODONT.04"
                  value={formCurso.codigo}
                  onChange={(e) => setFormCurso({ ...formCurso, codigo: e.target.value })}
                  required
                />
              </div>
              <div className="input-field">
                <label>Nome do Curso</label>
                <input
                  type="text"
                  placeholder="Ex: Ciência da Computação"
                  value={formCurso.nome}
                  onChange={(e) => setFormCurso({ ...formCurso, nome: e.target.value })}
                  required
                />
              </div>
              <div className="input-field">
                <label>Departamento Responsável</label>
                <input
                  type="text"
                  value={formCurso.departamento}
                  onChange={(e) => setFormCurso({ ...formCurso, departamento: e.target.value })}
                  required
                />
              </div>
              <div className="input-field">
                <label>Turno</label>
                <select
                  className="admin-select"
                  value={formCurso.turno}
                  onChange={(e: any) => setFormCurso({ ...formCurso, turno: e.target.value })}
                >
                  <option value="INTEGRAL">Integral</option>
                  <option value="MANHA">Manhã</option>
                  <option value="TARDE">Tarde</option>
                  <option value="NOITE">Noite</option>
                </select>
              </div>
              <div className="modal-actions">
                <button type="button" className="btn-cancel" onClick={() => setModalCursoOpen(false)}>Cancelar</button>
                <button type="submit" className="btn-confirm">Criar Curso</button>
              </div>
            </form>
          </div>
        </div>
      )}

      {/* Modal Criar Disciplina */}
      {modalDiscOpen && (
        <div className="login-modal-overlay" onClick={() => setModalDiscOpen(false)}>
          <div className="login-modal-card" onClick={(e) => e.stopPropagation()}>
            <div className="modal-header">
              <h3>Adicionar Disciplina ao Curso</h3>
              <button onClick={() => setModalDiscOpen(false)}>✕</button>
            </div>
            <form onSubmit={handleSalvarDisciplina} className="modal-form">
              <div className="input-field">
                <label>Curso Pertencente</label>
                <select
                  className="admin-select"
                  value={formDisc.cursoId}
                  onChange={(e) => setFormDisc({ ...formDisc, cursoId: Number(e.target.value) })}
                  required
                >
                  <option value="">Selecione o Curso...</option>
                  {cursos.map((c) => (
                    <option key={c.id} value={c.id}>{c.codigo} - {c.nome}</option>
                  ))}
                </select>
              </div>
              <div className="input-field">
                <label>Código da Disciplina</label>
                <input
                  type="text"
                  placeholder="Ex: COMP.142"
                  value={formDisc.codigo}
                  onChange={(e) => setFormDisc({ ...formDisc, codigo: e.target.value })}
                  required
                />
              </div>
              <div className="input-field">
                <label>Nome da Disciplina</label>
                <input
                  type="text"
                  placeholder="Ex: Inteligência Artificial"
                  value={formDisc.nome}
                  onChange={(e) => setFormDisc({ ...formDisc, nome: e.target.value })}
                  required
                />
              </div>
              <div className="input-field">
                <label>Professor Responsável Único (Alocação)</label>
                <select
                  className="admin-select"
                  value={formDisc.professorId}
                  onChange={(e) => setFormDisc({ ...formDisc, professorId: Number(e.target.value) })}
                >
                  <option value="">Selecione o Professor...</option>
                  {professores.map((p) => (
                    <option key={p.id} value={p.id}>{p.nome} ({p.siape})</option>
                  ))}
                </select>
              </div>
              <div className="modal-actions">
                <button type="button" className="btn-cancel" onClick={() => setModalDiscOpen(false)}>Cancelar</button>
                <button type="submit" className="btn-confirm">Criar Disciplina</button>
              </div>
            </form>
          </div>
        </div>
      )}

      {/* Modal Criar Aluno */}
      {modalAlunoOpen && (
        <div className="login-modal-overlay" onClick={() => setModalAlunoOpen(false)}>
          <div className="login-modal-card" onClick={(e) => e.stopPropagation()}>
            <div className="modal-header">
              <h3>Cadastrar Novo Aluno</h3>
              <button onClick={() => setModalAlunoOpen(false)}>✕</button>
            </div>
            <form onSubmit={handleSalvarAluno} className="modal-form">
              <div className="input-field">
                <label>Nome Completo</label>
                <input
                  type="text"
                  placeholder="Ex: Lucas Ferreira Silva"
                  value={formAluno.nome}
                  onChange={(e) => setFormAluno({ ...formAluno, nome: e.target.value })}
                  required
                />
              </div>
              <div className="input-field">
                <label>Matrícula Institucional</label>
                <input
                  type="text"
                  placeholder="Ex: 2025101099"
                  value={formAluno.matricula}
                  onChange={(e) => setFormAluno({ ...formAluno, matricula: e.target.value })}
                  required
                />
              </div>
              <div className="input-field">
                <label>Curso</label>
                <select
                  className="admin-select"
                  value={formAluno.cursoId}
                  onChange={(e) => setFormAluno({ ...formAluno, cursoId: Number(e.target.value) })}
                  required
                >
                  <option value="">Selecione o Curso...</option>
                  {cursos.map((c) => (
                    <option key={c.id} value={c.id}>{c.nome}</option>
                  ))}
                </select>
              </div>
              <div className="input-field">
                <label>CPF</label>
                <input
                  type="text"
                  placeholder="000.000.000-00"
                  value={formAluno.cpf}
                  onChange={(e) => setFormAluno({ ...formAluno, cpf: e.target.value })}
                />
              </div>
              <div className="modal-actions">
                <button type="button" className="btn-cancel" onClick={() => setModalAlunoOpen(false)}>Cancelar</button>
                <button type="submit" className="btn-confirm">Cadastrar Aluno</button>
              </div>
            </form>
          </div>
        </div>
      )}

      {/* Modal Substituição de Docente com Justificativa */}
      {modalSubstOpen && (
        <div className="login-modal-overlay" onClick={() => setModalSubstOpen(false)}>
          <div className="login-modal-card" onClick={(e) => e.stopPropagation()}>
            <div className="modal-header">
              <h3>
                {isSubstituicaoFormal
                  ? "Substituição Excepcional de Docente (Auditoria)"
                  : "Alocação Ordinária de Docente (Início de Período)"}
              </h3>
              <button onClick={() => setModalSubstOpen(false)}>✕</button>
            </div>
            <form onSubmit={handleConfirmarSubstituicao} className="modal-form">
              <p>
                Turma: <b>{substTurmaNome}</b>
              </p>
              <div className="input-field">
                <label>Novo Professor Responsável</label>
                <select
                  className="admin-select"
                  value={substNovoProfId || ""}
                  onChange={(e) => setSubstNovoProfId(Number(e.target.value))}
                  required
                >
                  <option value="">Selecione o Professor Substituto...</option>
                  {professores.map((p) => (
                    <option key={p.id} value={p.id}>{p.nome} (SIAPE: {p.siape} - {p.departamento})</option>
                  ))}
                </select>
              </div>

              {isSubstituicaoFormal && (
                <div className="input-field">
                  <label>Justificativa Formal Obrigatória (Mínimo 10 caracteres)</label>
                  <textarea
                    rows={3}
                    placeholder="Ex: Substituição por licença médica do docente titular autorizada pela Portaria PROEG 018/2025."
                    value={substMotivo}
                    onChange={(e) => setSubstMotivo(e.target.value)}
                    required
                  />
                </div>
              )}

              <div className="modal-actions">
                <button type="button" className="btn-cancel" onClick={() => setModalSubstOpen(false)}>Cancelar</button>
                <button type="submit" className="btn-confirm">
                  {isSubstituicaoFormal ? "Registrar Substituição" : "Alocar Docente"}
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
}









