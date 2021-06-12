#! /usr/bin/env bash

cp -fR ../plugins .

docker build -t diskit/gateway .

rm -fr plugins
