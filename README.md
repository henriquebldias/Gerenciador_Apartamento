# Gerenciamento de Apartamentos

Bem vindo ao gerenciador, esta é uma aplicação web para o gerenciamento de apartamentos. Neste sistema você pode criar, consultar, editar e excluir apartamentos.

## Tecnologias utilizadas:

- Backend: Java 17 com Spring Boot e Maven
- Frontend: ReactJS + Vite + Bootstrap
- Banco de dados: PostgresSQL

## Como executar o projeto:

Para executar o projeto, você precisa ter instalado as seguintes tecnologias:
- Java JDK 17
- Node.js (com NPM)

### Backend

É necessário configurar as credenciais do banco de dados no arquivo 'application.properties'.

Para executar o backend em um ambiente Windows, basta rodar o comando abaixo no diretório do projeto:

```sh
mvnw.cmd spring-boot:run
```

Para executar o backend em um ambiente Linux, basta rodar o comando abaixo no diretório do projeto:

```sh
./mvnw spring-boot:run
```

Certifique-se que o banco de dados esteja configurado corretamente. O PostgresSQL deve estar rodando na porta 5432 (padrão).
Ao rodar o backend, a estrutura de tabelas será automaticamente criada no banco de dados pelo Hibernate via DDL.

O backend está disponível em http://localhost:1337/api/apartamento.

### Frontend

Instale as dependências do frontend com o seguinte comando no diretório `frontend`:

```sh
npm install
```

Para executar o frontend, basta rodar o comando abaixo no diretório `frontend` usando outro terminal:

```sh
npm run dev
```

### Banco de Dados

Não é necessário rodar nenhum comando para criação das tabelas, ao rodar o backend a estrutura será criada automaticamente.

Estrutura da tabela utilizada no momento:
```sql
CREATE TABLE public.apartamento (
	numero int4 NULL,
	estado varchar(255) NULL,
	id varchar(255) NOT NULL,
	CONSTRAINT apartamento_estado_check CHECK (((estado)::text = ANY ((ARRAY['LIVRE'::character varying, 'LOCADO'::character varying])::text[]))),
	CONSTRAINT apartamento_numero_key UNIQUE (numero),
	CONSTRAINT apartamento_pkey PRIMARY KEY (id)
);
```

#### Técnicas de BD utilizadas:
- O ID utiliza o sistema não sequencial UUID para melhor segurança.
- Como pedido pelo enunciado, o campo estado é um enum com os valores: LIVRE e LOCADO. Isto será assegurado inclusive pelo próprio SGBD.
- O número do apartamento é único.
