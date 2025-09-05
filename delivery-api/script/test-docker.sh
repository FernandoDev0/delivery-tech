#!/bin/bash
echo "🧪 TESTE AUTOMATIZADO - DELIVERY API"
echo "===================================="

# Aguarda a aplicação subir
echo "⏳ Aguardando aplicação inicializar..."
sleep 45

# Função de teste
test_endpoint() {
    local url=$1
    local expected_status=$2
    local description=$3

    echo -n "🔍 Testando $description... "
    status=$(curl -s -o /dev/null -w "%{http_code}" "$url")

    if [ "$status" = "$expected_status" ]; then
        echo "✅ OK ($status)"
    else
        echo "❌ FALHOU (esperado $expected_status, obtido $status)"
        return 1
    fi
}

# Testes
echo "📊 EXECUTANDO TESTES:"
test_endpoint "http://localhost:8080/actuator/health" "200" "Health Check"
test_endpoint "http://localhost:8080/api/clientes" "401" "Endpoint protegido (sem auth)"

echo -n "🔐 Testando endpoint com autenticação... "
response=$(curl -s -u admin:admin123 -w "%{http_code}" "http://localhost:8080/api/clientes")
status="${response: -3}"
if [ "$status" = "200" ]; then
    echo "✅ OK ($status)"
else
    echo "❌ FALHOU (esperado 200, obtido $status)"
fi

# Resumo dos containers
echo "📋 RESUMO DOS CONTAINERS:"
docker-compose ps

echo "💾 USO DE RECURSOS:"
docker stats --no-stream --format "table {{.Container}}\t{{.CPUPerc}}\t{{.MemUsage}}\t{{.NetIO}}\t{{.PIDs}}"

echo "✅ Testes concluídos!"
