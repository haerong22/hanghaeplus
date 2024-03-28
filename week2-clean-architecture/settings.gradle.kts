rootProject.name = "clean-architecture"

include("domain")

include("modules:db")
include("modules:config")

include("boot:api")
