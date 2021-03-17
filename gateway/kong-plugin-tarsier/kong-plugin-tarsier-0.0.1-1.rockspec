package = "kong-plugin-tarsier"
version = "0.0.1-1"               
-- TODO: This is the name to set in the Kong configuration `plugins` setting.
-- Here we extract it from the package name.
local pluginName = package:match("^kong%-plugin%-(.+)$")  -- "tarsier"

supported_platforms = {"linux", "macosx"}
source = {
  url = "https://github.com/diskit/micro-frontends.git",
  tag = "0.0.1"
}

description = {
  summary = "Kong is a scalable and customizable API Management Layer built on top of Nginx.",
  license = "No license"
}

dependencies = {
}

build = {
  type = "builtin",
  modules = {
    -- TODO: add any additional files that the plugin consists of
    ["kong.plugins."..pluginName..".handler"] = "kong/plugins/"..pluginName.."/handler.lua",
    ["kong.plugins."..pluginName..".schema"] = "kong/plugins/"..pluginName.."/schema.lua",
  }
}
