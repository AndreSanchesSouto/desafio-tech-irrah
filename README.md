# Projeto Fullstack - Big Chat Brasil (BBC)

Esse projeto foi desenvolvido para se aplicar na vaga de desenvolvedor Full-Stack. Ele leva em conta a proposta apresentada no desafio técnico do Grupo Irrah.

O BBC se consiste em uma plataforma de Chat para conversar com clientes, sendo eles PF / PF.  

🌐 **Visão Geral**

O sistema consiste em três componentes principais:

- Frontend: Aplicação React + TypeSript somado ao Vite e TailwindCSS;
- Backend: API Java Spring Boot;
- Banco de Dados: PostgreSQL;

Todos os componentes são orquestrados via Docker Compose para execução simplificada. Basta rodar os seguintes comandos para iniciar os teste:
   ```
   git clone https://github.com/AndreSanchesSouto/desafio-tech-irrah.git
   cd desafio-tech-irrah
   docker-compose up
   ```

⚙️ **Tecnologias Utilizadas - Descrição**

**Frontend**
- React 19.1.0  
- Vite (build tool)  
- TypeScript
- TailwindCSS
- PNPM (gerenciador de pacotes)  
- Axios (comunicação HTTP)  
- Nginx (servidor web)  

**Backend**
- Java 21  
- Spring Boot v3.5.3  
- Spring Data JPA
- Flyway Migration
- Java-jwt
- Maven (gerenciamento de dependências)  
- PostgreSQL Driver  

**Banco de Dados**

- PostgreSQL 16.9
- Neon Serverless Postgres (SQBD)

📂 **Estrutura do Projeto**

Organizei as pastas desse projeto da seguinte forma:
```
├── back-end/../               # Código fonte do backend
├── front-end/../              # Código fonte do frontend
├── docker-compose.yml         # Orquestração de containers
└── README.md                  # Este arquivo (Descrição detalhada do projeto)
```
Separando os ambientes com cada um contendo seu próprio `Dockerfile`

## 💪 Funcionamento do sistema

O sistema conta com a seguinte lógica:
- O primeiro usuário cadastrado no sistema (CPF + nome) acessa o sistema como Administrador
- Todos os outros usuários (PF/PJ) a se cadastraren serão tratados como Usuário Comúm
- O Administrador tem acesso a todos os chats do sistema, mas os usuário comúns somente a um chat (com o ADM)
- O sistema possuí um sistema de relatórios que exibe o gasto com cada mensagem, além de diversificar o valor entre tipo de mensagem (normal | urgente)

Utilizei da funcionalidade de fila para gerenciar as mensagens, e com a resposta ao WebSocket emitida pela API, mas o FrontEnd está com falha para capturar o socket. 

## 🧑‍💻 Relato pessoal

Particularmente, gostei bastante de desenvolver esta aplicação. Achei a proposta desafiadora, principalmente porque nunca havia trabalhado com filas e sockets antes. Minhas experiências anteriores foram focadas no desenvolvimento de plataformas voltadas para a gestão e o controle de estoque, além da localização em tempo real de entregas. No entanto, achei extremamente interessante a forma como se opera e desenvolve esse tipo de plataforma.
