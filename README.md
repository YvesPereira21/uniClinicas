# UniClinicas

Este projeto é uma API REST para gerenciamento de clínicas, médicos, usuários e agendamentos.

## 🗂️ Estrutura de Pastas

-   `src/main/java`: Contém todo o código-fonte da aplicação.
    -   `com/projeto/uniClinicas`: Pacote raiz do projeto.
        -   `authentication`: Contém DTOs para autenticação.
        -   `controller`: Responsável por expor a API REST, recebendo as requisições HTTP.
        -   `dto`: Utilizados para transferir dados entre as camadas da aplicação e contém outros DTOs em uma pasta dentro para auxiliar na criação de objetos.
        -   `enums`: Contém as enumerações, como `UserRole`.
        -   `exception`: Classes de exceções customizadas.
        -   `mapper`: Classes de mapeamento de DTOs para entidades e vice-versa.
        -   `model`: Classes de entidade que representam as tabelas do banco de dados.
        -   `repository`: Interfaces para operações de acesso a dados.
        -   `security`: Configurações de segurança da aplicação, incluindo filtros e serviços de token.
        -   `seeds`: Classe para popular o banco de dados com dados iniciais.
        -   `service`: Contém a lógica de negócio da aplicação.
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

A seguir estão listadas as rotas da API, organizadas por método HTTP e com as permissões de acesso para cada `role`.

### POST

| Rota                                           | Descrição                                         | Roles Permitidas |
| ---------------------------------------------- | ------------------------------------------------- | ---------------- |
| `/auth/login`                                  | Realiza o login do usuário.                       | `permitAll`      |
| `/auth/register`                               | Registra um novo usuário (USER).                  | `permitAll`      |
| `/api/clinicas`                                | Adiciona uma nova clínica.                        | `ADMIN`          |
| `/api/medicos`                                 | Adiciona um novo médico.                          | `CLINICA`        |
| `/api/clinicas/{clinicaId}/agendas`            | Adiciona uma nova agenda para uma clínica.        | `CLINICA`        |
| `/api/clinicas/{clinicaId}/avaliacoes`         | Cria uma avaliação para uma clínica.              | `USER`           |
| `/api/enderecos`                               | Encontra uma clínica pelo endereço.               | `ADMIN`, `USER`  |

### PUT

| Rota                                 | Descrição                                 | Roles Permitidas |
| ------------------------------------ | ----------------------------------------- | ---------------- |
| `/api/clinicas/{clinicaId}`          | Atualiza uma clínica existente.           | `ADMIN`          |
| `/api/clinicas/{clinicaId}/enderecos` | Atualiza o endereço de uma clínica.       | `CLINICA`        |
| `/api/medicos/{medicoId}`            | Atualiza um médico existente.             | `CLINICA`        |
| `/api/usuarios/{usuarioId}`          | Atualiza os dados de um usuário.          | `USER`           |
| `/api/clinicas/{clinicaId}/agendas`  | Atualiza a agenda de uma clínica.         | `CLINICA`        |

### GET

| Rota                                                           | Descrição                                          | Roles Permitidas        |
| -------------------------------------------------------------- | -------------------------------------------------- | ----------------------- |
| `/api/clinicas/{clinicaId}`                                    | Retorna uma clínica específica.                    | `ADMIN`, `CLINICA`, `USER` |
| `/api/clinicas`                                                | Lista clínicas por nome.                           | `ADMIN`, `USER`         |
| `/api/enderecos/{nomeMunicipio}/clinicas`                      | Lista as clínicas de um município.                 | `ADMIN`, `USER`         |
| `/api/medicos/{medicoId}`                                      | Busca um médico específico.                        | `CLINICA`, `USER`       |
| `/api/agendas`                                                 | Lista a agenda de uma clínica.                     | `USER`                  |
| `/api/medicos/{medicoId}/clinicas/{clinicaId}/agendas`         | Lista a agenda de um médico em uma clínica.        | `ADMIN`, `USER`         |
| `/api/usuarios/{usuarioId}/avaliacoes`                         | Lista todas as avaliações de um usuário.           | `ADMIN`                 |
| `/api/clinicas/{clinicaId}/avaliacoes`                         | Lista todas as avaliações de uma clínica.          | `USER`                  |
| `/api/clinicas/{clinicaId}/avaliacoes-media`                   | Calcula a avaliação média de uma clínica.          | `USER`, `CLINICA`       |

### DELETE

| Rota                           | Descrição                    | Roles Permitidas |
| ------------------------------ | ---------------------------- | ---------------- |
| `/api/clinicas/{clinicaId}`    | Remove uma clínica.          | `ADMIN`          |
| `/api/medicos/{medicoId}`      | Deleta um médico.            | `CLINICA`        |
| `/api/agendas/{agendaId}`      | Remove uma agenda clínica.   | `CLINICA`        |
| `/api/avaliacoes/{avaliacaoId}` | Deleta uma avaliação.        | `USER`, `ADMIN`  |
| `/api/usuarios/{usuarioId}`    | Deleta um usuário.           | `ADMIN`  |
