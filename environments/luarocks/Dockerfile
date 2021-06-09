FROM alpine:latest

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
