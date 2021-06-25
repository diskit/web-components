package handler

import (
	"net/http"

	"github.com/gin-gonic/gin"
)

type SystemHandler struct{}

func (h SystemHandler) Ping(c *gin.Context) {
	c.String(http.StatusOK, "pong")
}

func NewSystemHandler() *SystemHandler {
	return &SystemHandler{}
}
