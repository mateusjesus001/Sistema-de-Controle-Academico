import { useMemo, useState, useEffect, type ReactNode } from "react";
import { AuthProvider, useAuth } from "./context/AuthContext";
import { LoginPage } from "./components/LoginPage";
import { AdminPanel } from "./components/AdminPanel";
import type { PerfilUsuario } from "./types/auth";

type IconName =
  | "home"
  | "grid"
  | "calendar"
  | "book"
  | "users"
  | "folder"
  | "bell"
  | "search"
  | "chevron"
  | "arrow"
  | "clock"
  | "map"
  | "lock"
  | "check"
  | "alert"
  | "plus"
  | "download"
  | "flag"
  | "thumb"
  | "file"
  | "x"
  | "menu"
  | "more"
  | "filter"
  | "upload"
  | "eye"
  | "user";

const paths: Record<IconName, ReactNode> = {
  home: (
    <>
      <path d="m3 10 9-7 9 7" />
      <path d="M5 9v11h14V9" />
      <path d="M9 20v-7h6v7" />
    </>
  ),
  grid: (
    <>
      <rect x="3" y="3" width="7" height="7" rx="1" />
      <rect x="14" y="3" width="7" height="7" rx="1" />
      <rect x="3" y="14" width="7" height="7" rx="1" />
      <rect x="14" y="14" width="7" height="7" rx="1" />
    </>
  ),
  calendar: (
    <>
      <rect x="3" y="5" width="18" height="16" rx="2" />
      <path d="M16 3v4M8 3v4M3 10h18" />
    </>
  ),
  book: (
    <>
      <path d="M4 19.5A2.5 2.5 0 0 1 6.5 17H20V4H6.5A2.5 2.5 0 0 0 4 6.5z" />
      <path d="M4 6.5v13M8 8h8" />
    </>
  ),
  users: (
    <>
      <path d="M16 21v-2a4 4 0 0 0-4-4H6a4 4 0 0 0-4 4v2" />
      <circle cx="9" cy="7" r="4" />
      <path d="M22 21v-2a4 4 0 0 0-3-3.87M16 3.13a4 4 0 0 1 0 7.75" />
    </>
  ),
  folder: (
    <>
      <path d="M3 6h7l2 2h9v11H3z" />
    </>
  ),
  bell: (
    <>
      <path d="M18 8a6 6 0 0 0-12 0c0 7-3 7-3 9h18c0-2-3-2-3-9" />
      <path d="M10 21h4" />
    </>
  ),
  search: (
    <>
      <circle cx="11" cy="11" r="7" />
      <path d="m20 20-4-4" />
    </>
  ),
  chevron: <path d="m9 18 6-6-6-6" />,
  arrow: (
    <>
      <path d="M5 12h14" />
      <path d="m13 6 6 6-6 6" />
    </>
  ),
  clock: (
    <>
      <circle cx="12" cy="12" r="9" />
      <path d="M12 7v5l3 2" />
    </>
  ),
  map: (
    <>
      <path d="M20 10c0 5-8 11-8 11S4 15 4 10a8 8 0 1 1 16 0Z" />
      <circle cx="12" cy="10" r="2.5" />
    </>
  ),
  lock: (
    <>
      <rect x="4" y="10" width="16" height="11" rx="2" />
      <path d="M8 10V7a4 4 0 0 1 8 0v3" />
    </>
  ),
  check: <path d="m5 12 4 4L19 6" />,
  alert: (
    <>
      <path d="M12 3 2 21h20Z" />
      <path d="M12 9v5M12 18h.01" />
    </>
  ),
  plus: <path d="M12 5v14M5 12h14" />,
  download: (
    <>
      <path d="M12 3v12" />
      <path d="m7 10 5 5 5-5M5 21h14" />
    </>
  ),
  flag: (
    <>
      <path d="M5 22V3" />
      <path d="M5 4h12l-2 4 2 4H5" />
    </>
  ),
  thumb: (
    <>
      <path d="M7 10v11H3V10zM7 19c3 2 7 2 11 1l2-8c.3-1.3-.7-2-2-2h-5l1-5c.2-1-1-2-2-1l-5 6" />
    </>
  ),
  file: (
    <>
      <path d="M6 2h8l4 4v16H6z" />
      <path d="M14 2v5h5M9 13h6M9 17h6" />
    </>
  ),
  x: <path d="m6 6 12 12M18 6 6 18" />,
  menu: <path d="M4 7h16M4 12h16M4 17h16" />,
  more: (
    <>
      <circle cx="5" cy="12" r="1" />
      <circle cx="12" cy="12" r="1" />
      <circle cx="19" cy="12" r="1" />
    </>
  ),
  filter: <path d="M4 5h16l-6 7v6l-4 2v-8z" />,
  upload: (
    <>
      <path d="M12 16V4m0 0L7 9m5-5 5 5" />
      <path d="M5 14v6h14v-6" />
    </>
  ),
  eye: (
    <>
      <path d="M2 12s4-7 10-7 10 7 10 7-4 7-10 7S2 12 2 12Z" />
      <circle cx="12" cy="12" r="3" />
    </>
  ),
  user: (
    <>
      <circle cx="12" cy="8" r="4" />
      <path d="M4 21a8 8 0 0 1 16 0" />
    </>
  ),
};

function Icon({ name, size = 18 }: { name: IconName; size?: number }) {
  return (
    <svg
      width={size}
      height={size}
      viewBox="0 0 24 24"
      fill="none"
      stroke="currentColor"
      strokeWidth="1.8"
      strokeLinecap="round"
      strokeLinejoin="round"
      aria-hidden
    >
      {paths[name]}
    </svg>
  );
}

type Page = "dashboard" | "curriculum" | "enrollment" | "diary" | "monitoring" | "library" | "admin";

interface NavItem {
  id: Page;
  label: string;
  icon: IconName;
}

function getNavItemsForProfile(perfil?: PerfilUsuario): NavItem[] {
  switch (perfil) {
    case "ADMIN":
      return [
        { id: "admin", label: "Painel Institucional", icon: "folder" },
        { id: "library", label: "Acervo & Moderação", icon: "file" },
      ];
    case "PROFESSOR":
      return [
        { id: "diary", label: "Diário de Classe", icon: "book" },
        { id: "dashboard", label: "Visão Docente", icon: "home" },
        { id: "library", label: "Acervo de Materiais", icon: "folder" },
      ];
    case "MONITOR":
      return [
        { id: "monitoring", label: "Gestão de Plantões", icon: "users" },
        { id: "dashboard", label: "Minha Vida Discente", icon: "home" },
        { id: "curriculum", label: "Grade Curricular", icon: "grid" },
        { id: "library", label: "Acervo Acadêmico", icon: "folder" },
      ];
    case "ALUNO":
    default:
      return [
        { id: "dashboard", label: "Visão Geral", icon: "home" },
        { id: "curriculum", label: "Grade Curricular", icon: "grid" },
        { id: "enrollment", label: "Matrícula no Período", icon: "calendar" },
        { id: "monitoring", label: "Plantões de Monitoria", icon: "users" },
        { id: "library", label: "Acervo Acadêmico", icon: "folder" },
      ];
  }
}

function Badge({
  tone = "gray",
  children,
  pulse = false,
}: {
  tone?: string;
  children: ReactNode;
  pulse?: boolean;
}) {
  return (
    <span className={`badge badge-${tone} ${pulse ? "pulse" : ""}`}>
      <span className="badge-dot" />
      {children}
    </span>
  );
}

function Button({
  children,
  variant = "primary",
  icon,
  disabled,
  onClick,
  small = false,
}: {
  children: ReactNode;
  variant?: "primary" | "secondary" | "danger" | "warning" | "ghost";
  icon?: IconName;
  disabled?: boolean;
  onClick?: () => void;
  small?: boolean;
}) {
  return (
    <button
      className={`btn btn-${variant} ${small ? "btn-small" : ""}`}
      disabled={disabled}
      onClick={onClick}
    >
      {icon && <Icon name={icon} size={small ? 15 : 17} />} {children}
    </button>
  );
}

function Progress({ value, color = "blue" }: { value: number; color?: string }) {
  return (
    <div className="progress">
      <span style={{ width: `${value}%` }} className={`progress-${color}`} />
    </div>
  );
}

function PageHeader({
  eyebrow,
  title,
  description,
  actions,
}: {
  eyebrow: string;
  title: string;
  description: string;
  actions?: ReactNode;
}) {
  return (
    <div className="page-header">
      <div>
        <p className="eyebrow">{eyebrow}</p>
        <h1>{title}</h1>
        <p>{description}</p>
      </div>
      {actions && <div className="header-actions">{actions}</div>}
    </div>
  );
}

function Dashboard({ go }: { go: (page: Page) => void }) {
  const { usuario } = useAuth();
  const [requestOpen, setRequestOpen] = useState(false);
  const initials = usuario?.nome
    ? usuario.nome
        .split(" ")
        .map((x) => x[0])
        .join("")
        .slice(0, 2)
        .toUpperCase()
    : "MC";
  const primeiroNome = usuario?.nome ? usuario.nome.split(" ")[0] : "Mariana";
  const courses = [
    {
      code: "COMP.142",
      name: "Estruturas de Dados",
      professor: "Profa. Helena Moura",
      time: "SEG • 08:00–10:00",
      grade: "8,7",
      freq: 92,
      tone: "blue",
    },
    {
      code: "COMP.151",
      name: "Banco de Dados II",
      professor: "Prof. Lucas Nóbrega",
      time: "TER • 10:00–12:00",
      grade: "9,1",
      freq: 88,
      tone: "violet",
    },
    {
      code: "MAT.108",
      name: "Cálculo Numérico",
      professor: "Profa. Lívia Porto",
      time: "QUA • 14:00–16:00",
      grade: "7,8",
      freq: 76,
      tone: "orange",
    },
    {
      code: "COMP.163",
      name: "Engenharia de Software",
      professor: "Prof. Caio Freire",
      time: "QUI • 08:00–10:00",
      grade: "—",
      freq: 96,
      tone: "emerald",
    },
  ];
  return (
    <>
      <PageHeader
        eyebrow="PERÍODO 2025.1"
        title={`Bom dia, ${primeiroNome}.`}
        description="Aqui está o panorama da sua jornada acadêmica neste período."
        actions={
          <>
            <Button variant="secondary" icon="file" onClick={() => setRequestOpen(true)}>
              Novo requerimento
            </Button>
            <Button variant="secondary" icon="download">
              Comprovante
            </Button>
          </>
        }
      />
      <section className="student-strip">
        <div className="student-avatar">{initials}</div>
        <div>
          <b>{usuario?.nome || "Mariana Costa"}</b>
          <span>{usuario?.curso || "Ciência da Computação"} • {usuario?.semestreAtual || "5º período"}</span>
        </div>
        <div className="student-meta">
          <span>{usuario?.perfil === "PROFESSOR" ? "SIAPE" : "MATRÍCULA"}</span>
          <b>{usuario?.identificador || "2023101001"}</b>
        </div>
        <div className="student-meta">
          <span>STATUS</span>
          <Badge tone="green">REGULAR</Badge>
        </div>
      </section>
      <div className="kpi-grid">
        <article className="kpi-card">
          <div className="kpi-top">
            <span className="icon-box blue">
              <Icon name="book" />
            </span>
            <span className="trend">+0,32 este período</span>
          </div>
          <div className="kpi-content">
            <div>
              <span>Coeficiente de rendimento</span>
              <strong>{usuario?.coeficienteRendimento ? usuario.coeficienteRendimento.toFixed(2).replace(".", ",") : "8,45"}</strong>
              <small>Acima da média do curso (7,62)</small>
            </div>
            <div className="radial" style={{ "--p": `${(usuario?.coeficienteRendimento || 8.45) * 10}` } as React.CSSProperties}>
              <span>{Math.round((usuario?.coeficienteRendimento || 8.45) * 10)}%</span>
            </div>
          </div>
        </article>
        <article className="kpi-card">
          <div className="kpi-top">
            <span className="icon-box emerald">
              <Icon name="grid" />
            </span>
            <button onClick={() => go("curriculum")}>
              Ver matriz <Icon name="arrow" size={14} />
            </button>
          </div>
          <span>Integralização curricular</span>
          <strong>{usuario?.percentualIntegralizacao || 68}%</strong>
          <Progress value={usuario?.percentualIntegralizacao || 68} color="green" />
          <small>{usuario?.horasConcluidas || 2176}h de {usuario?.horasTotais || 3200}h concluídas</small>
        </article>
        <article className="kpi-card">
          <div className="kpi-top">
            <span className="icon-box violet">
              <Icon name="calendar" />
            </span>
            <button onClick={() => go("diary")}>
              Ver diários <Icon name="arrow" size={14} />
            </button>
          </div>
          <span>Disciplinas neste período</span>
          <strong>
            5 <em>turmas ativas</em>
          </strong>
          <div className="mini-avatars">
            <i>ED</i>
            <i>BD</i>
            <i>CN</i>
            <i>ES</i>
            <i>+1</i>
          </div>
        </article>
      </div>
      <div className="dashboard-grid">
        <section className="card course-panel">
          <div className="section-head">
            <div>
              <h2>Turmas ativas</h2>
              <p>Acompanhe frequência e notas parciais</p>
            </div>
            <button onClick={() => go("enrollment")}>
              Ver todas <Icon name="arrow" size={14} />
            </button>
          </div>
          <div className="course-list">
            {courses.map((c) => (
              <div className="course-row" key={c.code}>
                <span className={`course-mark ${c.tone}`}>
                  {c.name
                    .split(" ")
                    .map((x) => x[0])
                    .join("")
                    .slice(0, 2)}
                </span>
                <div className="course-name">
                  <b>{c.name}</b>
                  <span>
                    {c.code} • {c.professor}
                  </span>
                </div>
                <div className="course-time">
                  <Icon name="clock" size={15} />
                  <span>{c.time}</span>
                </div>
                <div className="course-stat">
                  <span>
                    Frequência <b className={c.freq < 80 ? "warn-text" : ""}>{c.freq}%</b>
                  </span>
                  <Progress value={c.freq} color={c.freq < 80 ? "orange" : "green"} />
                </div>
                <div className="grade">
                  <span>Nota parcial</span>
                  <b>{c.grade}</b>
                </div>
                <Icon name="chevron" size={16} />
              </div>
            ))}
          </div>
        </section>
        <aside className="card deadlines">
          <div className="section-head">
            <div>
              <h2>Próximos prazos</h2>
              <p>Não perca nenhuma data</p>
            </div>
            <button>
              <Icon name="more" />
            </button>
          </div>
          <div className="date-item urgent">
            <div>
              <b>18</b>
              <span>JUN</span>
            </div>
            <section>
              <Badge tone="red">EM 2 DIAS</Badge>
              <b>Trancamento parcial</b>
              <span>Último dia para solicitar</span>
            </section>
          </div>
          <div className="date-item">
            <div>
              <b>21</b>
              <span>JUN</span>
            </div>
            <section>
              <Badge tone="blue">AVALIAÇÃO</Badge>
              <b>Prova de Banco de Dados</b>
              <span>10:00 • Sala C-204</span>
            </section>
          </div>
          <div className="date-item">
            <div>
              <b>25</b>
              <span>JUN</span>
            </div>
            <section>
              <Badge tone="gray">ATIVIDADE</Badge>
              <b>Projeto de Engenharia</b>
              <span>Entrega até 23:59</span>
            </section>
          </div>
          <button className="calendar-link">
            Abrir calendário acadêmico <Icon name="arrow" size={14} />
          </button>
        </aside>
      </div>
      <section className="support-banner">
        <span className="icon-box amber">
          <Icon name="users" />
        </span>
        <div>
          <b>Plantão disponível hoje</b>
          <p>Banco de Dados II com Rafael • 17:30–19:00 • Lab 03</p>
        </div>
        <Button variant="secondary" small onClick={() => go("monitoring")}>
          Agendar dúvida
        </Button>
      </section>
      {requestOpen && (
        <Modal title="Novo requerimento acadêmico" icon="file" close={() => setRequestOpen(false)}>
          <div className="form-grid">
            <label className="field-label full">
              TIPO DE REQUERIMENTO
              <select>
                <option>Trancamento parcial de disciplina</option>
                <option>Aproveitamento de estudos</option>
                <option>Revisão de nota</option>
              </select>
            </label>
            <label className="field-label full">
              JUSTIFICATIVA
              <textarea placeholder="Descreva o motivo da solicitação..." />
            </label>
          </div>
          <button className="dropzone">
            <Icon name="upload" />
            <b>Arraste seus comprovantes aqui</b>
            <span>ou clique para selecionar arquivos PDF de até 10 MB</span>
          </button>
          <div className="modal-note">
            <Icon name="alert" />
            <span>
              A solicitação será encaminhada à Secretaria Acadêmica e poderá levar até 5 dias úteis.
            </span>
          </div>
          <div className="modal-actions">
            <Button variant="secondary" onClick={() => setRequestOpen(false)}>
              Cancelar
            </Button>
            <Button onClick={() => setRequestOpen(false)}>Protocolar requerimento</Button>
          </div>
        </Modal>
      )}
    </>
  );
}

type SubjectState = "done" | "active" | "available" | "locked" | "simulated";
const semesters = [
  [
    { c: "CC0101", n: "Introdução à Computação", h: "60h", s: "done" },
    { c: "MAT0101", n: "Cálculo Diferencial I", h: "60h", s: "done" },
    { c: "CC0102", n: "Lógica de Programação", h: "60h", s: "done" },
  ],
  [
    { c: "CC0201", n: "Programação I", h: "60h", s: "done" },
    { c: "MAT0201", n: "Álgebra Linear", h: "60h", s: "done" },
    { c: "CC0203", n: "Matemática Discreta", h: "60h", s: "done" },
  ],
  [
    { c: "CC0301", n: "Estruturas de Dados", h: "60h", s: "active" },
    { c: "CC0302", n: "Banco de Dados I", h: "60h", s: "active" },
    { c: "MAT0301", n: "Probabilidade", h: "60h", s: "done" },
  ],
  [
    { c: "CC0401", n: "Projeto de Software", h: "60h", s: "available" },
    { c: "CC0402", n: "Redes de Computadores", h: "60h", s: "available" },
    { c: "CC0403", n: "Teoria da Computação", h: "60h", s: "locked" },
  ],
  [
    { c: "CC0501", n: "Inteligência Artificial", h: "60h", s: "locked" },
    { c: "CC0502", n: "Sistemas Distribuídos", h: "60h", s: "locked" },
    { c: "OPT501", n: "Optativa I", h: "60h", s: "available" },
  ],
  [
    { c: "CC0601", n: "Compiladores", h: "60h", s: "locked" },
    { c: "CC0602", n: "Projeto Integrador", h: "90h", s: "locked" },
    { c: "OPT601", n: "Optativa II", h: "60h", s: "locked" },
  ],
  [
    { c: "CC0701", n: "TCC I", h: "60h", s: "locked" },
    { c: "OPT701", n: "Optativa III", h: "60h", s: "locked" },
  ],
  [
    { c: "CC0801", n: "TCC II", h: "120h", s: "locked" },
    { c: "EST801", n: "Estágio Supervisionado", h: "180h", s: "locked" },
  ],
] as { c: string; n: string; h: string; s: SubjectState }[][];

function Curriculum() {
  const [simulation, setSimulation] = useState(false);
  const [selected, setSelected] = useState<string[]>([]);
  const [focus, setFocus] = useState("CC0401");
  function pick(code: string, state: SubjectState) {
    setFocus(code);
    if (simulation && (state === "locked" || state === "available"))
      setSelected((x) => (x.includes(code) ? x.filter((v) => v !== code) : [...x, code]));
  }
  return (
    <>
      <PageHeader
        eyebrow="MATRIZ 2023.1 • VERSÃO 4"
        title="Grade curricular"
        description="Explore sua trajetória e simule os próximos períodos."
        actions={
          <>
            <div className="sim-toggle">
              <div>
                <b>Modo simulação</b>
                <span>Planejamento sem alterar seu histórico</span>
              </div>
              <button
                className={simulation ? "on" : ""}
                onClick={() => {
                  setSimulation(!simulation);
                  setSelected([]);
                }}
              >
                <i />
              </button>
            </div>
            <Button variant="secondary" icon="download">
              Exportar grade
            </Button>
          </>
        }
      />
      <div className="curriculum-summary card">
        <div>
          <span>PROGRESSO GERAL</span>
          <b>68%</b>
          <Progress value={68} />
        </div>
        <div>
          <span>OBRIGATÓRIAS</span>
          <b>
            32 <small>de 46</small>
          </b>
        </div>
        <div>
          <span>OPTATIVAS</span>
          <b>
            4 <small>de 8</small>
          </b>
        </div>
        <div>
          <span>CARGA HORÁRIA</span>
          <b>
            2.176h <small>/ 3.200h</small>
          </b>
        </div>
        <div className="legend">
          <i className="done" />
          Concluída <i className="active" />
          Em curso <i className="available" />
          Disponível <i className="locked" />
          Bloqueada
        </div>
      </div>
      {simulation && (
        <div className="simulation-note">
          <Icon name="alert" />
          <div>
            <b>Simulação ativa</b>
            <span>Selecione disciplinas futuras para visualizar o impacto na sua trajetória.</span>
          </div>
          <Badge tone="blue">{selected.length} SELECIONADAS</Badge>
        </div>
      )}
      <div className="curriculum-wrap">
        <div className="semester-grid">
          {semesters.map((sem, i) => (
            <section className="semester" key={i}>
              <header>
                <b>{i + 1}º período</b>
                <span>{sem.reduce((a, x) => a + Number(x.h.replace("h", "")), 0)}h</span>
              </header>
              {sem.map((sub) => {
                const simulated = selected.includes(sub.c);
                return (
                  <button
                    key={sub.c}
                    onClick={() => pick(sub.c, sub.s)}
                    className={`subject ${simulated ? "simulated" : sub.s} ${focus === sub.c ? "focused" : ""}`}
                  >
                    <div>
                      <span>{sub.c}</span>
                      {sub.s === "locked" && !simulated ? (
                        <Icon name="lock" size={14} />
                      ) : (
                        <Icon name="check" size={14} />
                      )}
                    </div>
                    <b>{sub.n}</b>
                    <small>
                      {sub.h} • {sub.c.startsWith("OPT") ? "OPTATIVA" : "OBRIGATÓRIA"}
                    </small>
                  </button>
                );
              })}
            </section>
          ))}
        </div>
      </div>
      <div className="prereq-info card">
        <span className="icon-box blue">
          <Icon name="grid" />
        </span>
        <div>
          <span>DISCIPLINA SELECIONADA</span>
          <b>Projeto de Software</b>
          <small>Pré-requisito: Estruturas de Dados • Libera 3 disciplinas futuras</small>
        </div>
        <div className="tree-dots">
          <i>ED</i>
          <Icon name="arrow" />
          <i className="current">PS</i>
          <Icon name="arrow" />
          <i>PI</i>
          <i>ES</i>
          <i>TC</i>
        </div>
      </div>
    </>
  );
}

const offered = [
  {
    code: "COMP.142.01",
    name: "Estruturas de Dados",
    teacher: "Helena Moura",
    room: "C-102",
    time: "SEG/QUA • 08:00",
    used: 38,
    total: 40,
    credits: 4,
    hours: 60,
    unlocks: 3,
    slots: [
      { day: 0, row: 0 },
      { day: 2, row: 0 },
    ],
  },
  {
    code: "COMP.151.02",
    name: "Banco de Dados II",
    teacher: "Lucas Nóbrega",
    room: "Lab 03",
    time: "TER/QUI • 10:00",
    used: 40,
    total: 40,
    credits: 4,
    hours: 60,
    unlocks: 2,
    slots: [
      { day: 1, row: 1 },
      { day: 3, row: 1 },
    ],
  },
  {
    code: "MAT.108.01",
    name: "Cálculo Numérico",
    teacher: "Lívia Porto",
    room: "B-201",
    time: "SEG/QUA • 10:00",
    used: 27,
    total: 35,
    credits: 4,
    hours: 60,
    unlocks: 1,
    slots: [
      { day: 0, row: 1 },
      { day: 2, row: 1 },
    ],
  },
  {
    code: "COMP.163.01",
    name: "Engenharia de Software",
    teacher: "Caio Freire",
    room: "C-205",
    time: "TER/QUI • 08:00",
    used: 19,
    total: 30,
    credits: 4,
    hours: 60,
    unlocks: 3,
    slots: [
      { day: 1, row: 0 },
      { day: 3, row: 0 },
    ],
  },
];

const coursePlan = [
  { code: "COMP.142.01", name: "Estruturas de Dados", prerequisites: [] },
  { code: "COMP.151.02", name: "Banco de Dados II", prerequisites: [] },
  { code: "MAT.108.01", name: "Cálculo Numérico", prerequisites: [] },
  { code: "COMP.163.01", name: "Engenharia de Software", prerequisites: [] },
  { code: "CC0401", name: "Projeto de Software", prerequisites: ["COMP.142.01"] },
  { code: "CC0403", name: "Teoria da Computação", prerequisites: ["COMP.142.01"] },
  { code: "CC0501", name: "Inteligência Artificial", prerequisites: ["MAT.108.01", "CC0401"] },
  { code: "CC0502", name: "Sistemas Distribuídos", prerequisites: ["COMP.151.02"] },
  { code: "CC0601", name: "Compiladores", prerequisites: ["CC0403"] },
  {
    code: "CC0602",
    name: "Projeto Integrador",
    prerequisites: ["CC0401", "COMP.163.01"],
  },
  { code: "CC0701", name: "TCC I", prerequisites: ["CC0602"] },
  { code: "CC0801", name: "TCC II", prerequisites: ["CC0701"] },
  { code: "EST801", name: "Estágio Supervisionado", prerequisites: ["CC0602"] },
  {
    code: "GRAD",
    name: "Conclusão do curso",
    prerequisites: ["CC0801", "EST801", "CC0601", "CC0501", "CC0502"],
  },
];

function projectGraduation(chosen: string[]) {
  const selected = new Set(chosen);
  const byCode = new Map(coursePlan.map((course) => [course.code, course]));
  const memo = new Map<string, { term: number; path: string[] }>();

  function resolve(code: string): { term: number; path: string[] } {
    const cached = memo.get(code);
    if (cached) return cached;
    const course = byCode.get(code);
    if (!course) return { term: 0, path: [] };

    if (course.prerequisites.length === 0) {
      const result = {
        term: selected.has(code) ? 1 : 2,
        path: [course.name],
      };
      memo.set(code, result);
      return result;
    }

    const prerequisiteResults = course.prerequisites.map(resolve);
    const critical = prerequisiteResults.reduce((longest, current) =>
      current.term > longest.term ? current : longest,
    );
    const result = {
      term: code === "GRAD" ? critical.term : critical.term + 1,
      path: code === "GRAD" ? critical.path : [...critical.path, course.name],
    };
    memo.set(code, result);
    return result;
  }

  const graduation = resolve("GRAD");
  return {
    months: graduation.term * 6,
    terms: graduation.term,
    path: graduation.path,
  };
}

function termAfterPeriods(periods: number) {
  const absoluteSemester = 2025 * 2 + 1 + (periods - 1);
  return `${Math.floor(absoluteSemester / 2)}.${absoluteSemester % 2 === 0 ? 1 : 2}`;
}

function Enrollment({ toast }: { toast: (x: string) => void }) {
  const [chosen, setChosen] = useState(["COMP.142.01"]);
  const [confirm, setConfirm] = useState(false);
  const selectedCourses = offered.filter((course) => chosen.includes(course.code));
  const selectedHours = selectedCourses.reduce((sum, course) => sum + course.hours, 0);
  const selectedCredits = selectedCourses.reduce((sum, course) => sum + course.credits, 0);
  const unlocked = selectedCourses.reduce((sum, course) => sum + course.unlocks, 0);
  const remainingHours = Math.max(0, 1024 - selectedHours);
  const projection = projectGraduation(chosen);
  const completionTerm = termAfterPeriods(projection.terms);

  function toggle(code: string) {
    setChosen((x) => (x.includes(code) ? x.filter((i) => i !== code) : [...x, code]));
  }
  return (
    <>
      <PageHeader
        eyebrow="MATRÍCULA ONLINE • 2025.2"
        title="Monte sua grade"
        description="Escolha suas turmas. As vagas são atualizadas em tempo real."
        actions={
          <div className="live">
            <i />
            VAGAS AO VIVO
          </div>
        }
      />
      <div className="enroll-status">
        <div>
          <span>ETAPA ATUAL</span>
          <b>Solicitação de matrícula</b>
          <small>Encerra em 20 jun, 23:59</small>
        </div>
        <div className="stepper">
          <i className="done">
            <Icon name="check" size={13} />
          </i>
          <span />
          <i className="active">2</i>
          <span />
          <i>3</i>
          <span />
          <i>4</i>
          <label>Dados</label>
          <label>Turmas</label>
          <label>Revisão</label>
          <label>Comprovante</label>
        </div>
        <div>
          <span>CRÉDITOS SELECIONADOS</span>
          <b>
            {selectedCredits} <small>/ 28 máx.</small>
          </b>
        </div>
      </div>
      <section className="enrollment-window">
        <span className="window-date">
          <b>16</b>
          <small>JUN</small>
        </span>
        <div>
          <Badge tone="green" pulse>
            MATRÍCULA ABERTA
          </Badge>
          <b>Período letivo 2025.2</b>
          <p>Faça sua escolha entre 16 e 20 de junho. O resultado será processado em 23 jun.</p>
        </div>
        <div className="window-countdown">
          <span>ENCERRA EM</span>
          <b>04 dias</b>
          <small>20 jun • 23:59</small>
        </div>
      </section>
      <section className="completion-planner card">
        <div className="planner-copy">
          <span className="icon-box emerald">
            <Icon name="flag" />
          </span>
          <div>
            <span>PREVISÃO DO CURSO COMPLETO</span>
            <h2>Sua rota crítica até a formatura</h2>
            <p>Calculada pela grade, pré-requisitos e disciplinas escolhidas em 2025.2.</p>
          </div>
        </div>
        <div className="completion-estimate">
          <span>TEMPO ESTIMADO</span>
          <strong>{projection.months} meses</strong>
          <b>Conclusão em {completionTerm}</b>
        </div>
        <div
          className="route-visual"
          aria-label={`Previsão de ${projection.months} meses até a conclusão do curso`}
        >
          <div className="route-line">
            {Array.from({ length: 6 }, (_, index) => (
              <span
                key={index}
                className={
                  index < 6 - projection.terms ? "route-past" : index === 5 ? "route-finish" : ""
                }
              >
                {index === 5 && <Icon name="flag" size={12} />}
              </span>
            ))}
          </div>
          <div className="route-labels">
            <span>2025.2</span>
            <span>{projection.terms} períodos letivos</span>
            <span>{completionTerm}</span>
          </div>
          <div className="planner-stats">
            <span>
              <b>{remainingHours}h</b> após este período
            </span>
            <span>
              <b>{unlocked}</b> disciplinas liberadas
            </span>
            <span>
              <b>{selectedHours}h</b> selecionadas agora
            </span>
          </div>
        </div>
        <div className="critical-path">
          <div>
            <span>ROTA CRÍTICA ATUAL</span>
            <Badge tone="amber">DEFINE A DATA</Badge>
          </div>
          <section>
            {projection.path.map((course, index) => (
              <div key={course}>
                <span
                  className={
                    index === 0 ? "current" : index === projection.path.length - 1 ? "finish" : ""
                  }
                >
                  {index === projection.path.length - 1 ? (
                    <Icon name="flag" size={12} />
                  ) : (
                    index + 1
                  )}
                </span>
                <b>{course}</b>
                {index < projection.path.length - 1 && <Icon name="chevron" size={13} />}
              </div>
            ))}
          </section>
          <p>
            A previsão assume aprovação nas disciplinas e oferta regular a cada semestre.
            Reprovações, trancamentos ou indisponibilidade de turmas podem alterar a data.
          </p>
        </div>
      </section>
      <section className="card weekly">
        <div className="section-head">
          <div>
            <h2>Espelho semanal</h2>
            <p>Sua composição de horários</p>
          </div>
          <Badge tone="green">SEM CONFLITOS</Badge>
        </div>
        <div className="week-grid">
          <span />
          <b>SEG</b>
          <b>TER</b>
          <b>QUA</b>
          <b>QUI</b>
          <b>SEX</b>
          <em>08:00</em>
          {[0, 1, 2, 3, 4].map((day) => {
            const course = selectedCourses.find((item) =>
              item.slots.some((slot) => slot.day === day && slot.row === 0),
            );
            return course ? (
              <div className="class-block blue" key={`morning-${day}`}>
                {course.name}
                <small>{course.room}</small>
              </div>
            ) : (
              <div key={`morning-${day}`} />
            );
          })}
          <em>10:00</em>
          {[0, 1, 2, 3, 4].map((day) => {
            const course = selectedCourses.find((item) =>
              item.slots.some((slot) => slot.day === day && slot.row === 1),
            );
            return course ? (
              <div className="class-block violet" key={`late-${day}`}>
                {course.name}
                <small>{course.room}</small>
              </div>
            ) : (
              <div key={`late-${day}`} />
            );
          })}
        </div>
      </section>
      <section className="card catalog">
        <div className="catalog-head">
          <div>
            <h2>Turmas ofertadas</h2>
            <p>{offered.length} turmas compatíveis com sua matriz</p>
          </div>
          <div className="search">
            <Icon name="search" />
            <input placeholder="Buscar disciplina, código ou docente..." />
          </div>
          <button className="filter">
            <Icon name="filter" /> Filtros <b>2</b>
          </button>
        </div>
        <div className="table-scroll">
          <table>
            <thead>
              <tr>
                <th>DISCIPLINA</th>
                <th>DOCENTE</th>
                <th>SALA</th>
                <th>HORÁRIO</th>
                <th>OCUPAÇÃO</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {offered.map((t) => {
                const full = t.used === t.total;
                const is = chosen.includes(t.code);
                const alternative = projectGraduation(
                  is ? chosen.filter((code) => code !== t.code) : [...chosen, t.code],
                );
                const impact = is
                  ? alternative.months - projection.months
                  : projection.months - alternative.months;
                return (
                  <tr key={t.code}>
                    <td>
                      <div className="discipline-cell">
                        <span className="course-mark blue">
                          {t.name
                            .split(" ")
                            .map((x) => x[0])
                            .join("")
                            .slice(0, 2)}
                        </span>
                        <div>
                          <b>{t.name}</b>
                          <span>{t.code}</span>
                          <small
                            className={impact > 0 ? "course-impact positive" : "course-impact"}
                          >
                            {impact > 0
                              ? `${is ? "Evita" : "Antecipa"} ${impact} meses na conclusão`
                              : "Sem alteração na rota crítica"}
                          </small>
                        </div>
                      </div>
                    </td>
                    <td>{t.teacher}</td>
                    <td>
                      <span className="with-icon">
                        <Icon name="map" size={14} />
                        {t.room}
                      </span>
                    </td>
                    <td>
                      <span className="with-icon">
                        <Icon name="clock" size={14} />
                        {t.time}
                      </span>
                    </td>
                    <td>
                      <div className="capacity">
                        <span>
                          <b>{t.used}</b> / {t.total}
                        </span>
                        <Badge tone={full ? "amber" : t.total - t.used <= 3 ? "red" : "green"}>
                          {full ? "LOTADA" : `${t.total - t.used} VAGAS`}
                        </Badge>
                        <Progress
                          value={(t.used / t.total) * 100}
                          color={full ? "orange" : "blue"}
                        />
                      </div>
                    </td>
                    <td>
                      {full ? (
                        <Button
                          variant="warning"
                          small
                          onClick={() => toast("Você entrou na lista de espera na posição #4.")}
                        >
                          Lista de espera
                        </Button>
                      ) : (
                        <Button
                          variant={is ? "secondary" : "primary"}
                          small
                          onClick={() => toggle(t.code)}
                          icon={is ? "check" : "plus"}
                        >
                          {is ? "Selecionada" : "Adicionar"}
                        </Button>
                      )}
                    </td>
                  </tr>
                );
              })}
            </tbody>
          </table>
        </div>
      </section>
      <div className="floating-cart">
        <div>
          <span className="icon-box blue">
            <Icon name="book" />
          </span>
          <div>
            <b>{chosen.length} turmas selecionadas</b>
            <span>
              {selectedCredits} créditos • {selectedHours}h • curso concluído em ~
              {projection.months} meses
            </span>
          </div>
        </div>
        <Button onClick={() => setConfirm(true)} disabled={!chosen.length}>
          Revisar e confirmar <Icon name="arrow" />
        </Button>
      </div>
      {confirm && (
        <Modal title="Confirmar solicitação" icon="lock" close={() => setConfirm(false)}>
          <p>As vagas serão reservadas de forma transacional após a confirmação.</p>
          <div className="confirm-list">
            {chosen.map((c) => (
              <div key={c}>
                <Icon name="check" />
                <b>{offered.find((x) => x.code === c)?.name}</b>
                <span>{c}</span>
              </div>
            ))}
          </div>
          <div className="modal-note">
            <Icon name="alert" />
            <span>Após confirmar, um comprovante PDF com código único será gerado.</span>
          </div>
          <div className="modal-actions">
            <Button variant="secondary" onClick={() => setConfirm(false)}>
              Voltar
            </Button>
            <Button
              onClick={() => {
                setConfirm(false);
                toast("Matrícula solicitada. Seu comprovante está pronto.");
              }}
            >
              Confirmar matrícula
            </Button>
          </div>
        </Modal>
      )}
    </>
  );
}

function Diary({ toast }: { toast: (x: string) => void }) {
  const [tab, setTab] = useState<"freq" | "grades">("freq");
  const [close, setClose] = useState(false);
  const students = [
    {
      name: "Aline Ferreira",
      id: "2023101024",
      f: 8,
      grades: ["8,5", "9,0", "—", "—"],
      avg: "8,8",
      status: "APROVADA",
    },
    {
      name: "Bruno Almeida",
      id: "2022101041",
      f: 26,
      grades: ["6,0", "5,5", "—", "—"],
      avg: "5,8",
      status: "EM EXAME",
    },
    {
      name: "Camila Rocha",
      id: "2023101066",
      f: 12,
      grades: ["9,0", "8,7", "—", "—"],
      avg: "8,9",
      status: "APROVADA",
    },
    {
      name: "Diego Santos",
      id: "2021101098",
      f: 32,
      grades: ["7,0", "6,5", "—", "—"],
      avg: "6,8",
      status: "RISCO POR FALTA",
    },
  ];
  return (
    <>
      <PageHeader
        eyebrow="DOCÊNCIA • PERÍODO 2025.1"
        title="Diário de classe"
        description="Estruturas de Dados • COMP.142.01"
        actions={
          <>
            <Badge tone="green">ABERTO • EM EDIÇÃO</Badge>
            <Button variant="danger" icon="lock" onClick={() => setClose(true)}>
              Consolidar e fechar
            </Button>
          </>
        }
      />
      <div className="class-summary card">
        <div>
          <span className="course-mark blue">ED</span>
          <div>
            <b>Estruturas de Dados</b>
            <span>COMP.142.01 • Bacharelado em Ciência da Computação</span>
          </div>
        </div>
        <dl>
          <div>
            <dt>CARGA HORÁRIA</dt>
            <dd>60h</dd>
          </div>
          <div>
            <dt>AULAS MINISTRADAS</dt>
            <dd>
              32 <small>/ 40</small>
            </dd>
          </div>
          <div>
            <dt>ALUNOS ATIVOS</dt>
            <dd>28</dd>
          </div>
          <div>
            <dt>HORÁRIO</dt>
            <dd>SEG/QUA • 08:00</dd>
          </div>
        </dl>
      </div>
      <div className="tabs">
        <button className={tab === "freq" ? "active" : ""} onClick={() => setTab("freq")}>
          <Icon name="calendar" /> Frequência
        </button>
        <button className={tab === "grades" ? "active" : ""} onClick={() => setTab("grades")}>
          <Icon name="book" /> Notas e avaliações
        </button>
        <button>
          <Icon name="file" /> Registro de aulas
        </button>
        <button>
          <Icon name="folder" /> Materiais
        </button>
      </div>
      <section className="card diary-table">
        <div className="section-head">
          <div>
            <h2>{tab === "freq" ? "Frequência — Junho" : "Notas e avaliações"}</h2>
            <p>
              {tab === "freq"
                ? "Clique para alternar entre presente e ausente. Salvamento automático."
                : "Média calculada automaticamente conforme o plano de ensino."}
            </p>
          </div>
          <div className="saved">
            <Icon name="check" /> Alterações salvas
          </div>
        </div>
        <div className="table-scroll">
          <table>
            <thead>
              <tr>
                <th>ALUNO</th>
                {tab === "freq" ? (
                  <>
                    <th>02 JUN</th>
                    <th>04 JUN</th>
                    <th>09 JUN</th>
                    <th>11 JUN</th>
                    <th>16 JUN</th>
                    <th>% FALTAS</th>
                  </>
                ) : (
                  <>
                    <th>AVALIAÇÃO 1</th>
                    <th>AVALIAÇÃO 2</th>
                    <th>AVALIAÇÃO 3</th>
                    <th>EXAME FINAL</th>
                    <th>MÉDIA</th>
                    <th>SITUAÇÃO</th>
                  </>
                )}
              </tr>
            </thead>
            <tbody>
              {students.map((s, si) => (
                <tr key={s.id}>
                  <td>
                    <div className="student-cell">
                      <span>
                        {s.name
                          .split(" ")
                          .map((x) => x[0])
                          .join("")}
                      </span>
                      <div>
                        <b>{s.name}</b>
                        <small>{s.id}</small>
                      </div>
                    </div>
                  </td>
                  {tab === "freq" ? (
                    <>
                      <td>
                        <Attendance checked />
                      </td>
                      <td>
                        <Attendance checked={si !== 3} />
                      </td>
                      <td>
                        <Attendance checked={si !== 1} />
                      </td>
                      <td>
                        <Attendance checked />
                      </td>
                      <td>
                        <Attendance checked={si < 2} />
                      </td>
                      <td>
                        <div className="absence">
                          <b className={s.f > 25 ? "danger-text" : ""}>{s.f}%</b>
                          {s.f > 25 && (
                            <Badge tone="red" pulse>
                              RISCO DE REPROVAÇÃO
                            </Badge>
                          )}
                        </div>
                      </td>
                    </>
                  ) : (
                    <>
                      {s.grades.map((g, i) => (
                        <td key={i}>
                          <input className="grade-input" defaultValue={g} />
                        </td>
                      ))}
                      <td>
                        <b className="final-grade">{s.avg}</b>
                      </td>
                      <td>
                        <Badge
                          tone={
                            s.status === "APROVADA"
                              ? "green"
                              : s.status === "EM EXAME"
                                ? "amber"
                                : "red"
                          }
                        >
                          {s.status}
                        </Badge>
                      </td>
                    </>
                  )}
                </tr>
              ))}
            </tbody>
          </table>
        </div>
        <div className="table-footer">
          <span>Exibindo 4 de 28 alunos</span>
          <Button variant="secondary" small>
            Carregar todos
          </Button>
        </div>
      </section>
      {close && (
        <Modal title="Consolidar diário" icon="lock" close={() => setClose(false)}>
          <p>Esta ação é irreversível. As notas e frequências ficarão bloqueadas para edição.</p>
          <div className="critical-box">
            <Icon name="alert" />
            <div>
              <b>Confirme antes de continuar</b>
              <span>
                2 alunos estão em risco de reprovação por falta. Todos os registros serão
                consolidados.
              </span>
            </div>
          </div>
          <label className="field-label">
            SENHA INSTITUCIONAL
            <input type="password" placeholder="Digite sua senha" />
          </label>
          <div className="modal-actions">
            <Button variant="secondary" onClick={() => setClose(false)}>
              Cancelar
            </Button>
            <Button
              variant="danger"
              onClick={() => {
                setClose(false);
                toast("Diário consolidado e enviado à coordenação.");
              }}
            >
              Autenticar e fechar diário
            </Button>
          </div>
        </Modal>
      )}
    </>
  );
}

function Attendance({ checked = false }: { checked?: boolean }) {
  const [on, setOn] = useState(checked);
  return (
    <button
      className={`attendance ${on ? "present" : "absent"}`}
      onClick={() => setOn(!on)}
      title={on ? "Presente" : "Ausente"}
    >
      {on ? <Icon name="check" size={15} /> : <Icon name="x" size={15} />}
    </button>
  );
}

function Monitoring({ toast }: { toast: (x: string) => void }) {
  const [view, setView] = useState<"monitor" | "student">("monitor");
  const [newShift, setNewShift] = useState(false);
  const appointments = [
    { name: "João Victor", topic: "Árvores AVL e rotações", time: "Hoje • 17:30", state: "asked" },
    { name: "Larissa Melo", topic: "Exercício 4 da lista", time: "Hoje • 18:00", state: "asked" },
    {
      name: "Paulo Henrique",
      topic: "Complexidade de algoritmos",
      time: "18 jun • 14:00",
      state: "confirmed",
    },
    { name: "Ana Beatriz", topic: "Tabela hash", time: "14 jun • 16:30", state: "done" },
  ];
  return (
    <>
      <PageHeader
        eyebrow="PROGRAMA DE MONITORIA • 2025.1"
        title="Plantões de dúvidas"
        description="Organize sua disponibilidade e acompanhe os atendimentos."
        actions={
          <>
            <div className="segmented">
              <button
                className={view === "monitor" ? "active" : ""}
                onClick={() => setView("monitor")}
              >
                Visão monitor
              </button>
              <button
                className={view === "student" ? "active" : ""}
                onClick={() => setView("student")}
              >
                Visão aluno
              </button>
            </div>
            {view === "monitor" && (
              <Button icon="plus" onClick={() => setNewShift(true)}>
                Novo horário
              </Button>
            )}
          </>
        }
      />
      {view === "monitor" ? (
        <>
          <div className="monitor-kpis">
            <div className="card">
              <span className="icon-box blue">
                <Icon name="calendar" />
              </span>
              <div>
                <span>ATENDIMENTOS NO MÊS</span>
                <b>18</b>
                <small>+12% em relação a maio</small>
              </div>
            </div>
            <div className="card">
              <span className="icon-box emerald">
                <Icon name="clock" />
              </span>
              <div>
                <span>HORAS COMPUTADAS</span>
                <b>24h 30min</b>
                <small>Meta mensal: 30h</small>
              </div>
            </div>
            <div className="card">
              <span className="icon-box amber">
                <Icon name="users" />
              </span>
              <div>
                <span>TAXA DE PRESENÇA</span>
                <b>91%</b>
                <small>2 faltas registradas</small>
              </div>
            </div>
          </div>
          <section className="kanban">
            {[
              { id: "asked", title: "Solicitados", tone: "amber", desc: "Aguardando sua resposta" },
              {
                id: "confirmed",
                title: "Confirmados",
                tone: "blue",
                desc: "Próximos atendimentos",
              },
              { id: "done", title: "Concluídos", tone: "green", desc: "Histórico recente" },
            ].map((col) => (
              <div className="kanban-col" key={col.id}>
                <header>
                  <div>
                    <i className={col.tone} />
                    <b>{col.title}</b>
                    <em>{appointments.filter((x) => x.state === col.id).length}</em>
                  </div>
                  <span>{col.desc}</span>
                </header>
                {appointments
                  .filter((x) => x.state === col.id)
                  .map((a, i) => (
                    <article className="appointment" key={a.name}>
                      <div className="appointment-user">
                        <span>
                          {a.name
                            .split(" ")
                            .map((x) => x[0])
                            .join("")
                            .slice(0, 2)}
                        </span>
                        <div>
                          <b>{a.name}</b>
                          <small>Ciência da Computação</small>
                        </div>
                        <Icon name="more" />
                      </div>
                      <div className="topic">
                        <span>TÓPICO DA DÚVIDA</span>
                        <b>{a.topic}</b>
                      </div>
                      <div className="appt-time">
                        <Icon name="clock" />
                        <b>{a.time}</b>
                      </div>
                      {col.id === "asked" && (
                        <div className="double-action">
                          <Button
                            small
                            onClick={() => toast(`Agendamento de ${a.name} confirmado.`)}
                            icon="check"
                          >
                            Confirmar
                          </Button>
                          <Button small variant="secondary" icon="x">
                            Recusar
                          </Button>
                        </div>
                      )}
                      {col.id === "confirmed" && (
                        <div className="double-action">
                          <Button
                            small
                            onClick={() => toast("Presença registrada e horas computadas.")}
                            icon="check"
                          >
                            Presente
                          </Button>
                          <Button small variant="secondary">
                            Marcar falta
                          </Button>
                        </div>
                      )}
                      {col.id === "done" && (
                        <div className="completed">
                          <Icon name="check" />
                          <span>Atendimento concluído</span>
                          <b>{i + 1}h computada</b>
                        </div>
                      )}
                    </article>
                  ))}
                {col.id === "confirmed" && (
                  <div className="empty-slot">
                    <Icon name="calendar" />
                    <span>Próximo horário livre</span>
                    <b>Quarta, 16:00</b>
                  </div>
                )}
              </div>
            ))}
          </section>
        </>
      ) : (
        <StudentMonitor toast={toast} />
      )}
      {newShift && (
        <Modal
          title="Cadastrar horário de plantão"
          icon="calendar"
          close={() => setNewShift(false)}
        >
          <div className="form-grid">
            <label className="field-label">
              DIA DA SEMANA
              <select>
                <option>Quarta-feira</option>
                <option>Sexta-feira</option>
              </select>
            </label>
            <label className="field-label">
              QUANTIDADE DE VAGAS
              <input type="number" defaultValue="4" />
            </label>
            <label className="field-label">
              HORA INÍCIO
              <input type="time" defaultValue="16:00" />
            </label>
            <label className="field-label">
              HORA FIM
              <input type="time" defaultValue="18:00" />
            </label>
            <label className="field-label full">
              LOCAL OU LINK
              <input placeholder="Lab 03 ou link do Google Meet" />
            </label>
          </div>
          <div className="modal-actions">
            <Button variant="secondary" onClick={() => setNewShift(false)}>
              Cancelar
            </Button>
            <Button
              onClick={() => {
                setNewShift(false);
                toast("Novo horário de plantão publicado.");
              }}
            >
              Publicar disponibilidade
            </Button>
          </div>
        </Modal>
      )}
    </>
  );
}

function StudentMonitor({ toast }: { toast: (x: string) => void }) {
  return (
    <section className="card available-shifts">
      <div className="section-head">
        <div>
          <h2>Horários disponíveis</h2>
          <p>Monitores de Estruturas de Dados</p>
        </div>
        <div className="search">
          <Icon name="search" />
          <input placeholder="Buscar monitor..." />
        </div>
      </div>
      <div className="shift-grid">
        {[
          {
            name: "Rafael Lima",
            when: "Hoje • 17:30–19:00",
            place: "Lab 03",
            slots: "2 de 4 vagas",
          },
          {
            name: "Beatriz Alves",
            when: "Amanhã • 14:00–16:00",
            place: "Google Meet",
            slots: "3 de 5 vagas",
          },
          {
            name: "Rafael Lima",
            when: "Sexta • 10:00–12:00",
            place: "Biblioteca, sala 2",
            slots: "1 de 4 vagas",
          },
        ].map((x) => (
          <article key={x.when}>
            <div className="monitor-name">
              <span>
                {x.name
                  .split(" ")
                  .map((y) => y[0])
                  .join("")}
              </span>
              <div>
                <b>{x.name}</b>
                <small>Monitor(a) • Nota 4,9</small>
              </div>
            </div>
            <div className="shift-detail">
              <Icon name="clock" />
              <span>{x.when}</span>
            </div>
            <div className="shift-detail">
              <Icon name="map" />
              <span>{x.place}</span>
            </div>
            <Progress value={65} />
            <small>{x.slots}</small>
            <Button onClick={() => toast(`Dúvida agendada com ${x.name}.`)}>Agendar dúvida</Button>
          </article>
        ))}
      </div>
    </section>
  );
}

function Library({ toast }: { toast: (x: string) => void }) {
  const [mode, setMode] = useState<"public" | "moderation">("public");
  const materials = [
    {
      title: "Guia Visual de Árvores AVL",
      type: "RESUMO",
      author: "Mariana Costa",
      semester: "2025.1",
      tags: ["#EstruturasDeDados", "#P2"],
      votes: 124,
      color: "blue",
    },
    {
      title: "Lista Resolvida — Normalização",
      type: "EXERCÍCIOS",
      author: "Discente anônimo",
      semester: "2024.2",
      tags: ["#BancoDeDados", "#Listas"],
      votes: 89,
      color: "violet",
    },
    {
      title: "Cálculo Numérico em Python",
      type: "SLIDES",
      author: "Prof. Pedro Lima",
      semester: "2025.1",
      tags: ["#Python", "#MétodosNuméricos"],
      votes: 67,
      color: "orange",
    },
  ];
  return (
    <>
      <PageHeader
        eyebrow="ACERVO COLABORATIVO"
        title={mode === "public" ? "Conhecimento que circula." : "Fila de moderação"}
        description={
          mode === "public"
            ? "Materiais compartilhados pela comunidade acadêmica da UEPB."
            : "Avalie publicações e denúncias da comunidade."
        }
        actions={
          <>
            <div className="segmented">
              <button
                className={mode === "public" ? "active" : ""}
                onClick={() => setMode("public")}
              >
                Acervo
              </button>
              <button
                className={mode === "moderation" ? "active" : ""}
                onClick={() => setMode("moderation")}
              >
                Moderação <i>6</i>
              </button>
            </div>
            {mode === "public" && <Button icon="upload">Enviar material</Button>}
          </>
        }
      />
      {mode === "public" ? (
        <>
          <div className="library-search">
            <Icon name="search" size={21} />
            <input placeholder="Busque por título, disciplina, assunto ou autor..." />
            <Button>Buscar no acervo</Button>
          </div>
          <div className="filter-row">
            <button>
              <Icon name="filter" /> Todos os filtros
            </button>
            <button>
              Disciplina <Icon name="chevron" size={13} />
            </button>
            <button>
              Tipo de material <Icon name="chevron" size={13} />
            </button>
            <button>
              Semestre <Icon name="chevron" size={13} />
            </button>
            <span>328 materiais encontrados</span>
          </div>
          <div className="materials-grid">
            {materials.map((m) => (
              <article className="material card" key={m.title}>
                <div className={`doc-preview ${m.color}`}>
                  <Icon name="file" size={40} />
                  <Badge tone="white">PDF • 2,4 MB</Badge>
                </div>
                <div className="material-body">
                  <div className="material-top">
                    <Badge tone={m.color}>{m.type}</Badge>
                    <button>
                      <Icon name="more" />
                    </button>
                  </div>
                  <h3>{m.title}</h3>
                  <p>
                    Material completo com conceitos, exemplos práticos e exercícios para revisão.
                  </p>
                  <div className="tags">
                    {m.tags.map((t) => (
                      <span key={t}>{t}</span>
                    ))}
                  </div>
                  <div className="author">
                    <span>
                      {m.author
                        .split(" ")
                        .map((x) => x[0])
                        .join("")
                        .slice(0, 2)}
                    </span>
                    <div>
                      <b>{m.author}</b>
                      <small>Publicado em {m.semester}</small>
                    </div>
                  </div>
                  <div className="material-actions">
                    <Button
                      variant="secondary"
                      small
                      icon="thumb"
                      onClick={() => toast("Seu voto de utilidade foi computado.")}
                    >
                      Útil • {m.votes}
                    </Button>
                    <Button small icon="download">
                      Baixar
                    </Button>
                    <button className="flag">
                      <Icon name="flag" size={15} />
                    </button>
                  </div>
                </div>
              </article>
            ))}
          </div>
        </>
      ) : (
        <Moderation toast={toast} />
      )}
    </>
  );
}

function Moderation({ toast }: { toast: (x: string) => void }) {
  return (
    <>
      <div className="moderation-tabs">
        <button className="active">
          Pendentes <Badge tone="amber">4</Badge>
        </button>
        <button>
          Denúncias <Badge tone="red">2</Badge>
        </button>
        <button>Histórico</button>
      </div>
      <section className="moderation-layout">
        <div className="moderation-list card">
          <div className="section-head">
            <div>
              <h2>Aguardando avaliação</h2>
              <p>Ordenado por data de envio</p>
            </div>
            <button>
              <Icon name="filter" />
            </button>
          </div>
          {[
            {
              title: "Resumo de Grafos — P2",
              author: "Igor Matias",
              time: "Há 2 horas",
              active: true,
            },
            { title: "Lista de Cálculo Resolvida", author: "Discente anônimo", time: "Há 5 horas" },
            { title: "Slides — Modelo Relacional", author: "Júlia Nunes", time: "Ontem, 18:40" },
            { title: "Dicas para o TCC I", author: "Renato Alves", time: "Ontem, 14:12" },
          ].map((x) => (
            <button className={`review-item ${x.active ? "active" : ""}`} key={x.title}>
              <span className="icon-box blue">
                <Icon name="file" />
              </span>
              <div>
                <b>{x.title}</b>
                <small>
                  {x.author} • {x.time}
                </small>
                <Badge tone="amber">PENDENTE</Badge>
              </div>
              <Icon name="chevron" />
            </button>
          ))}
        </div>
        <article className="review-detail card">
          <div className="review-head">
            <div>
              <Badge tone="amber">PENDENTE DE APROVAÇÃO</Badge>
              <h2>Resumo de Grafos — P2</h2>
              <p>
                Enviado por <b>Igor Matias</b> • 16 jun 2025, 09:42
              </p>
            </div>
            <Button variant="secondary" icon="download">
              Baixar original
            </Button>
          </div>
          <div className="pdf-preview">
            <div>
              <Icon name="file" size={42} />
              <b>resumo_grafos_p2.pdf</b>
              <span>12 páginas • 3,8 MB</span>
              <Button variant="secondary" icon="eye">
                Abrir prévia completa
              </Button>
            </div>
          </div>
          <dl className="document-meta">
            <div>
              <dt>DISCIPLINA</dt>
              <dd>Teoria dos Grafos</dd>
            </div>
            <div>
              <dt>TIPO</dt>
              <dd>Resumo</dd>
            </div>
            <div>
              <dt>SEMESTRE</dt>
              <dd>2025.1</dd>
            </div>
            <div>
              <dt>TERMO DE AUTORIA</dt>
              <dd className="success-text">
                <Icon name="check" /> Aceito pelo autor
              </dd>
            </div>
          </dl>
          <label className="field-label">
            OBSERVAÇÃO AO AUTOR (OPCIONAL)
            <textarea placeholder="Adicione um motivo ou orientação..." />
          </label>
          <div className="review-actions">
            <Button variant="danger" icon="x">
              Rejeitar
            </Button>
            <Button variant="warning" icon="arrow">
              Solicitar ajuste
            </Button>
            <Button icon="check" onClick={() => toast("Material aprovado e publicado no acervo.")}>
              Aprovar e publicar
            </Button>
          </div>
        </article>
      </section>
    </>
  );
}

function Modal({
  title,
  icon,
  close,
  children,
}: {
  title: string;
  icon: IconName;
  close: () => void;
  children: ReactNode;
}) {
  return (
    <div
      className="modal-backdrop"
      onMouseDown={(e) => {
        if (e.target === e.currentTarget) close();
      }}
    >
      <section className="modal">
        <header>
          <span className="icon-box blue">
            <Icon name={icon} />
          </span>
          <h2>{title}</h2>
          <button onClick={close}>
            <Icon name="x" />
          </button>
        </header>
        <div className="modal-body">{children}</div>
      </section>
    </div>
  );
}

function AppContent() {
  const { usuario, isAuthenticated, logout } = useAuth();
  const [page, setPage] = useState<Page>("dashboard");
  const [collapsed, setCollapsed] = useState(false);
  const [mobileOpen, setMobileOpen] = useState(false);
  const [toast, setToast] = useState("");

  const navItems = useMemo(() => getNavItemsForProfile(usuario?.perfil), [usuario?.perfil]);
  const pageName = useMemo(() => navItems.find((x) => x.id === page)?.label || "", [navItems, page]);

  useEffect(() => {
    if (usuario?.perfil === "ADMIN") {
      setPage("admin");
    } else if (usuario?.perfil === "PROFESSOR") {
      setPage("diary");
    } else if (usuario?.perfil === "MONITOR") {
      setPage("monitoring");
    } else {
      setPage("dashboard");
    }
  }, [usuario?.perfil]);

  if (!isAuthenticated) {
    return <LoginPage />;
  }

  const initials = usuario?.nome
    ? usuario.nome
        .split(" ")
        .map((x) => x[0])
        .join("")
        .slice(0, 2)
        .toUpperCase()
    : "MC";

  function notify(message: string) {
    setToast(message);
    window.setTimeout(() => setToast(""), 4000);
  }
  function go(p: Page) {
    setPage(p);
    setMobileOpen(false);
    window.scrollTo({ top: 0, behavior: "smooth" });
  }

  const portalNome =
    usuario?.perfil === "ADMIN"
      ? "Portal do Administrador Geral (Gestão Global)"
      : usuario?.perfil === "PROFESSOR"
      ? "Portal do Docente"
      : usuario?.perfil === "COORDENADOR"
      ? "Portal da Coordenação de Curso"
      : usuario?.perfil === "SECRETARIO"
      ? "Portal da Secretaria Acadêmica"
      : usuario?.perfil === "MONITOR"
      ? "Portal do Monitor Acadêmico"
      : "Portal do Aluno";

  return (
    <div className={`app-shell ${collapsed ? "is-collapsed" : ""}`}>
      <aside className={`sidebar ${mobileOpen ? "mobile-open" : ""}`}>
        <div className="brand">
          <div className="brand-mark">
            <span>UE</span>
            <i />
          </div>
          <div>
            <b>UEPB</b>
            <span>Sistema Acadêmico</span>
          </div>
          <button onClick={() => setCollapsed(!collapsed)}>
            <Icon name="chevron" />
          </button>
        </div>
        <div className="profile-card">
          <div className="avatar">
            {initials}
            <span />
          </div>
          <div>
            <b>{usuario?.nome || "Mariana Costa"}</b>
            <span>{usuario?.perfil} • {usuario?.identificador}</span>
          </div>
          <Icon name="chevron" size={14} />
        </div>
        <nav>
          <span className="nav-label">MENU • {usuario?.perfil}</span>
          {navItems.map((item) => (
            <button
              key={item.id}
              className={page === item.id ? "active" : ""}
              onClick={() => go(item.id)}
              title={item.label}
            >
              <Icon name={item.icon} />
              <span>{item.label}</span>
              {item.id === "enrollment" && <i>2</i>}
            </button>
          ))}
        </nav>
        <div className="sidebar-bottom">
          <div className="term-card">
            <span>PERÍODO ATUAL</span>
            <b>2025.1</b>
            <Progress value={74} />
            <small>74% concluído • 28 dias restantes</small>
          </div>
          <button>
            <Icon name="alert" />
            <span>Central de ajuda</span>
          </button>
          <button onClick={logout} style={{ color: "#ef233c" }}>
            <Icon name="x" />
            <span>Sair do sistema (Logout)</span>
          </button>
        </div>
      </aside>
      <div className="main-area">
        <header className="topbar">
          <button className="mobile-menu" onClick={() => setMobileOpen(!mobileOpen)}>
            <Icon name="menu" />
          </button>
          <div className="breadcrumb">
            <span>{portalNome}</span>
            <Icon name="chevron" size={13} />
            <b>{pageName}</b>
          </div>
          <div className="top-actions">
            <button className="command">
              <Icon name="search" />
              <span>Buscar no sistema</span>
              <kbd>⌘ K</kbd>
            </button>
            <button className="notify">
              <Icon name="bell" />
              <i />
            </button>
            <span className="divider" />
            <button className="campus">
              <span>CAMPUS I</span>
              <b>Campina Grande</b>
              <Icon name="chevron" size={13} />
            </button>
            <button
              onClick={logout}
              style={{
                background: "#fee2e2",
                color: "#b91c1c",
                border: "1px solid #fecaca",
                borderRadius: "8px",
                padding: "6px 12px",
                fontSize: "12px",
                fontWeight: 600,
                display: "flex",
                alignItems: "center",
                gap: "6px",
              }}
              title="Encerrar sessão ativa"
            >
              <Icon name="x" size={14} />
              <span>Sair</span>
            </button>
          </div>
        </header>
        <main>
          {page === "admin" && <AdminPanel toast={notify} />}
          {page === "dashboard" && <Dashboard go={go} />}
          {page === "curriculum" && <Curriculum />}
          {page === "enrollment" && <Enrollment toast={notify} />}
          {page === "diary" && <Diary toast={notify} />}
          {page === "monitoring" && <Monitoring toast={notify} />}
          {page === "library" && <Library toast={notify} />}
        </main>
      </div>
      {mobileOpen && <div className="mobile-overlay" onClick={() => setMobileOpen(false)} />}
      {toast && (
        <div className="toast">
          <span>
            <Icon name="check" />
          </span>
          <div>
            <b>Atualização concluída</b>
            <p>{toast}</p>
          </div>
          <button onClick={() => setToast("")}>
            <Icon name="x" />
          </button>
        </div>
      )}
    </div>
  );
}

function App() {
  return (
    <AuthProvider>
      <AppContent />
    </AuthProvider>
  );
}

export default App;
