package io.hhplus.cleanarchitecture.jpa.config

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@Configuration
@EnableJpaAuditing
@ComponentScan(basePackages = [
    "io.hhplus.cleanarchitecture.domain"
])
internal class JpaConfig