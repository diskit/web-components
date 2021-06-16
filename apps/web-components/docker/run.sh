#! /usr/bin/env bash

docker rm -f resource-server
docker run -d --name resource-server -p 8084:80 diskit/resource-server