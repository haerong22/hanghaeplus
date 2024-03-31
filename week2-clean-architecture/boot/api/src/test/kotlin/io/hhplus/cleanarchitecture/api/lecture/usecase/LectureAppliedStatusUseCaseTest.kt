package io.hhplus.cleanarchitecture.api.lecture.usecase

import io.hhplus.cleanarchitecture.api.IntegrationTestSupport
import io.hhplus.cleanarchitecture.domain.lecture.LectureAppliedStatusCommand
import io.hhplus.cleanarchitecture.jpa.lecture.LectureApplicantEntity
import io.hhplus.cleanarchitecture.jpa.lecture.LectureApplicantJpaRepository
import io.hhplus.cleanarchitecture.jpa.lecture.LectureEntity
import io.hhplus.cleanarchitecture.jpa.lecture.LectureJpaRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class LectureAppliedStatusUseCaseTest(
    private val lectureAppliedStatusUseCase: LectureAppliedStatusUseCase,
    private val lectureJpaRepository: LectureJpaRepository,
    private val lectureApplicantJpaRepository: LectureApplicantJpaRepository,
) : IntegrationTestSupport() {

    @Test
    fun `내 강의 신청 상태 조회 시 강의 신청이 완료 되었으면 true를 응답한다`() {
        // given
        val startAt = LocalDateTime.of(2024, 3, 25, 12, 10, 0)
        lectureJpaRepository.save(LectureEntity("강의", 5, startAt))
        lectureApplicantJpaRepository.save(LectureApplicantEntity(1L, 1L))

        val command = LectureAppliedStatusCommand(1L, 1L)

        // when
        val result = lectureAppliedStatusUseCase(command)

        // then
        assertThat(result).isTrue()
    }

    @Test
    fun `내 강의 신청 상태 조회 시 강의 신청 되지 않았으면 false를 응답한다`() {
        // given
        val startAt = LocalDateTime.of(2024, 3, 25, 12, 10, 0)
        lectureJpaRepository.save(LectureEntity("강의", 5, startAt))

        val command = LectureAppliedStatusCommand(1L, 1L)

        // when
        val result = lectureAppliedStatusUseCase(command)

        // then
        assertThat(result).isFalse()
    }
}