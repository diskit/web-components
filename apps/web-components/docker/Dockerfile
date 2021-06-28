FROM node:lts-alpine as build
WORKDIR /work

COPY ./app/package.json ./app/yarn.lock ./
RUN yarn

COPY ./app/src ./src
COPY ./app/public ./public
COPY ./app/types ./types
COPY ./app/*.* ./

RUN yarn build

FROM nginx:latest

COPY --from=build /work/build/dist/*.js /usr/share/nginx/html/
COPY --from=build /work/build/scripts/_snowpack /usr/share/nginx/html/_snowpack