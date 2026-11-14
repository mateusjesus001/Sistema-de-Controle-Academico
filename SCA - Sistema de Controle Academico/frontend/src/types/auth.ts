export type PerfilUsuario =
  | "ALUNO"
  | "MONITOR"
  | "PROFESSOR"
  | "COORDENADOR"
  | "SECRETARIO"
  | "ADMIN";

export interface UsuarioLogado {
  id: number;
  nome: string;
  email: string;
  cpf: string;
  perfil: PerfilUsuario;
  identificador: string; // Matrícula ou SIAPE ou Código
  curso?: string;
  semestreAtual?: string;
  coeficienteRendimento?: number;
  percentualIntegralizacao?: number;
  horasConcluidas?: number;
  horasTotais?: number;
  departamento?: string;
  token?: string;
  notifEmail: boolean;
  notifSistema: boolean;
}
