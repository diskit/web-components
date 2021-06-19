package usecase

import "bff/infra"

type UserUsecase struct {
	api infra.Api
}

func NewUserUsecase(api infra.Api) UserUsecase {
	return UserUsecase{api}
}

func (u *UserUsecase) GetUser(id string) map[string]interface{} {
	return u.api.GetUser(id)
}
