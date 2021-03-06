#!/usr/bin/env bash

set -eu

port=${1:-8082}
cwd=$(realpath $(dirname $0))

docker run --rm -it \
  --name mock-server \
  -v $cwd/data:/home/wiremock \
  -p ${port}:8080 \
  rodolpheche/wiremock --verbose
