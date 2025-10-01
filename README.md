# UniClinicas

Este projeto é uma API REST para gerenciamento de clínicas, médicos, usuários e agendamentos. No começo são os detalhamentos da estrutura das rotas e pápeis, além das seeds com alguns pápeis. No final, é mostrado como executar o projeto.

## 🗂️ Estrutura de Pastas

O projeto segue a estrutura padrão do Spring Boot:

-   `src/main/java`: Contém todo o código-fonte da aplicação.
    -   `com/projeto/uniClinicas`: Pacote raiz do projeto.
        -   `authentication`: Contém DTOs para autenticação.
        -   `controller`: Responsável por expor a API REST, recebendo as requisições HTTP.
        -   `dto`: Utilizados para transferir dados entre as camadas da aplicação, além de possuir outra pasta dentro com DTOs para facilitação de criação de objetos.
        -   `enums`: Contém as enumerações, como `UserRole`.
        -   `exception`: Classes de exceções customizadas.
        -   `mapper`: Classes para mapear DTOs para entidades e vice-versa.
        -   `model`: Classes de entidade.
        -   `repository`: Interfaces de operações de acesso a dados.
        -   `security`: Configurações de segurança da aplicação, incluindo filtros e token.
        -   `seeds`: Classe para popular o banco de dados com dados iniciais.
        -   `service`: Lógica de negócio da aplicação.
        -   `validation`: Classes para validações customizadas.
-   `src/main/resources`: Arquivos de configuração e recursos.
    -   `application.properties`: Arquivo de configuração principal do Spring Boot.
-   `src/test/java`: Contém os testes da aplicação.
-   `pom.xml`: Arquivo de configuração do Maven, com as dependências e configurações do projeto.

## Roles

O sistema possui três tipos de `roles` (perfis de usuário) com diferentes níveis de acesso:

-   **ADMIN**: Administrador do sistema, com acesso total a todas as funcionalidades.
-   **CLINICA**: Perfil destinado a clínicas, com permissões para gerenciar seus próprios dados, médicos e agendamentos.
-   **USER**: Usuário padrão, com permissões para consultar informações, fazer avaliações e interagir com as clínicas.


## Seeds

Ao iniciar a aplicação, os seguintes usuários são criados no banco de dados para fins de teste e demonstração:

### Admin

-   **username**: `admin`
-   **password**: `admin123`
-   **role**: `ADMIN`

### Clínica

-   **username**: `clinica_unip`
-   **password**: `clinica123`
-   **role**: `CLINICA`

-   **username**: `SouDoto`
-   **password**: `soudoto123`
-   **role**: `CLINICA`

## Rotas da API

A seguir estão listadas as rotas da API, organizadas por método HTTP e com as permissões de acesso para cada `role` atualizadas.

### POST

| Rota | Descrição | Roles Permitidas |
| --- | --- | --- |
| `/auth/login` | Realiza o login do usuário. | `permitAll` |
| `/auth/register` | Registra um novo usuário (USER). | `permitAll` |
| `/auth/change-password` | Permite que um usuário altere sua senha. | `permitAll` |
| `/api/clinicas` | Adiciona uma nova clínica. | `CLINICA` |
| `/api/medicos` | Adiciona um novo médico. | `CLINICA` |
| `/api/clinicas/{clinicaId}/agendas` | Adiciona uma nova agenda para uma clínica. | `CLINICA` |
| `/api/clinicas/{clinicaId}/avaliacoes` | Cria uma avaliação para uma clínica. | `USER` |
| `/api/enderecos` | Encontra uma clínica pelo endereço. | `ADMIN`, `USER` |

### PUT

| Rota | Descrição | Roles Permitidas |
| --- | --- | --- |
| `/api/clinicas/{clinicaId}` | Atualiza uma clínica existente. | `ADMIN` |
| `/api/clinicas/{clinicaId}/enderecos` | Atualiza o endereço de uma clínica. | `CLINICA` |
| `/api/medicos/{medicoId}` | Atualiza um médico existente. | `CLINICA` |
| `/api/usuarios/{usuarioId}` | Atualiza os dados de um usuário. | `USER` |
| `/api/clinicas/{clinicaId}/agendas` | Atualiza a agenda de uma clínica. | `CLINICA` |

### GET

| Rota | Descrição | Roles Permitidas |
| --- | --- | --- |
| `/api/clinicas/{clinicaId}` | Retorna uma clínica específica. | `ADMIN`, `CLINICA`, `USER` |
| `/api/clinicas` | Lista clínicas por nome. | `ADMIN`, `USER` |
| `/api/enderecos/{nomeMunicipio}/clinicas` | Lista as clínicas de um município. | `ADMIN`, `USER` |
| `/api/medicos/{medicoId}` | Busca um médico específico. | `CLINICA`, `USER` |
| `/api/agendas` | Lista a agenda de uma clínica. | `USER` |
| `/api/medicos/{medicoId}/clinicas/{clinicaId}/agendas` | Lista a agenda de um médico em uma clínica. | `ADMIN`, `USER` |
| `/api/usuarios/{usuarioId}/avaliacoes` | Lista todas as avaliações de um usuário. | `ADMIN` |
| `/api/clinicas/{clinicaId}/avaliacoes` | Lista todas as avaliações de uma clínica. | `USER` |
| `/api/clinicas/{clinicaId}/avaliacoes-media` | Calcula a avaliação média de uma clínica. | `USER`, `CLINICA` |

### DELETE

| Rota | Descrição | Roles Permitidas |
| --- | --- | --- |
| `/api/clinicas/{clinicaId}` | Remove uma clínica. | `ADMIN` |
| `/api/medicos/{medicoId}` | Deleta um médico. | `CLINICA` |
| `/api/agendas/{agendaId}` | Remove uma agenda clínica. | `CLINICA` |
| `/api/avaliacoes/{avaliacaoId}` | Deleta uma avaliação. | `USER`, `ADMIN` |
| `/api/usuarios/{usuarioId}` | Deleta um usuário. | `ADMIN` |


## Siga os passos abaixo para clonar e executar a aplicação.

### 1. Clonar o Repositório

Utilize o comando abaixo no terminal para clonar o projeto:

git clone <URL_DO_SEU_REPOSITÓRIO>
cd uniClinicas


### 2. Iniciar o servidor pela IDE


### 3. Acessar os Recursos

Uma vez que o servidor estiver em execução na porta padrão (`8080`), você pode acessar a documentação e o banco de dados conforme a tabela abaixo:

| Recurso | URL | Observação |
| :--- | :--- | :--- |
| **Documentação da API (Swagger UI)** | `http://localhost:8080/swagger-ui/index.html#/` | Use esta interface para explorar e testar todas as rotas da API, além de gerar tokens de autenticação. |
| **Console do Banco de Dados H2** | `http://localhost:8080/h2-ui` | Este é um banco de dados **em memória**. Os dados são perdidos ao reiniciar o servidor por estar configurado como "ddl-auto:create-drop". Use as credenciais padrão para login: **Username: `sa`** e **Password: (vazio)**. |
