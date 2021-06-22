# FROM diskit/lua-alpine:5.3 as builder

FROM alpine:latest as builder

ENV LUA_VERSION 5.3
ENV LUA_PACKAGE lua${LUA_VERSION}
ENV LUAROCKS_VERSION 3.7.0

RUN apk update && apk add ${LUA_PACKAGE} ${LUA_PACKAGE}-dev build-base git bash zip unzip openssl

RUN cd /tmp \
  && wget https://luarocks.github.io/luarocks/releases/luarocks-${LUAROCKS_VERSION}.tar.gz -O - | tar -xzf - \
  && cd luarocks-${LUAROCKS_VERSION} \
  && sh ./configure \
  && make build install \
  && cd \
  && rm -rf /tmp/luarocks

WORKDIR /app
COPY ./plugins ./plugins
RUN cd /app/plugins/access-control && \
    luarocks --local make --pack-binary-rock

RUN cd /app/plugins/token-upgrader && \
    luarocks --local make --pack-binary-rock

FROM kong:2.4.0-alpine

USER root

RUN luarocks install lua-resty-http && luarocks install luajson

COPY --from=builder /app/plugins/access-control/kong-plugin-access-control-0.1.0-1.all.rock /tmp
RUN luarocks install /tmp/kong-plugin-access-control-0.1.0-1.all.rock

COPY --from=builder /app/plugins/token-upgrader/kong-plugin-token-upgrader-0.1.0-1.all.rock /tmp
RUN luarocks install /tmp/kong-plugin-token-upgrader-0.1.0-1.all.rock

COPY ./docker/kong.conf /etc/kong/

USER kong