package io.hhplus.cleanarchitecture.web.integration

import io.hhplus.cleanarchitecture.domain.lecture.LectureRepository
import io.hhplus.cleanarchitecture.domain.lecture.LectureService
import io.hhplus.cleanarchitecture.web.IntegrationTestSupport
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletableFuture.allOf
import java.util.concurrent.CompletableFuture.runAsync
import java.util.concurrent.atomic.AtomicInteger

class LectureIntegrationTest(
    private val lectureService: LectureService,
    private val lectureRepository: LectureRepository,
) : IntegrationTestSupport() {

    @Test
    fun `수강 신청 동시성 테스트`() {
        // given

        // when
        val failCount = AtomicInteger(0)
        val futures = mutableListOf<CompletableFuture<Void>>()

        repeat(10) {index ->
            futures.add( runAsync {
                runCatching {
                    lectureService.apply(index + 1L)
                }
                    .onFailure { failCount.addAndGet(1) }
            })
        }

        allOf(*futures.toTypedArray()).join()

        // then
        val count = lectureRepository.getLectureApplicantCount(1L)
        assertThat(count).isEqualTo(5)
        assertThat(failCount.get()).isEqualTo(5)
    }
}