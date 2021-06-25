package handler

import (
	"bff/usecase"

	"github.com/gin-gonic/gin"
)

type UserHandler struct {
	usecase usecase.UserUsecase
}

func NewUserHandler(usecase usecase.UserUsecase) *UserHandler {
	return &UserHandler{usecase}
}

func (h UserHandler) GetUser(c *gin.Context) {
	id := c.Param("id")
	user := h.usecase.GetUser(id)
	c.JSON(200, user)
}
