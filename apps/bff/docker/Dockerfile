FROM golang:1.16.5-alpine3.13 as builder

ENV CGO_ENABLED 0
ENV GOOS=linux
ENV GOARCH=amd64

WORKDIR /work
COPY app app
RUN apk add make gcc g++
RUN cd app && make build

FROM alpine
RUN apk add --no-cache ca-certificates
COPY --from=builder /work/app/main /app/main
EXPOSE 8080
ENTRYPOINT [ "/app/main" ]