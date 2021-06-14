#! /usr/bin/env bash

cwd=$(cd $(dirname $0); pwd)

docker rm -f kong-gateway

docker run -d \
  -e KONG_DECLARATIVE_CONFIG="/etc/kong-nginx/kong.yml" \
  -e KONG_DATABASE="off" \
  -e AUTH_ENDPOINT="http://host.docker.internal:8083" \
  -v $PWD/kong.yml:/etc/kong-nginx/kong.yml \
  -v $PWD/kong.conf:/etc/kong/kong.conf \
  -p 8080:8000 \
  --name kong-gateway \
  diskit/gateway