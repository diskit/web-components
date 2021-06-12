local plugin = {
  PRIORITY = 1000,
  VERSION = "0.1.0",
}

function plugin:init_worker()
  kong.log.debug("saying hi from the 'init_worker' handler")
end

function plugin:access(plugin_conf)
  kong.log("** access start **")
  local token = ngx.var.cookie_t
  kong.log("token=", token)
  -- kong.service.request.set_header(plugin_conf.request_header, "this is on a request")
  kong.log("** access end **")
end

return plugin
