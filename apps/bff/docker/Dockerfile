FROM golang:1.16.5-alpine3.13 as builder

ENV CGO_ENABLED 0
ENV GOOS=linux
ENV GOARCH=amd64
ENV GO111MODULE="on"

WORKDIR /work
COPY app/go.mod app/go.sum ./
RUN go mod download
COPY app ./
RUN go build -o main

FROM alpine
RUN apk add --no-cache ca-certificates
COPY --from=builder /work/main /app/main
ENV GIN_MODE=release
EXPOSE 8080
ENTRYPOINT [ "/app/main" ]