#!/bin/bash

# --- ATENCIÓN ---
# Este script está diseñado para ser ejecutado DESDE DENTRO de la carpeta
# que contiene la estructura de paquetes (ej: src/main/java).
#
# RECORRERÁ CADA ARCHIVO .java, calculará el paquete correcto según su
# ubicación y FORZARÁ a que esa sea la declaración de paquete.

echo "Iniciando la rectificación forzosa de TODOS los paquetes..."
echo "-----------------------------------------------------------------"

# Busca todos los archivos .java desde la ubicación actual.
find . -type f -name "*.java" -print0 | while IFS= read -r -d $'\0' file; do

    # --- 1. Calcula el paquete correcto a partir de la ruta ---
    dir_path=$(dirname "$file")
    package_path=${dir_path#./}
    correct_package=$(echo "$package_path" | tr '/' '.')

    # Si la clase está en la raíz, no debería tener paquete.
    if [ "$package_path" == "." ]; then
        # Si por error tiene una línea de paquete, se la quitamos.
        sed -i '/^package .*/d' "$file" >/dev/null 2>&1
        continue
    fi

    correct_package_line="package $correct_package;"

    # --- 2. Verifica y actualiza el paquete en el archivo ---

    # Extrae la línea del paquete actual, si existe.
    current_package_line=$(grep -m 1 '^package' "$file")

    if [ -z "$current_package_line" ]; then
        # CASO A: No hay línea de paquete, la insertamos al principio.
        # Se usa una sintaxis compatible con Linux y macOS.
        sed -i "1s|^|${correct_package_line}\n|" "$file"
        echo "✅ AÑADIDO:  $file -> $correct_package"

    elif [ "$current_package_line" != "$correct_package_line" ]; then
        # CASO B: La línea de paquete existe pero es incorrecta, la reemplazamos.
        sed -i "s/^package .*;/$correct_package_line/" "$file"
        echo "🔄 CORREGIDO: $file -> $correct_package"

    # CASO C: La línea es correcta, no se hace nada.
    fi
done

echo "-----------------------------------------------------------------"
echo "✨ ¡Rectificación completada!"
