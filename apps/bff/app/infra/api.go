package infra

import (
	"bff/config"
	"encoding/json"
	"fmt"
	"io/ioutil"
	"net/http"
)

type Api struct{}

func NewApi() Api {
	return Api{}
}

func (a Api) GetUser(id string) map[string]interface{} {
	path := fmt.Sprintf("%s/users/%s", config.GetConfig().UserApi.Endpoint, id)
	req, _ := http.NewRequest(http.MethodGet, path, nil)
	value, _ := doRequest(req)
	return value
}

func doRequest(req *http.Request) (map[string]interface{}, error) {
	res, err := http.DefaultClient.Do(req)
	if err != nil {
		return nil, err
	}
	defer res.Body.Close()
	body, err := ioutil.ReadAll(res.Body)

	if err != nil {
		return nil, err
	}
	var value map[string]interface{}
	json.Unmarshal([]byte(body), &value)
	return value, err
}
