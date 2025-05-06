#!/bin/bash

BASE_URL="http://localhost:8881/api/v1/employees"

echo "==== Teste 1: Requisição normal (deve funcionar) ===="
curl -i -X POST $BASE_URL \
    -H "Content-Type: application/json" \
    -d '{
        "name": "Joao Normal",
        "email": "joao.normal@example.com"
    }'
echo ""
echo "===================================================="

echo "==== Teste 2: Forçar falha para acionar Retry e Fallback ===="
curl -i -X POST $BASE_URL \
    -H "Content-Type: application/json" \
    -d '{
        "name": "FAIL",
        "email": "falha@example.com"
    }'
echo ""
echo "===================================================="

echo "==== Teste 3: Testar Bulkhead com várias requisições concorrentes ===="
for i in {1..12}
do
   curl -s -X POST $BASE_URL \
    -H "Content-Type: application/json" \
    -d "{
        \"name\": \"User $i\",
        \"email\": \"user$i@example.com\"
    }" &
done
wait
echo ""
echo "===================================================="

echo "==== Teste 4: Abrir CircuitBreaker com falhas repetidas ===="
for i in {1..6}
do
    curl -i -X POST $BASE_URL \
        -H "Content-Type: application/json" \
        -d '{
            "name": "FAIL",
            "email": "falha@example.com"
        }'
    echo ""
done
echo "===================================================="
