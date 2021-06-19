package handler

import (
	"github.com/gin-gonic/gin"
)

type UserHandler struct{}

func (h UserHandler) GetUser(c *gin.Context) {
	id := c.Param("id")
	c.JSON(200, map[string]interface{}{"id": id})
}
