package config

type Config struct {
	UserApi UserApiConfig
}

type UserApiConfig struct {
	Endpoint string
}
