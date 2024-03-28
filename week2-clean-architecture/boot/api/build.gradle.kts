tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":modules:db"))

    implementation("org.springframework.boot:spring-boot-starter-web")
}