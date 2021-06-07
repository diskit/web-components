FROM kong:2.4.0-alpine

USER root

RUN luarocks install lua-resty-http
RUN luarocks install luajson

COPY ./kong.conf /etc/kong/

USER kong