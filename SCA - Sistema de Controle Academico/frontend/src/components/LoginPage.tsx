import { useState, type FormEvent } from "react";
import { useAuth, USUARIOS_DEMO } from "../context/AuthContext";
import type { PerfilUsuario } from "../types/auth";

export function LoginPage() {
  const { login, selecionarPerfilRapido, isLoading } = useAuth();
  const [identificador, setIdentificador] = useState("");
  const [senha, setSenha] = useState("");
  const [lembrar, setLembrar] = useState(true);
  const [showPassword, setShowPassword] = useState(false);
  const [esqueciSenhaOpen, setEsqueciSenhaOpen] = useState(false);
  const [recuperacaoEmail, setRecuperacaoEmail] = useState("");
  const [recuperacaoEnviada, setRecuperacaoEnviada] = useState(false);
  const [erro, setErro] = useState("");

  async function handleSubmit(e: FormEvent) {
    e.preventDefault();
    setErro("");
    if (!identificador.trim()) {
      setErro("Informe sua matrícula, SIAPE ou e-mail institucional.");
      return;
    }
    if (!senha) {
      setErro("Informe sua senha de acesso.");
      return;
    }
    await login(identificador, senha);
  }

  function handleRecuperarSenha(e: FormEvent) {
    e.preventDefault();
    if (!recuperacaoEmail.trim()) return;
    setRecuperacaoEnviada(true);
  }

  return (
    <div className="login-wrapper">
      <div className="login-ambient-shape shape-1" />
      <div className="login-ambient-shape shape-2" />

      <div className="login-container">
        {/* Lado Esquerdo - Institucional */}
        <div className="login-hero">
          <div className="hero-content">
            <div className="hero-brand">
              <div className="hero-logo-box">
                <span className="logo-initials">UE</span>
                <i className="logo-dot" />
              </div>
              <div>
                <h2>UEPB</h2>
                <span>Universidade Estadual da Paraíba</span>
              </div>
            </div>

            <div className="hero-body">
              <span className="hero-tag">SISTEMA OFICIAL • CAMPUS I</span>
              <h1>Sistema de Controle Acadêmico (SCA)</h1>
              <p>
                Acesso unificado para consulta de vida acadêmica, matrícula inteligente
                com previsão de conclusão, diários eletrônicos, plantões de monitoria e
                acervo colaborativo.
              </p>

              <div className="hero-features">
                <div className="feature-item">
                  <div className="feature-icon">⚡</div>
                  <div>
                    <b>Matrícula Inteligente</b>
                    <span>Validação de pré-requisitos e controle de vagas em tempo real.</span>
                  </div>
                </div>
                <div className="feature-item">
                  <div className="feature-icon">📊</div>
                  <div>
                    <b>Melhor Caminho & Grade Interativa</b>
                    <span>Estimativa automática do tempo de formatura e prevenção de gargalos.</span>
                  </div>
                </div>
                <div className="feature-item">
                  <div className="feature-icon">🛡️</div>
                  <div>
                    <b>Segurança Transacional</b>
                    <span>Bloqueio atômico de concorrência e conformidade com a matriz curricular.</span>
                  </div>
                </div>
              </div>
            </div>

            <div className="hero-footer">
              <p>Ciência da Computação • Disciplina de Análise e Projeto de Sistemas (APS)</p>
            </div>
          </div>
        </div>

        {/* Lado Direito - Formulário de Login */}
        <div className="login-panel">
          <div className="panel-header">
            <span className="login-eyebrow">AUTENTICAÇÃO INSTITUCIONAL (UC01)</span>
            <h2>Bem-vindo de volta</h2>
            <p>Insira suas credenciais institucionais para acessar o seu painel.</p>
          </div>

          {erro && (
            <div className="login-alert error">
              <span>⚠️</span>
              <p>{erro}</p>
            </div>
          )}

          <form onSubmit={handleSubmit} className="login-form">
            <div className="input-field">
              <label htmlFor="login-id">Matrícula, SIAPE ou E-mail Institucional</label>
              <div className="input-box">
                <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                  <circle cx="12" cy="8" r="4" />
                  <path d="M4 21a8 8 0 0 1 16 0" />
                </svg>
                <input
                  id="login-id"
                  type="text"
                  placeholder="Ex: 2023101001 ou mateus@aluno.uepb.edu.br"
                  value={identificador}
                  onChange={(e) => setIdentificador(e.target.value)}
                  autoFocus
                />
              </div>
            </div>

            <div className="input-field">
              <div className="label-with-link">
                <label htmlFor="login-pass">Senha de Acesso</label>
                <button
                  type="button"
                  className="link-btn"
                  onClick={() => {
                    setEsqueciSenhaOpen(true);
                    setRecuperacaoEnviada(false);
                  }}
                >
                  Esqueci minha senha
                </button>
              </div>
              <div className="input-box">
                <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                  <rect x="4" y="10" width="16" height="11" rx="2" />
                  <path d="M8 10V7a4 4 0 0 1 8 0v3" />
                </svg>
                <input
                  id="login-pass"
                  type={showPassword ? "text" : "password"}
                  placeholder="Digite sua senha institucional"
                  value={senha}
                  onChange={(e) => setSenha(e.target.value)}
                />
                <button
                  type="button"
                  className="toggle-pass"
                  onClick={() => setShowPassword(!showPassword)}
                  title={showPassword ? "Ocultar senha" : "Ver senha"}
                >
                  {showPassword ? "🙈" : "👁️"}
                </button>
              </div>
            </div>

            <div className="form-remember">
              <label>
                <input
                  type="checkbox"
                  checked={lembrar}
                  onChange={(e) => setLembrar(e.target.checked)}
                />
                <span>Lembrar meu usuário neste navegador</span>
              </label>
            </div>

            <button type="submit" className="login-btn-submit" disabled={isLoading}>
              {isLoading ? "Validando credenciais..." : "Entrar no Sistema"}
            </button>
          </form>

          {/* Atalho de Demonstração / Perfis */}
          <div className="demo-shortcuts">
            <div className="divider-label">
              <span>OU ACESSE RAPIDAMENTE COMO UM PERFIL (TESTE)</span>
            </div>

            <div className="demo-pills-grid">
              {(Object.keys(USUARIOS_DEMO) as PerfilUsuario[]).map((p) => {
                const u = USUARIOS_DEMO[p];
                return (
                  <button
                    key={p}
                    type="button"
                    className={`demo-pill pill-${p.toLowerCase()}`}
                    onClick={() => selecionarPerfilRapido(p)}
                    title={`Entrar diretamente como ${u.nome}`}
                  >
                    <span className="pill-role">{p}</span>
                    <span className="pill-name">{u.nome.split(" ")[0]}</span>
                  </button>
                );
              })}
            </div>
          </div>

          <div className="login-security-notice">
            <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
              <path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z" />
            </svg>
            <span>Ambiente seguro protegido por criptografia e autenticação por tokens (JWT).</span>
          </div>
        </div>
      </div>

      {/* Modal de Esqueci a Senha (UC01 - Fluxo A2) */}
      {esqueciSenhaOpen && (
        <div className="login-modal-overlay" onClick={() => setEsqueciSenhaOpen(false)}>
          <div className="login-modal-card" onClick={(e) => e.stopPropagation()}>
            <div className="modal-header">
              <h3>Recuperação de Senha (UC01)</h3>
              <button onClick={() => setEsqueciSenhaOpen(false)}>✕</button>
            </div>

            {!recuperacaoEnviada ? (
              <form onSubmit={handleRecuperarSenha} className="modal-form">
                <p>
                  Informe seu e-mail institucional cadastrado. O SCA enviará um link de
                  redefinição seguro com validade de 30 minutos via Serviço de E-mail.
                </p>
                <div className="input-field">
                  <label>E-mail Institucional</label>
                  <input
                    type="email"
                    placeholder="exemplo@aluno.uepb.edu.br"
                    value={recuperacaoEmail}
                    onChange={(e) => setRecuperacaoEmail(e.target.value)}
                    required
                  />
                </div>
                <div className="modal-actions">
                  <button type="button" className="btn-cancel" onClick={() => setEsqueciSenhaOpen(false)}>
                    Cancelar
                  </button>
                  <button type="submit" className="btn-confirm">
                    Enviar Link de Redefinição
                  </button>
                </div>
              </form>
            ) : (
              <div className="modal-success">
                <div className="success-icon">✉️</div>
                <h4>Instruções enviadas!</h4>
                <p>
                  Se o e-mail informado estiver cadastrado, um link de redefinição de
                  senha foi enviado para <b>{recuperacaoEmail}</b>. Verifique sua caixa de entrada.
                </p>
                <button type="button" className="btn-confirm" onClick={() => setEsqueciSenhaOpen(false)}>
                  Entendido
                </button>
              </div>
            )}
          </div>
        </div>
      )}
    </div>
  );
}
