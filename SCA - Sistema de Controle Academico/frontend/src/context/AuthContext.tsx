import { createContext, useContext, useState, useEffect, type ReactNode } from "react";
import type { PerfilUsuario, UsuarioLogado } from "../types/auth";

export const USUARIOS_DEMO: Record<PerfilUsuario, UsuarioLogado> = {
  ALUNO: {
    id: 1,
    nome: "Mateus de Jesus Costa",
    email: "mateus.costa@aluno.uepb.edu.br",
    cpf: "123.456.789-00",
    perfil: "ALUNO",
    identificador: "2023101001",
    curso: "Ciência da Computação",
    semestreAtual: "5º período",
    coeficienteRendimento: 8.45,
    percentualIntegralizacao: 68,
    horasConcluidas: 2176,
    horasTotais: 3200,
    departamento: "Departamento de Sistemas e Computação",
    notifEmail: true,
    notifSistema: true,
    token: "mock-jwt-token-aluno-mateus",
  },
  MONITOR: {
    id: 2,
    nome: "Thiago Silva (Monitor)",
    email: "thiago.silva@aluno.uepb.edu.br",
    cpf: "234.567.890-11",
    perfil: "MONITOR",
    identificador: "2022101045",
    curso: "Ciência da Computação",
    semestreAtual: "7º período",
    coeficienteRendimento: 8.90,
    percentualIntegralizacao: 82,
    horasConcluidas: 2624,
    horasTotais: 3200,
    departamento: "Departamento de Sistemas e Computação",
    notifEmail: true,
    notifSistema: true,
    token: "mock-jwt-token-monitor-thiago",
  },
  PROFESSOR: {
    id: 3,
    nome: "Prof. Dr. Lucas Nóbrega",
    email: "lucas.nobrega@servidor.uepb.edu.br",
    cpf: "345.678.901-22",
    perfil: "PROFESSOR",
    identificador: "SIAPE 1092831",
    departamento: "Departamento de Sistemas e Computação",
    notifEmail: true,
    notifSistema: true,
    token: "mock-jwt-token-prof-lucas",
  },
  COORDENADOR: {
    id: 4,
    nome: "Profa. Dra. Helena Moura",
    email: "helena.moura@servidor.uepb.edu.br",
    cpf: "456.789.012-33",
    perfil: "COORDENADOR",
    identificador: "SIAPE 2039182",
    departamento: "Coordenação de Ciência da Computação",
    notifEmail: true,
    notifSistema: true,
    token: "mock-jwt-token-coord-helena",
  },
  SECRETARIO: {
    id: 5,
    nome: "Carlos Eduardo Andrade",
    email: "carlos.andrade@servidor.uepb.edu.br",
    cpf: "567.890.123-44",
    perfil: "SECRETARIO",
    identificador: "SEC-0041",
    departamento: "Secretaria de Gestão Acadêmica - Campus I",
    notifEmail: true,
    notifSistema: true,
    token: "mock-jwt-token-sec-carlos",
  },
  ADMIN: {
    id: 6,
    nome: "Administrador Central (TI)",
    email: "admin.sca@uepb.edu.br",
    cpf: "000.111.222-33",
    perfil: "ADMIN",
    identificador: "ADM-0001",
    departamento: "Núcleo de Tecnologia da Informação - UEPB",
    notifEmail: true,
    notifSistema: true,
    token: "mock-jwt-token-admin",
  },
};

interface AuthContextType {
  usuario: UsuarioLogado | null;
  isAuthenticated: boolean;
  isLoading: boolean;
  login: (identificador: string, senha: string, perfilSugerido?: PerfilUsuario) => Promise<boolean>;
  logout: () => void;
  selecionarPerfilRapido: (perfil: PerfilUsuario) => void;
}

const AuthContext = createContext<AuthContextType>({} as AuthContextType);

const STORAGE_KEY = "@sca_uepb:user";

export function AuthProvider({ children }: { children: ReactNode }) {
  const [usuario, setUsuario] = useState<UsuarioLogado | null>(null);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    try {
      const stored = localStorage.getItem(STORAGE_KEY);
      if (stored) {
        setUsuario(JSON.parse(stored));
      }
    } catch {
      localStorage.removeItem(STORAGE_KEY);
    } finally {
      setIsLoading(false);
    }
  }, []);

  async function login(identificador: string, _senha: string, perfilSugerido?: PerfilUsuario): Promise<boolean> {
    setIsLoading(true);
    await new Promise((resolve) => setTimeout(resolve, 350));

    // Determina o perfil baseado no identificador ou no perfil sugerido
    let perfil: PerfilUsuario = perfilSugerido || "ALUNO";
    const limpo = identificador.trim().toLowerCase();

    if (limpo.includes("prof") || limpo.includes("lucas") || limpo.startsWith("109")) {
      perfil = "PROFESSOR";
    } else if (limpo.includes("coord") || limpo.includes("helena") || limpo.startsWith("203")) {
      perfil = "COORDENADOR";
    } else if (limpo.includes("sec") || limpo.includes("carlos")) {
      perfil = "SECRETARIO";
    } else if (limpo.includes("monit") || limpo.includes("thiago")) {
      perfil = "MONITOR";
    } else if (limpo.includes("admin")) {
      perfil = "ADMIN";
    }

    const usuarioAutenticado = USUARIOS_DEMO[perfil];
    setUsuario(usuarioAutenticado);
    localStorage.setItem(STORAGE_KEY, JSON.stringify(usuarioAutenticado));
    setIsLoading(false);
    return true;
  }

  function selecionarPerfilRapido(perfil: PerfilUsuario) {
    const usuarioAutenticado = USUARIOS_DEMO[perfil];
    setUsuario(usuarioAutenticado);
    localStorage.setItem(STORAGE_KEY, JSON.stringify(usuarioAutenticado));
  }

  function logout() {
    setUsuario(null);
    localStorage.removeItem(STORAGE_KEY);
  }

  return (
    <AuthContext.Provider
      value={{
        usuario,
        isAuthenticated: !!usuario,
        isLoading,
        login,
        logout,
        selecionarPerfilRapido,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error("useAuth deve ser utilizado dentro de um AuthProvider");
  }
  return context;
}
