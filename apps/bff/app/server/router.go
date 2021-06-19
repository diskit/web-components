package server

import (
	"bff/handler"

	"github.com/gin-gonic/gin"
)

func NewRouter() *gin.Engine {
	router := gin.New()
	router.Use(gin.Logger())
	router.Use(gin.Recovery())
	system(router)
	user(router)
	return router
}

func system(router *gin.Engine) {
	systemGroup := router.Group("system")
	{
		system := new(handler.SystemHandler)
		systemGroup.GET("/ping", system.Ping)
	}
}

func user(router *gin.Engine) {
	userGroup := router.Group("users")
	{
		user := new(handler.UserHandler)
		userGroup.GET("/:id", user.GetUser)
	}
}
