package main

import (
	"bff/config"
	"bff/server"
)

func main() {
	config.InitConfig()
	server.Init()
}
