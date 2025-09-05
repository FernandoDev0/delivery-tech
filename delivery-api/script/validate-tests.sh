#!/bin/bash

echo "🚀 VALIDAÇÃO DE AUTOMAÇÃO DE TESTES - DELIVERY API"
echo "=================================================="
echo ""
echo "📊 Executando bateria completa de testes..."

# Vai para a raiz do projeto (assumindo que o script está em uma subpasta)
cd "$(dirname "$0")/.." || exit 1

# Executa testes Maven e salva log
mvn test > test_results.log 2>&1
test_exit_code=$?

echo "🔍 Debug: Verificando arquivo de log..."
if [ -f "test_results.log" ]; then
    echo "✅ Arquivo test_results.log encontrado"
    echo "📄 Primeiras linhas do log:"; head -5 test_results.log
    echo "📄 Últimas linhas do log:"; tail -5 test_results.log
else
    echo "❌ Arquivo test_results.log NÃO encontrado"
    ls -la | head -10
fi

# Resultado geral
if [ $test_exit_code -eq 0 ]; then
    echo "✅ Todos os testes passaram!"
else
    echo "❌ Alguns testes falharam:"
    grep -E "(FAILURE|ERROR|BUILD FAILURE)" test_results.log | tail -5
fi

# Estatísticas básicas
if [ -f "test_results.log" ]; then
    total_tests=$(grep -o "Tests run: [0-9]*" test_results.log | awk '{sum += $3} END {print sum}')
    echo "Total de testes executados: ${total_tests:-0}"
else
    echo "Total de testes executados: N/A (log não encontrado)"
fi

echo "Tempo de execução: $(date)"
echo "✅ AUTOMAÇÃO COMPLETA FINALIZADA"
echo "📝 Arquivo test_results.log mantido para análise"

# Retorna código de saída para CI/CD
exit $test_exit_code
