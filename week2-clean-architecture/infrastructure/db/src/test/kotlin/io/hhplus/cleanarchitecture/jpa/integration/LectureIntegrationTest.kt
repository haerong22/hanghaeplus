package io.hhplus.cleanarchitecture.jpa.integration

import io.hhplus.cleanarchitecture.domain.lecture.LectureManager
import io.hhplus.cleanarchitecture.domain.lecture.LectureReader
import io.hhplus.cleanarchitecture.domain.lecture.LectureRepository
import io.hhplus.cleanarchitecture.domain.lecture.LectureService
import io.hhplus.cleanarchitecture.jpa.IntegrationTestSupport
import io.hhplus.cleanarchitecture.jpa.lecture.LectureEntity
import io.hhplus.cleanarchitecture.jpa.lecture.LectureJpaRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletableFuture.allOf
import java.util.concurrent.CompletableFuture.runAsync
import java.util.concurrent.atomic.AtomicInteger

internal class LectureIntegrationTest(
    private val lectureRepository: LectureRepository,
    private val lectureService: LectureService,
    private val lectureJpaRepository: LectureJpaRepository,
) : IntegrationTestSupport() {

    @Test
    fun `수강 신청 동시성 테스트`() {
        // given
        val startAt = LocalDateTime.of(2024, 3, 25, 12, 10, 0)
        lectureJpaRepository.save(LectureEntity("강의", 5, startAt))

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