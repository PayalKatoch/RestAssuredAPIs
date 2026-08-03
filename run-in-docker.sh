#!/usr/bin/env bash
set -euo pipefail

IMAGE_NAME=restassured-api-tests
WORKDIR=/app

echo "Building Docker image: $IMAGE_NAME"
docker build -t "$IMAGE_NAME" .

echo "Running tests inside Docker container"
docker run --rm \
  -v "$PWD:$WORKDIR" \
  -w "$WORKDIR" \
  "$IMAGE_NAME"
