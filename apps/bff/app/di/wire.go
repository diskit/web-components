// +build wireinject

package di

import (
	"bff/handler"
	"bff/infra"
	"bff/usecase"

	"github.com/google/wire"
)

func InitUserHandler() handler.UserHandler {
	wire.Build(handler.NewUserHandler, infra.NewApi, usecase.NewUserUsecase)
	return handler.UserHandler{}
}

func InitSystemHandler() handler.SystemHandler {
	return handler.SystemHandler{}
}
