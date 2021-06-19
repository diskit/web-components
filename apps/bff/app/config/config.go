package config

type Config struct {
	UserApi UserApiConfig
}

type UserApiConfig struct {
	Endpoint string
}

var config *Config

func InitConfig() {
	config = &Config{
		UserApi: UserApiConfig{
			Endpoint: "http://localhost:8081",
		},
	}
}

func GetConfig() *Config {
	return config
}
