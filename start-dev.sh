#!/bin/bash

set -e

CONTAINER_NAME="netty-example-dev-container"
PROJECT_PATH_IN_CONTAINER="/netty-example"

# Verifica si el contenedor ya existe
if [ "$(docker ps -aq -f name=^/${CONTAINER_NAME}$)" ]; then
  echo "🛠 Contenedor ya existe."

  if [ "$(docker ps -q -f name=^/${CONTAINER_NAME}$)" ]; then
    echo "✅ Contenedor ya está corriendo."
  else
    echo "▶️ Iniciando contenedor..."
    docker start $CONTAINER_NAME
  fi
else
  echo "🚀 Contenedor no existe. Creando con docker-compose..."
  docker-compose up -d
fi

echo "📁 Entrando al proyecto en $PROJECT_PATH_IN_CONTAINER..."
docker exec -it $CONTAINER_NAME bash -c "cd $PROJECT_PATH_IN_CONTAINER && exec bash"
