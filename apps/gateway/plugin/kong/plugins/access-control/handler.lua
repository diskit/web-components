local plugin = {
  PRIORITY = 1000,
  VERSION = "0.1.0",
}

function plugin:init_worker()
  kong.log.debug("saying hi from the 'init_worker' handler")
end

--[[ runs in the 'rewrite_by_lua_block'
-- IMPORTANT: during the `rewrite` phase neither `route`, `service`, nor `consumer`
-- will have been identified, hence this handler will only be executed if the plugin is
-- configured as a global plugin!
function plugin:rewrite(plugin_conf)

  -- your custom code here
  kong.log.debug("saying hi from the 'rewrite' handler")

end --]]


function plugin:access(plugin_conf)
  kong.log("** access start **")
  local token = ngx.var.cookie_t
  kong.log("token=", token)
  -- kong.service.request.set_header(plugin_conf.request_header, "this is on a request")
  kong.log("** access end **")
end

function plugin:header_filter(plugin_conf)
  kong.log("** header_filter start **")
  -- kong.response.set_header(plugin_conf.response_header, "this is on the response")
  kong.log("** header_filter end **")
end

--[[ runs in the 'body_filter_by_lua_block'
function plugin:body_filter(plugin_conf)

  -- your custom code here
  kong.log.debug("saying hi from the 'body_filter' handler")

end --]]

--[[ runs in the 'log_by_lua_block'
function plugin:log(plugin_conf)

  -- your custom code here
  kong.log.debug("saying hi from the 'log' handler")

end --]]

return plugin
