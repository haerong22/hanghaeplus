package io.hhplus.cleanarchitecture.api.lecture.usecase

import io.hhplus.cleanarchitecture.api.IntegrationTestSupport
import io.hhplus.cleanarchitecture.domain.lecture.LectureApplyCommand
import io.hhplus.cleanarchitecture.jpa.lecture.LectureApplicantJpaRepository
import io.hhplus.cleanarchitecture.jpa.lecture.LectureEntity
import io.hhplus.cleanarchitecture.jpa.lecture.LectureJpaRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.util.concurrent.CompletableFuture
import java.util.concurrent.atomic.AtomicInteger

class LectureApplyUseCaseTest(
    private val lectureApplyUseCase: LectureApplyUseCase,
    private val lectureJpaRepository: LectureJpaRepository,
    private val lectureApplicantJpaRepository: LectureApplicantJpaRepository,
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
            futures.add(CompletableFuture.runAsync {
                runCatching {
                    lectureApplyUseCase(LectureApplyCommand(1L, index + 1L))
                }
                    .onFailure {
                        failCount.addAndGet(1)
                    }
            })
        }

        CompletableFuture.allOf(*futures.toTypedArray()).join()

        // then
        val count = lectureApplicantJpaRepository.count()
        Assertions.assertThat(count).isEqualTo(5)
        Assertions.assertThat(failCount.get()).isEqualTo(5)
    }
}