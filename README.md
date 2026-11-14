🎓 SCA — Sistema de Controle Acadêmico

Sistema de Controle Acadêmico desenvolvido pela empresa Luna para a Universidade Estadual da Paraíba (UEPB) — Campus I, com o objetivo de centralizar e facilitar o gerenciamento das atividades acadêmicas e institucionais.

O sistema é composto por um backend desenvolvido com Spring Boot e um frontend desenvolvido com React, integrados a um banco de dados MySQL.

📌 Tecnologias utilizadas
Backend
Java 17
Spring Boot 3.2.5
Spring Data JPA
Hibernate
MySQL 8+
Flyway
JWT (JSON Web Token)
Maven
Frontend
React 19
Vite 8
TypeScript
Tailwind CSS v4
npm / pnpm
📑 Índice
Visão Geral
Pré-requisitos
Instalação e Configuração do MySQL
Configuração do Banco de Dados
Execução do Backend
Execução do Frontend
Variáveis de Ambiente
Estrutura do Projeto
Solução de Problemas
Comandos Rápidos
🌐 Visão Geral

O SCA possui uma arquitetura dividida em três componentes principais:

Módulo	Tecnologia	Porta	Diretório
Backend	Spring Boot 3.2.5 / Java 17	8080	backend/
Frontend	React 19 / Vite 8	8443	frontend/
Banco de Dados	MySQL 8+	3306	—
Comunicação entre os módulos
Backend: disponibiliza uma API REST em http://localhost:8080/api.
Frontend: consome a API REST e disponibiliza a interface web por meio do servidor de desenvolvimento Vite.
Banco de Dados: armazena as informações acadêmicas e institucionais do sistema.
📋 Pré-requisitos

Antes de executar o projeto, certifique-se de que as seguintes ferramentas estejam instaladas:

Ferramenta	Versão mínima	Comando de verificação
Java (JDK)	17	java -version
Maven	3.8+	mvn -version
Node.js	18+	node -v
npm	9+	npm -v
pnpm	8+	pnpm -v
MySQL Server	8.0+	mysql --version
Git	2.30+	git --version
Instalação das ferramentas

Windows

Java JDK 17 — Adoptium
Java JDK — Oracle
Apache Maven
Node.js
MySQL Installer

Linux (Ubuntu/Debian)

bash
sudo apt update
sudo apt install openjdk-17-jdk maven nodejs npm mysql-server


macOS

bash
brew install openjdk@17 maven node mysql

🗄️ 1. Instalação e Configuração do MySQL
Instale o MySQL Server 8.0 ou superior.
Durante a instalação, configure uma senha para o usuário root.
Inicie o serviço do MySQL.

Windows

Abra o gerenciador de serviços (services.msc), localize o serviço MySQL80 e clique em Iniciar.

Linux

bash
sudo systemctl start mysql
sudo systemctl enable mysql


macOS

bash
brew services start mysql

Testando a conexão

Execute o seguinte comando no terminal:

bash
mysql -u root -p


Informe a senha configurada durante a instalação.

Se a conexão for estabelecida e o terminal apresentar o prompt mysql>, o MySQL estará funcionando corretamente.

🗄️ 2. Configuração do Banco de Dados

O backend utiliza um banco de dados chamado sca_uepb.

Conecte-se ao MySQL e execute:

sql
CREATE DATABASE sca_uepb
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

Criando um usuário exclusivo para o projeto

Recomenda-se utilizar um usuário específico para a aplicação, em vez de utilizar diretamente o usuário root.

sql
CREATE USER 'sca'@'localhost'
IDENTIFIED BY 'sca123';

GRANT ALL PRIVILEGES ON sca_uepb.*
TO 'sca'@'localhost';

FLUSH PRIVILEGES;

Observação: O Flyway está desabilitado por padrão (spring.flyway.enabled: false) no arquivo application.yml. Atualmente, a criação e atualização das tabelas são realizadas pelo Hibernate, por meio da configuração ddl-auto: update.
⚙️ 3. Execução do Backend

Abra um terminal e acesse o diretório backend/:

powershell
cd backend

3.1. Configuração das credenciais do banco

Abra o arquivo:

text
backend/src/main/resources/application.yml


Verifique as configurações de conexão com o MySQL:

yaml
spring:
  datasource:
    url: ${SPRING_DATASOURCE_URL:jdbc:mysql://localhost:3306/sca_uepb?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&characterEncoding=utf-8&useUnicode=true}
    username: ${SPRING_DATASOURCE_USERNAME:root}
    password: ${SPRING_DATASOURCE_PASSWORD:root}
    driver-class-name: com.mysql.cj.jdbc.Driver


Caso tenha criado o usuário dedicado sca, configure as credenciais por meio das variáveis de ambiente.

3.2. Executando a aplicação

Para compilar e iniciar o backend:

bash
mvn spring-boot:run


Alternativamente, para gerar o arquivo JAR:

bash
mvn clean package


Depois, execute:

bash
java -jar target/sca-backend-1.0.0.jar


Após a inicialização, a API estará disponível em:

text
http://localhost:8080/api

3.3. Primeiro acesso

O sistema não realiza a criação automática de um administrador.

O primeiro usuário administrador deve ser criado por meio do painel administrativo do frontend, na aba Painel Institucional, ou diretamente no banco de dados, respeitando os relacionamentos entre as tabelas:

usuario
administrador_geral
professor
aluno
💻 4. Execução do Frontend

Abra um segundo terminal e acesse o diretório frontend/:

powershell
cd frontend

4.1. Instalando as dependências

Utilizando npm:

bash
npm install


Ou utilizando pnpm:

bash
pnpm install

4.2. Executando em ambiente de desenvolvimento

Com as dependências instaladas, execute:

bash
npm run dev


Ou:

bash
pnpm dev


O Vite iniciará o servidor de desenvolvimento.

Por padrão, o frontend utiliza a porta 8443, configurada por meio da variável de ambiente PORT.

Acesse o endereço apresentado no terminal, geralmente:

text
http://localhost:8443

Importante: O frontend consome a API disponibilizada em http://localhost:8080/api, configurada em frontend/src/services/api.ts. Portanto, certifique-se de que o backend esteja em execução.
4.3. Gerando o build de produção

Para gerar os arquivos otimizados para produção:

bash
npm run build


Ou:

bash
pnpm build


Os arquivos serão gerados no diretório:

text
frontend/dist/


O conteúdo desse diretório pode ser hospedado em servidores HTTP, como:

Nginx
Apache
IIS
🔐 Variáveis de Ambiente
Backend

As configurações podem ser personalizadas por meio de variáveis de ambiente.

Variável	Valor padrão	Descrição
SPRING_DATASOURCE_URL	URL JDBC do MySQL	Endereço de conexão com o banco
SPRING_DATASOURCE_USERNAME	root	Usuário do banco de dados
SPRING_DATASOURCE_PASSWORD	root	Senha do banco de dados
SERVER_PORT	8080	Porta de execução do backend
Frontend
Variável	Valor padrão	Descrição
PORT	8443	Porta do servidor de desenvolvimento Vite
API_BASE_URL	http://localhost:8080/api	Endereço da API, atualmente definido diretamente em api.ts
Configurando variáveis no Windows (PowerShell)
powershell
$env:SPRING_DATASOURCE_USERNAME="sca"
$env:SPRING_DATASOURCE_PASSWORD="sca123"

mvn spring-boot:run

Configurando variáveis no Linux/macOS
bash
export SPRING_DATASOURCE_USERNAME=sca
export SPRING_DATASOURCE_PASSWORD=sca123

mvn spring-boot:run

📁 Estrutura do Projeto

A organização dos diretórios está apresentada abaixo:

text
SCA - Sistema de Controle Academico/
│
├── backend/
│   ├── pom.xml
│   │
│   └── src/
│       ├── main/
│       │   ├── java/
│       │   │   └── br/uepb/sca/
│       │   │       ├── controllers/
│       │   │       ├── services/
│       │   │       ├── models/
│       │   │       ├── repositories/
│       │   │       └── security/
│       │   │
│       │   └── resources/
│       │       └── application.yml
│       │
│       └── test/
│
├── frontend/
│   ├── package.json
│   ├── vite.config.ts
│   ├── tsconfig.json
│   ├── index.html
│   │
│   ├── src/
│   │   ├── main.tsx
│   │   ├── App.tsx
│   │   ├── index.css
│   │   │
│   │   ├── services/
│   │   │   └── api.ts
│   │   │
│   │   ├── components/
│   │   │
│   │   ├── context/
│   │   │   └── AuthContext.tsx
│   │   │
│   │   └── types/
│   │       └── auth.ts
│   │
│   └── dist/
│
└── README.md

Descrição dos principais diretórios

Backend

controllers/: gerenciamento dos endpoints da API REST.
services/: implementação das regras de negócio.
models/: entidades e modelos utilizados pela aplicação.
repositories/: acesso e persistência dos dados.
security/: configurações de segurança e autenticação JWT.
resources/: arquivos de configuração da aplicação.

Frontend

components/: componentes e telas da interface.
services/: comunicação com a API REST.
context/: gerenciamento de estados globais, incluindo autenticação.
types/: definições de tipos e interfaces TypeScript.
🛠️ Solução de Problemas
1. Erro DuplicateKeyException: found duplicate key encoding

Causa: existência de uma chave encoding: duplicada no arquivo application.yml.

Solução: remova o bloco duplicado e mantenha apenas uma configuração encoding: dentro da seção server:.

2. Caracteres estranhos na interface

Exemplos:

text
ðŸ›ï¸
Ã§Ã£o


Causa: arquivos-fonte salvos com codificação incorreta, como UTF-8 interpretado como Latin-1.

Solução: certifique-se de que o editor esteja utilizando a codificação UTF-8.

O arquivo frontend/src/components/AdminPanel.tsx já foi corrigido para UTF-8 puro.

3. Backend não conecta ao MySQL

Verifique os seguintes pontos:

O serviço do MySQL está em execução.
O banco sca_uepb foi criado corretamente.
As credenciais estão configuradas corretamente no application.yml ou nas variáveis de ambiente.
O usuário possui permissões de acesso ao banco.

Para testar a conexão:

bash
mysql -u root -p

4. Frontend não carrega dados ou apresenta erro CORS

Possíveis causas: backend desligado ou origem do frontend não autorizada pelo CORS.

Soluções:

Confirme que o backend está disponível em http://localhost:8080.
Verifique a configuração de CORS no SecurityConfig do backend.
Caso esteja utilizando outra porta, adicione a origem correspondente em CorsConfiguration.setAllowedOrigins.

As origens já permitidas incluem:

text
http://localhost:5173
http://localhost:3000
http://localhost:8443
http://127.0.0.1:8443

5. Porta 8080 ou 8443 já está em uso

Para alterar a porta do backend, modifique a propriedade server.port no application.yml.

Para alterar a porta do frontend no PowerShell:

powershell
$env:PORT="5173"
npm run dev

6. Erro de compilação Java (unsupported class version)

Causa: utilização de uma versão incompatível do Java.

Solução: instale e configure o JDK 17.

Verifique as versões instaladas:

bash
java -version
javac -version


Caso existam múltiplas versões, ajuste a variável de ambiente JAVA_HOME.

7. Erro de dependências ou node_modules ausente

Causa: dependências do frontend ainda não foram instaladas.

Solução: acesse a pasta frontend/ e execute:

bash
npm install


Depois, inicie o projeto:

bash
npm run dev

🚀 Comandos Rápidos

Para facilitar a inicialização do projeto no Windows, utilize os comandos abaixo.

Backend
powershell
cd backend
mvn spring-boot:run

Frontend

Abra um segundo terminal:

powershell
cd frontend
npm install
npm run dev

✅ Conclusão

Com o backend, frontend e banco de dados devidamente configurados e em execução, o Sistema de Controle Acadêmico estará pronto para utilização.

Por meio do painel institucional, será possível acessar as funcionalidades de gerenciamento acadêmico, incluindo:

Gerenciamento de cursos.
Gerenciamento de disciplinas.
Cadastro e gerenciamento de professores.
Cadastro e gerenciamento de alunos.
Administração institucional.
Autenticação e controle de acesso por JWT.

SCA — Sistema de Controle Acadêmico

Universidade Estadual da Paraíba (UEPB) — Campus I
