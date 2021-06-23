package server

import (
	"bff/di"

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
	systemGroup := router.Group("systems")
	{
		system := di.InitSystemHandler()
		systemGroup.GET("/ping", system.Ping)
	}
}

func user(router *gin.Engine) {
	userGroup := router.Group("users")
	{
		user := di.InitUserHandler()
		userGroup.GET("/:id", user.GetUser)
	}
}
