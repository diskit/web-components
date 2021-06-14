#! /usr/bin/env bash

cp -fpR ../app .
rm -fr app/src/main/resources app/target app/tmp app/.idea

docker build -t diskit/auth-api .

rm -fr app