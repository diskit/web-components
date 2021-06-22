FROM node:lts-alpine as build
WORKDIR /work

COPY ./app/src /work/src
COPY ./app/public /work/public
COPY ./app/types /work/types
COPY ./app/*.* /work/

RUN yarn 
RUN yarn build

FROM nginx:latest

COPY --from=build /work/build/dist/*.js /usr/share/nginx/html/
COPY --from=build /work/build/scripts/_snowpack /usr/share/nginx/html/_snowpack