//allOpen {
//    annotation("jakarta.persistence.Entity")
//    annotation("jakarta.persistence.MappedSuperclass")
//    annotation("jakarta.persistence.Embeddable")
//}

dependencies {
    implementation(project(":domain"))

    api("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("com.h2database:h2")
}