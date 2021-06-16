FROM node:lts-alpine as build
WORKDIR /work

COPY ./app/src /work/app/src
COPY ./app/public /work/app/public
COPY ./app/types /work/app/types
COPY ./app/*.* /work/app/

RUN cd app && yarn && yarn build

FROM nginx:latest

COPY --from=build /work/app/build/dist/*.js /usr/share/nginx/html
COPY --from=build /work/app/build/scripts/_snowpack /usr/share/nginx/html/_snowpack