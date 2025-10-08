# UniClinicas

Este projeto é uma API REST desenvolvido como projeto final da cadeira Desenvolvimento de Sistemas Corporativos. A aplicação tem como objetivo o gerenciamento de clínicas, permitindo que usuários consultem informações como horários de funcionamento, endereços e médicos disponíveis com suas especialidades. A plataforma oferece um sistema completo onde usuários podem se cadastrar, buscar clínicas por município e realizar avaliações, enquanto as próprias clínicas podem gerenciar seus dados, médicos e agendas de atendimento. O README detalha a estrutura de pastas, as rotas da API com suas respectivas permissões por perfil (ADMIN, CLINICA, USER), a seed inicial de um usuário administrador e as instruções para executar o projeto.

## 🗂️ Estrutura de Pastas

O projeto segue a estrutura padrão do Spring Boot:

-   `src/main/java`: Contém todo o código-fonte da aplicação.
    -   `com/projeto/uniClinicas`: Pacote raiz do projeto.
        -   `authentication`: Contém DTOs para autenticação.
        -   `config`: Classes de configuração, como a estratégia do Flyway.
        -   `controller`: Responsável por expor a API REST, recebendo as requisições HTTP.
        -   `dto`: Utilizados para transferir dados entre as camadas da aplicação, além de possuir outra pasta dentro com DTOs para facilitação de criação de objetos.
        -   `enums`: Contém as enumerações, como `UserRole`.
        -   `exception`: Classes de exceções customizadas.
        -   `mapper`: Classes para mapear DTOs para entidades e vice-versa.
        -   `model`: Classes de entidade.
        -   `repository`: Interfaces de operações de acesso a dados.
        -   `security`: Configurações de segurança da aplicação, incluindo filtros e token.
        -   `service`: Lógica de negócio da aplicação.
        -   `validation`: Classes para validações customizadas.
    -   `db/migration`: Migrações (seeds) baseadas em Java para popular o banco de dados.
-   `src/main/resources`: Arquivos de configuração e recursos.
    -   `application.properties`: Arquivo de configuração principal do Spring Boot.
    -   `db/migration`: Scripts de migração SQL do Flyway.
-   `src/test/java`: Contém os testes da aplicação.
-   `pom.xml`: Arquivo de configuração do Maven, com as dependências e configurações do projeto.
## Roles

O sistema possui três tipos de `roles` (perfis de usuário) com diferentes níveis de acesso:

-   **ADMIN**: Administrador do sistema, com acesso total a todas as funcionalidades.
-   **CLINICA**: Perfil destinado a clínicas, com permissões para gerenciar seus próprios dados, médicos e agendamentos.
-   **USER**: Usuário padrão, com permissões para consultar informações, fazer avaliações e interagir com as clínicas.



Ao iniciar a aplicação, o seguinte usuário é criado no banco de dados para fins de teste e demonstração:

### Admin

-   **username**: `admin`
-   **password**: `admin123`
-   **role**: `ADMIN`


## Rotas da API

A seguir estão listadas as rotas da API, organizadas por perfil de usuário (`role`) e com as permissões de acesso para cada uma.

---

### Rotas Públicas

| Rota | Método | Descrição | Controller |
| :--- | :--- | :--- | :--- |
| `/auth/login` | `POST` | Realiza o login do usuário. | `AutenticacaoController` |
| `/auth/register` | `POST` | Registra um novo usuário (USER). | `AutenticacaoController` |

---

### ADMIN

| Rota | Método | Descrição | Controller |
| :--- | :--- | :--- | :--- |
| `/api/municipio` | `POST` | Adiciona um novo município. | `MunicipioController` |
| `/api/clinicas/{clinicaId}` | `PUT` | Atualiza uma clínica existente. | `ClinicaController` |
| `/api/clinicas/{clinicaId}` | `GET` | Retorna uma clínica específica. | `ClinicaController` |
| `/api/clinicas` | `GET` | Lista clínicas por nome. | `ClinicaController` |
| `/api/municipio` | `GET` | Lista as clínicas de um município. | `MunicipioController` |
| `/api/medico/{medicoId}/clinica/{clinicaId}/agendas` | `GET` | Lista a agenda de um médico em uma clínica. | `AgendaClinicaController` |
| `/api/usuarios/{usuarioId}/avaliacoes` | `GET` | Lista todas as avaliações de um usuário. | `AvaliacaoController` |
| `/api/clinicas/{clinicaId}` | `DELETE` | Remove uma clínica. | `ClinicaController` |
| `/api/avaliacoes/{avaliacaoId}` | `DELETE` | Deleta uma avaliação. | `AvaliacaoController` |
| `/api/usuarios/{usuarioId}` | `DELETE` | Deleta um usuário. | `UsuarioComumController` |

---

### CLINICA

| Rota | Método | Descrição | Controller |
| :--- | :--- | :--- | :--- |
| `/api/clinicas` | `POST` | Adiciona uma nova clínica. | `ClinicaController` |
| `/api/agendas` | `POST` | Adiciona uma nova agenda para uma clínica. | `AgendaClinicaController` |
| `/api/clinicas/{clinicaId}/enderecos` | `PUT` | Atualiza o endereço de uma clínica. | `ClinicaController` |
| `/api/medicos` | `PUT` | Atualiza um médico existente. | `MedicoController` |
| `/api/agendas`| `PUT` | Atualiza a agenda de uma clínica. | `AgendaClinicaController` |
| `/api/medicos/{medicoId}` | `GET` | Busca um médico específico. | `MedicoController` |
| `/api/clinicas/{clinicaId}/avaliacoes-media` | `GET` | Calcula a avaliação média de uma clínica. | `AvaliacaoController` |
| `/api/medicos/{medicoId}` | `DELETE` | Deleta um médico. | `MedicoController` |
| `/api/agendas/{agendaId}` | `DELETE` | Remove uma agenda clínica. | `AgendaClinicaController` |

---

### USER

| Rota | Método | Descrição | Controller |
| :--- | :--- | :--- | :--- |
| `/api/avaliacao/{clinicaId}/clinica` | `POST` | Cria uma avaliação para uma clínica. | `UsuarioComumController` |
| `/api/enderecos` | `POST` | Encontra uma clínica pelo endereço. | `ClinicaController` |
| `/api/usuarios`| `PUT` | Atualiza os dados de um usuário. | `UsuarioComumController` |
| `/api/clinicas/{clinicaId}` | `GET` | Retorna uma clínica específica. | `ClinicaController` |
| `/api/clinicas`| `GET` | Lista clínicas por nome. | `ClinicaController` |
| `/api/municipio` | `GET` | Lista as clínicas de um município. | `MunicipioController` |
| `/api/medicos/{medicoId}` | `GET` | Busca um médico específico. | `MedicoController` |
| `/api/agendas`| `GET` | Lista a agenda de uma clínica. | `AgendaClinicaController` |
| `/api/medico/{medicoId}/clinica/{clinicaId}/agendas` | `GET` | Lista a agenda de um médico em uma clínica. | `AgendaClinicaController` |
| `/api/clinicas/{clinicaId}/avaliacoes` | `GET` | Lista todas as avaliações de uma clínica. | `AvaliacaoController` |
| `/api/clinicas/{clinicaId}/avaliacoes-media` | `GET` | Calcula a avaliação média de uma clínica. | `AvaliacaoController` |
| `/api/avaliacoes/{avaliacaoId}` | `DELETE` | Deleta uma avaliação. | `AvaliacaoController` |


## Siga os passos abaixo para clonar e executar a aplicação.

### 1\. Clonar o Repositório

Utilize o comando abaixo no terminal para clonar o projeto:

```
git clone <URL_DO_SEU_REPOSITÓRIO>
cd uniClinicas
```

### 2\. Configurar o Banco de Dados (PostgreSQL)

Antes de iniciar a aplicação, certifique-se de que você tem o PostgreSQL instalado e em execução.

1.  Crie um banco de dados no PostgreSQL com o nome `uniclinicas`.
2.  Abra o arquivo `src/main/resources/application.properties`.
3.  Modifique as seguintes linhas com suas credenciais do PostgreSQL:
    ```properties
    spring.datasource.username=seuusuariopostgresql
    spring.datasource.password=senhausuario
    ```

##### **Aviso:** No perfil de desenvolvimento (`development`), a configuração do Flyway está definida para limpar (`clean`) e recriar (`migrate`) o banco de dados a cada reinicialização da aplicação. Isso significa que **todos os dados inseridos serão perdidos** sempre que o servidor for reiniciado.

### 3\. Iniciar o servidor pela IDE

Com o banco de dados configurado, você pode iniciar o servidor de aplicação diretamente pela sua IDE (IntelliJ, Eclipse, etc.).

### 4\. Acessar os Recursos

Uma vez que o servidor estiver em execução na porta padrão (`8080`), você pode acessar a documentação da API:

| Recurso | URL |
| :--- | :--- |
| **Documentação da API (Swagger UI)** | `http://localhost:8080/swagger-ui/index.html#/` |