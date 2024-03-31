package io.hhplus.cleanarchitecture.api

import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestConstructor

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@ActiveProfiles("test")
@SpringBootTest
abstract class IntegrationTestSupport {

    @Autowired
    lateinit var dbCleanup: DbCleanup

    @BeforeEach
    fun setup() {
        dbCleanup.execute()
    }
}