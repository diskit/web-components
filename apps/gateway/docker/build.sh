#! /usr/bin/env bash

cp -fR ../plugin .

docker build -t diskit/gateway .

rm -fr plugin
