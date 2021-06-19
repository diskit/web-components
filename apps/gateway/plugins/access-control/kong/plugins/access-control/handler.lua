local http = require "resty.http"
local json = require "json"
local auth_endpoint = os.getenv('AUTH_ENDPOINT')

local plugin = {
  PRIORITY = 1000,
  VERSION = "0.1.0",
}

function plugin:init_worker()
  kong.log.debug("saying hi from the 'init_worker' handler")
end

function plugin:access(plugin_conf)
  local token = ngx.var.cookie_t
  if not(token) then
    return
  end
  local client = http.new()
  local request_json = {
    ["credential"] = token
  }
  local res, err = client:request_uri(auth_endpoint .. "/authn", {
    method = "POST",
    headers = {
      ["content-type"] = "application/json;"
    },
    body = json.encode(request_json),
    ssl_verify = false
  })
  if not(res) or res.status ~= 200 then
    return
  end
  local json = json.decode(res.body)
  kong.service.request.set_header("x-user-id", json.user)
  kong.service.request.set_header("x-consumer-app", json.consumer)
  kong.service.request.clear_header("cookie")
end

return plugin
