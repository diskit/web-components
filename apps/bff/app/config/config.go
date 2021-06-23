package config

import "github.com/kelseyhightower/envconfig"

type Config struct {
	UserApi UserApiConfig
}

type UserApiConfig struct {
	Endpoint string
}

var config *Config

func InitConfig() {
	var user UserApiConfig
	envconfig.Process("USER_API", &user)

	config = &Config{
		UserApi: user,
	}
}

func GetConfig() *Config {
	return config
}
