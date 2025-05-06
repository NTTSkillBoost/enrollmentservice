#!/bin/bash

API_URL=http://localhost:8881/api/v1/employees

echo "======================================"
echo "1️⃣ Criar funcionário (POST /register)"
echo "======================================"

curl -X POST $API_URL \
  -H "Content-Type: application/json" \
  -d '{
    "name": "João Silva",
    "email": "joao.silva@example.com",
    "cargo": "Analista",
    "departamento": "TI",
    "dataContratacao": "2024-05-01",
    "status": "ACTIVE"
  }'
echo -e "\n"

echo "======================================"
echo "2️⃣ Listar todos os funcionários (GET /)"
echo "======================================"

curl -X GET $API_URL
echo -e "\n"

echo "======================================"
echo "3️⃣ Buscar funcionário por ID (GET /{id})"
echo "======================================"
# Troque o ID abaixo pelo ID retornado no cadastro (copie o UUID gerado)
EMPLOYEE_ID="COLE_AQUI_O_ID_DO_FUNCIONARIO"

curl -X GET $API_URL/$EMPLOYEE_ID
echo -e "\n"

echo "======================================"
echo "4️⃣ Atualizar funcionário (PUT /{id})"
echo "======================================"

curl -X PUT $API_URL/$EMPLOYEE_ID \
  -H "Content-Type: application/json" \
  -d '{
    "name": "João da Silva",
    "email": "joao.silva@example.com",
    "cargo": "Senior Analyst",
    "departamento": "TI",
    "dataContratacao": "2024-05-01",
    "status": "ACTIVE"
  }'
echo -e "\n"

echo "======================================"
echo "5️⃣ Deletar funcionário (DELETE /{id})"
echo "======================================"

curl -X DELETE $API_URL/$EMPLOYEE_ID
echo -e "\n"
