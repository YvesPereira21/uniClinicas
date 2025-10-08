CREATE TABLE tb_usuario(
    usuario_id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL
);

CREATE TABLE tb_municipio(
    municipio_cep VARCHAR(255) PRIMARY KEY,
    nome_municipio VARCHAR(255) NOT NULL UNIQUE,
    estado VARCHAR(255) NOT NULL
);

CREATE TABLE tb_medico(
    medico_id BIGSERIAL PRIMARY KEY,
    crm_medico VARCHAR(15) NOT NULL UNIQUE,
    nome_medico VARCHAR(80) NOT NULL,
    especialidade VARCHAR(255) NOT NULL
);

CREATE TABLE tb_endereco(
    endereco_id BIGSERIAL PRIMARY KEY,
    logradouro VARCHAR(255) NOT NULL,
    numero VARCHAR(255) NOT NULL,
    complemento VARCHAR(255),
    bairro VARCHAR(255) NOT NULL,
    municipio_cep VARCHAR(255) REFERENCES tb_municipio(municipio_cep)
);

CREATE TABLE tb_clinica(
    clinica_id BIGSERIAL PRIMARY KEY,
    cnpj_clinica VARCHAR(255) NOT NULL UNIQUE,
    nome_clinica VARCHAR(255) NOT NULL,
    telefone INT NOT NULL,
    horario_funcionamento TIME NOT NULL,
    horario_fechamento TIME NOT NULL,
    usuario_id BIGINT REFERENCES tb_usuario(usuario_id),
    endereco_id BIGINT REFERENCES tb_endereco(endereco_id)
);

CREATE TABLE tb_usuario_comum(
    usuariocomum_id BIGSERIAL PRIMARY KEY,
    nome_usuariocomum VARCHAR(255) NOT NULL,
    cpf VARCHAR(255) NOT NULL UNIQUE,
    idade INT NOT NULL,
    sexo VARCHAR(1) CHECK (sexo IN ('M', 'F')),
    email VARCHAR(255) NOT NULL UNIQUE,
    usuario_id BIGINT REFERENCES tb_usuario(usuario_id),
    endereco_id BIGINT REFERENCES tb_endereco(endereco_id)
);

CREATE TABLE tb_avaliacao(
    avaliacao_id BIGSERIAL PRIMARY KEY,
    comentario VARCHAR(255),
    nota DOUBLE PRECISION NOT NULL,
    horario_comentario TIMESTAMP NOT NULL,
    usuariocomum_id BIGINT REFERENCES tb_usuario_comum(usuariocomum_id),
    clinica_id BIGINT REFERENCES tb_clinica(clinica_id)
);

CREATE TABLE tb_agenda_clinica(
    agenda_id BIGSERIAL PRIMARY KEY,
    dia_semana VARCHAR(20) NOT NULL,
    horario_atendimento_medico TIME NOT NULL,
    horario_saida TIME NOT NULL,
    clinica_id BIGINT REFERENCES tb_clinica(clinica_id),
    medico_id BIGINT REFERENCES tb_medico(medico_id)
);