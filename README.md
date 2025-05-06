Swagger UI: http://192.168.100.197:8881/api/swagger-ui/index.html
OpenAPI JSON: http://192.168.100.197:8881/api/v3/api-docs

# Gerar o arquivo openapi.yaml
curl http://localhost:8881/api/v3/api-docs.yaml -o openapi.yaml

✅ Retry realmente tenta 3 vezes.
✅ CircuitBreaker abre após falhas.
✅ Bulkhead limita concorrência.
✅ Fallback é acionado quando necessário.


# employeeservice
versão com application properties sem config server

==
/src
├── application (Casos de Uso)
├── domain (Entidades e regras de negócio)
├── infrastructure (Banco de dados e APIs externas)
├── adapters (Controllers, Repositories, etc.)
├── ports (Interfaces de comunicação)

Isso garante baixo acoplamento e alta testabilidade.

docker exec -it enrollment-service-postgres psql -U postgres
CREATE DATABASE "nkbost-enrollment-db";

\l==
Aqui estão alguns comandos úteis para trabalhar com o PostgreSQL no cliente psql:
Conexão e Navegação
Conectar a um banco de dados:
psql -U <usuario> -d <nome_do_banco>
Listar bancos de dados:
\l
Conectar a um banco de dados específico:
\c <nome_do_banco>
Listar tabelas no banco de dados atual:
\dt
Listar esquemas:
\dn
Consultas e Manipulação de Dados
Criar um banco de dados:
CREATE DATABASE nome_do_banco;
Criar uma tabela:
CREATE TABLE nome_tabela (
id SERIAL PRIMARY KEY,
nome VARCHAR(100),
idade INT
);
Inserir dados:
INSERT INTO nome_tabela (nome, idade) VALUES ('João', 30);
Consultar dados:
SELECT * FROM nome_tabela;
Atualizar dados:
UPDATE nome_tabela SET idade = 31 WHERE nome = 'João';
Deletar dados:
DELETE FROM nome_tabela WHERE nome = 'João';
Informações e Ajuda
Exibir estrutura de uma tabela:
\d nome_tabela
Sair do cliente psql:
\q# courseservice

- application local - > 0.0.1-application-properties-local# activitymanagementservice
# enrollmentservice
