rootProject.name = "clean-architecture"

include("domain")

include("infrastructure:db")
include("infrastructure:config")

include("interfaces:web")
