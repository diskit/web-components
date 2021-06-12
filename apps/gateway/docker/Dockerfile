FROM diskit/lua-alpine:5.3 as builder
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

COPY ./kong.conf /etc/kong/

USER kong