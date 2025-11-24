#!/bin/bash

# Script para compilar e executar o Sistema de Academia
# Versão otimizada para evitar problemas de renderização

echo "============================================"
echo "  COMPILANDO SISTEMA DE ACADEMIA"
echo "============================================"

# Limpa compilações anteriores
rm -rf bin
mkdir -p bin

# Compila todos os arquivos Java
echo "Compilando arquivos Java..."
find src -name "*.java" -print | xargs javac -d bin -sourcepath src

# Verifica se compilou com sucesso
if [ $? -eq 0 ]; then
    echo "✓ Compilação concluída com sucesso!"
    echo ""
    echo "============================================"
    echo "  EXECUTANDO SISTEMA"
    echo "============================================"
    echo ""

    # Executa o sistema com configurações otimizadas para Linux
    # -Dawt.useSystemAAFontSettings=on: Melhora renderização de fontes
    # -Dsun.java2d.xrender=true: Usa aceleração por hardware
    java -Dawt.useSystemAAFontSettings=on \
         -Dsun.java2d.xrender=true \
         -cp bin br.trabalho3.sistema.main.Main
else
    echo "✗ Erro na compilação!"
    echo "Verifique os erros acima."
    exit 1
fi
