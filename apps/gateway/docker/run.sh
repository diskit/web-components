#! /usr/bin/env bash

cwd=$(cd $(dirname $0); pwd)

docker run --rm -d \
  -e KONG_DECLARATIVE_CONFIG="/etc/kong-nginx/kong.yml" \
  -e KONG_DATABASE="off" \
  -v $PWD/kong.yml:/etc/kong-nginx/kong.yml \
  -v $PWD/kong.conf:/etc/kong/kong.conf \
  -p 8080:8000 \
  --name kong-gateway \
  kong:2.4.0-ubuntu