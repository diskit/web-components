FROM alpine:latest

ENV LUA_VERSION 5.3

ENV LUA_PACKAGE lua${LUA_VERSION}

RUN apk update

RUN apk add ${LUA_PACKAGE}
RUN apk add ${LUA_PACKAGE}-dev

RUN apk add build-base git bash zip unzip

RUN cd /tmp && \
    git clone https://github.com/keplerproject/luarocks.git && \
    cd luarocks && \
    sh ./configure && \
    make build install && \
    cd && \
    rm -rf /tmp/luarocks
