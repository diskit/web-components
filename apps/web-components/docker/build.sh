#! /usr/bin/env bash

rm -fr ./app
cp -a ../app .

docker build -t diskit/resource-server .

rm -fr ./app