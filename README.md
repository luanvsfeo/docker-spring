# docker-spring

## Objetivo
Ter uma aplicação com Spring boot que rode em um container Docker e que se comunique com um banco de dados Postgres tambem presente em um container.


## Tecnologias utilizadas
- Spring Boot 
- H2 (ambiente de teste/desenvolvimento)
- PostgreSQL (ambiente de produção)
- Docker
- JWT 

## Consideração ao rodar o projeto
- Não se esqueça de alterar os arquivos de configuração (docker-compose e application-prod) com os seus respectivos dados de configuração para o banco de dados postgres.
- Para gerar o jar do projeto, é necessario ir ate o build.gradle e executar a função bootJar

## Comandos necessarios

- Postgres separado
```
docker pull postgres

docker run --name some-postgres -e POSTGRES_PASSWORD=mysecretpassword -d postgres
```
- Api separada
```
docker build -t spring-app .

docker run -p 8080:8080 spring-app
```

- Projeto inteiro
```
docker compose up -d
```

## Endpoints

| Url | Metodo| 
|---|--------|
| /user/login | POST |
| /user/create | POST | 
