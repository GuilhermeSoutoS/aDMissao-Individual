# Portal do Membro - Igreja

## Sobre o projeto

O Portal do Membro é uma aplicação web desenvolvida para realizar o cadastro e o gerenciamento dos membros da igreja Assembleia de Deus ministério Missão.

O sistema permite cadastrar membros, visualizar os membros cadastrados e excluir um cadastro.

O projeto foi desenvolvido utilizando uma arquitetura cliente-servidor, onde o frontend é responsável pela interface com o usuário e o backend disponibiliza uma API REST responsável pelo processamento das informações e comunicação com o banco de dados.

### Tecnologias utilizadas

**Frontend**
- HTML
- CSS
- JavaScript

**Backend**
- Java
- Spring Boot
- JdbcTemplate

**Banco de dados**
- H2

---

## Regra de negócio

O sistema tem como objetivo manter os dados dos membros da igreja organizados e permitir que essas informações sejam consultadas e gerenciadas através da aplicação.

Para realizar um cadastro, os seguintes dados são obrigatórios:

- Nome completo
- CPF
- RG
- Data de nascimento
- Cargo na igreja
- CEP
- Estado (UF)
- Rua
- Número
- Bairro
- Cidade
- Telefone
- Gênero

O campo de complemento do endereço é opcional.

### Validações

As informações são validadas tanto no frontend quanto no backend.

No frontend, as validações impedem que o usuário envie o formulário com campos obrigatórios vazios ou com informações inválidas.

No backend, as mesmas regras são verificadas novamente. Isso garante que a API também rejeite requisições inválidas feitas diretamente por ferramentas como Bruno, Postman ou outras aplicações.


---

## Comunicação entre o cliente e a API

O frontend se comunica com o backend através de requisições HTTP utilizando JavaScript e a função `fetch()`.

A API está disponível em:

`http://localhost:8080`

O frontend realiza requisições para os endpoints disponibilizados pelo backend.

### Cadastro de membro

Para cadastrar um novo membro, o frontend realiza uma requisição:

```http
POST /membro