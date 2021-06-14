local http = require "resty.http"
local json = require "json"
local auth_endpoint = os.getenv('AUTH_ENDPOINT')

local plugin = {
  PRIORITY = 1000, -- set the plugin priority, which determines plugin execution order
  VERSION = "0.1",
}

function plugin:init_worker()
  kong.log.debug("saying hi from the 'init_worker' handler")
end

function plugin:access(plugin_conf)
  local token = kong.request.get_query_arg("token")
  local client = http.new()
  local res, err = client:request_uri(auth_endpoint .. "/credentials/" .. token, {
    method = "GET",
    ssl_verify = false
  })
  if not(res) or res.status ~= 200 then
    return
  end
  local json = json.decode(res.body)
  local credential = json.credential
  kong.ctx.plugin.credential = credential
end

function plugin:header_filter(plugin_conf)
  local credential = kong.ctx.plugin.credential
  local status = kong.response.get_status()
  if not credential or status ~= 200 then
    return
  end
  kong.response.add_header("set-cookie", "t=" .. credential .. "; HttpOnly;")
end

return plugin
