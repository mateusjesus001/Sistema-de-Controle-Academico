# SCA - Sistema de Controle Acadêmico

Sistema de controle acadêmico para a UEPB Campus I, composto por:

- **Backend**: Spring Boot 3.2.5 + Java 17 + JPA/Hibernate + MySQL + Flyway + JWT
- **Frontend**: React 19 + Vite + Tailwind CSS v4

---

## Índice

1. [Visão Geral](#visão-geral)
2. [Pré-requisitos](#pré-requisitos)
3. [Passo 1 — Instalar e configurar o MySQL](#passo-1--instalar-e-configurar-o-mysql)
4. [Passo 2 — Configurar o banco de dados](#passo-2--configurar-o-banco-de-dados)
5. [Passo 3 — Rodar o Backend](#passo-3--rodar-o-backend)
6. [Passo 4 — Rodar o Frontend](#passo-4--rodar-o-frontend)
7. [Variáveis de Ambiente e application.yml](#variáveis-de-ambiente-e-applicationyml)
8. [Estrutura do Projeto](#estrutura-do-projeto)
9. [Troubleshooting](#troubleshooting)

---

## Visão Geral

| Módulo | Tecnologia | Porta | Pasta |
|--------|-----------|-------|-------|
| Backend | Spring Boot 3.2.5, Java 17 | 8080 | `backend/` |
| Frontend | React 19, Vite 8 | 8443 (padrão do Figma Make) | `frontend/` |
| Banco de dados | MySQL 8+ | 3306 | — |

O backend expõe a API REST em `http://localhost:8080/api`.  
O frontend consome essa API e roda em um servidor de desenvolvimento Vite.

---

## Pré-requisitos

Antes de começar, instale na máquina:

| Ferramenta | Versão mínima | Como verificar |
|------------|--------------|----------------|
| **Java (JDK)** | 17 | `java -version` |
| **Maven** | 3.8+ | `mvn -version` |
| **Node.js** | 18+ | `node -v` |
| **npm ou pnpm** | npm 9+ / pnpm 8+ | `npm -v` ou `pnpm -v` |
| **MySQL Server** | 8.0+ | `mysql --version` |
| **Git** | 2.30+ | `git --version` |

### Windows

- **JDK 17**: baixe em https://adoptium.net/ ou https://www.oracle.com/java/technologies/downloads/
- **Maven**: https://maven.apache.org/download.cgi
- **Node.js**: https://nodejs.org/ (recomenda-se a versão LTS)
- **MySQL**: https://dev.mysql.com/downloads/installer/ (use o MySQL Installer for Windows)

### Linux (Ubuntu/Debian)

```bash
sudo apt update
sudo apt install openjdk-17-jdk maven nodejs npm mysql-server
```

### macOS

```bash
brew install openjdk@17 maven node mysql
```

---

## Passo 1 — Instalar e configurar o MySQL

1. Instale o MySQL Server 8.0+.
2. Durante a instalação, defina uma senha para o usuário `root` (anote-a, você vai precisar).
3. Inicie o serviço do MySQL:

   - **Windows**: `services.msc` → procure "MySQL80" → Iniciar
   - **Linux**: `sudo systemctl start mysql` / `sudo systemctl enable mysql`
   - **macOS**: `brew services start mysql`

4. Teste a conexão:

   ```bash
   mysql -u root -p
   ```

   Digite a senha do root. Se entrar no prompt `mysql>`, o banco está OK.

---

## Passo 2 — Configurar o banco de dados

O backend se conecta a um banco chamado `sca_uepb`. Crie-o manualmente (o Hibernate cria as tabelas automaticamente via `ddl-auto: update`):

```sql
CREATE DATABASE sca_uepb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

Se quiser usar um usuário dedicado ao projeto (recomendado):

```sql
CREATE USER 'sca'@'localhost' IDENTIFIED BY 'sca123';
GRANT ALL PRIVILEGES ON sca_uepb.* TO 'sca'@'localhost';
FLUSH PRIVILEGES;
```

> O Flyway está desabilitado por padrão (`spring.flyway.enabled: false`) no `application.yml`. As tabelas são criadas automaticamente pelo Hibernate ao iniciar a aplicação.

---

## Passo 3 — Rodar o Backend

Abra um terminal na pasta `backend/`:

```powershell
cd "C:\Users\mateu\OneDrive\Desktop\Workspace\SCA - Sistema de Controle Academico\backend"
```

### 3.1 Ajustar as credenciais do banco (se necessário)

Edite `backend/src/main/resources/application.yml` se você usou usuário/senha diferentes do padrão:

```yaml
spring:
  datasource:
    url: ${SPRING_DATASOURCE_URL:jdbc:mysql://localhost:3306/sca_uepb?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&characterEncoding=utf-8&useUnicode=true}
    username: ${SPRING_DATASOURCE_USERNAME:root}
    password: ${SPRING_DATASOURCE_PASSWORD:root}
    driver-class-name: com.mysql.cj.jdbc.Driver
```

Você pode definir as variáveis de ambiente `SPRING_DATASOURCE_USERNAME` e `SPRING_DATASOURCE_PASSWORD` no SO em vez de editar o arquivo.

### 3.2 Compilar e rodar

```powershell
mvn spring-boot:run
```

Ou, para gerar o JAR e rodar depois:

```powershell
mvn clean package
java -jar target/sca-backend-1.0.0.jar
```

O backend estará disponível em:

```
http://localhost:8080/api
```

### 3.3 Primeiro acesso / usuários padrão

Não há cadastro automático de administrador. Você deve criar os usuários pelo painel admin do frontend (aba "Painel Institucional") ou inserir diretamente no banco pelas tabelas `usuario`, `administrador_geral`, `professor`, `aluno`, etc.

---

## Passo 4 — Rodar o Frontend

Abra outro terminal na pasta `frontend/`:

```powershell
cd "C:\Users\mateu\OneDrive\Desktop\Workspace\SCA - Sistema de Controle Academico\frontend"
```

### 4.1 Instalar dependências

```powershell
npm install
# ou
pnpm install
```

### 4.2 Rodar em desenvolvimento

```powershell
npm run dev
# ou
pnpm dev
```

O Vite subirá o servidor. Por padrão ele usa a porta **8443** (variável `PORT`).  
Acesse a URL mostrada no terminal (ex: `http://localhost:8443` ou a URL de preview do Figma Make).

> O frontend consome a API em `http://localhost:8080/api` (definido em `frontend/src/services/api.ts`). Certifique-se de que o backend esteja rodando antes de usar o frontend.

### 4.3 Build para produção

```powershell
npm run build
# ou
pnpm build
```

Os arquivos estáticos serão gerados em `frontend/dist/`. Pode ser servido por qualquer servidor HTTP (Nginx, Apache, IIS, etc.).

---

## Variáveis de Ambiente e application.yml

### Backend (`backend/src/main/resources/application.yml`)

| Variável | Padrão | Descrição |
|----------|--------|-----------|
| `SPRING_DATASOURCE_URL` | `jdbc:mysql://localhost:3306/sca_uepb?...` | URL de conexão MySQL |
| `SPRING_DATASOURCE_USERNAME` | `root` | Usuário do MySQL |
| `SPRING_DATASOURCE_PASSWORD` | `root` | Senha do MySQL |
| `SERVER_PORT` | `8080` | Porta do backend |

### Frontend

| Variável | Padrão | Descrição |
|----------|--------|-----------|
| `PORT` | `8443` | Porta do servidor Vite |
| `API_BASE_URL` | `http://localhost:8080/api` | Endereço da API (hardcoded em `api.ts`; edite se o backend estiver em outro host) |

Exemplo no Windows (PowerShell):

```powershell
$env:SPRING_DATASOURCE_USERNAME="sca"
$env:SPRING_DATASOURCE_PASSWORD="sca123"
mvn spring-boot:run
```

Exemplo no Linux/macOS:

```bash
export SPRING_DATASOURCE_USERNAME=sca
export SPRING_DATASOURCE_PASSWORD=sca123
mvn spring-boot:run
```

---

## Estrutura do Projeto

```
SCA - Sistema de Controle Academico/
├── backend/
│   ├── pom.xml                      # Dependências Maven
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/br/uepb/sca/    # Código Java (controllers, services, models, repositórios, security)
│   │   │   └── resources/
│   │   │       └── application.yml  # Configuração Spring Boot
│   │   └── test/                    # Testes
│   └── target/                      # Build gerado (ignorado pelo git)
├── frontend/
│   ├── package.json                 # Dependências Node
│   ├── vite.config.ts               # Configuração Vite
│   ├── tsconfig.json
│   ├── index.html                   # Shell HTML
│   ├── src/
│   │   ├── main.tsx                 # Entrypoint React
│   │   ├── App.tsx                  # Componente principal
│   │   ├── index.css                # Tailwind CSS v4
│   │   ├── services/api.ts          # Cliente da API (fetch)
│   │   ├── components/              # Componentes React (AdminPanel, LoginPage, etc.)
│   │   ├── context/AuthContext.tsx  # Contexto de autenticação JWT
│   │   └── types/auth.ts            # Tipos TypeScript
│   └── dist/                        # Build de produção (ignorado pelo git)
└── README.md
```

---

## Troubleshooting

### 1. Erro `DuplicateKeyException: found duplicate key encoding` no backend

**Causa**: o arquivo `application.yml` tinha a chave `encoding:` duplicada.  
**Solução**: remova o bloco duplicado, deixando apenas um `encoding:` sob `server:`.

### 2. Caracteres estranhos na interface (`ðŸ›ï¸`, `Ã§Ã£o`, etc.)

**Causa**: arquivo fonte salvo com encoding corrompido (UTF-8 lido como Latin-1).  
**Solução**: o arquivo `frontend/src/components/AdminPanel.tsx` foi corrigido para UTF-8 puro. Se você baixar o projeto e vir caracteres estranhos, certifique-se de que o editor está lendo os arquivos como **UTF-8** e não re-salve em outro encoding.

### 3. Backend não conecta no MySQL

- Verifique se o MySQL está rodando: `services.msc` (Windows) ou `systemctl status mysql` (Linux).
- Teste a conexão manual: `mysql -u root -p`.
- Confirme que o banco `sca_uepb` existe.
- Confira usuário/senha no `application.yml` ou variáveis de ambiente.

### 4. Frontend não carrega dados / erro CORS

- Confirme que o backend está rodando em `http://localhost:8080`.
- O `SecurityConfig` do backend já permite CORS para `localhost:5173`, `localhost:3000`, `localhost:8443` e `127.0.0.1:8443`. Se usar outra porta, adicione-a em `CorsConfiguration.setAllowedOrigins`.

### 5. Porta 8080 ou 8443 já em uso

- Mude a porta do backend em `application.yml` (`server.port`).
- Mude a porta do frontend com a variável `PORT`:

  ```powershell
  $env:PORT="5173"
  npm run dev
  ```

### 6. Erro de compilação Java (unsupported class version)

- Você precisa do **JDK 17**. Verifique com `java -version` e `javac -version`. Se tiver múltiplas versões, ajuste o `JAVA_HOME`.

### 7. `node_modules` ausente no frontend

- Rode `npm install` (ou `pnpm install`) na pasta `frontend/` antes de `npm run dev`.

---

## Comandos rápidos (PowerShell / Windows)

```powershell
# Backend
cd backend
mvn spring-boot:run

# Frontend (novo terminal)
cd frontend
npm install
npm run dev
```

---

Pronto! Com o backend e o frontend rodando, você pode acessar a interface, logar, criar cursos, disciplinas, professores, alunos e gerenciar tudo pelo painel institucional.
