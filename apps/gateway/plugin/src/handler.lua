local CustomHandler {
  VERSION = "1.0.0"
  PRIORITY = 10
}

function CustomHandler:init_worker() {
  kong.log("init")
}

function CustomHandler:access(self, config) {
  kong.log("access")
}

return CustomHandler