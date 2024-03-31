package io.hhplus.cleanarchitecture.api.lecture.usecase

import io.hhplus.cleanarchitecture.api.IntegrationTestSupport
import io.hhplus.cleanarchitecture.jpa.lecture.LectureEntity
import io.hhplus.cleanarchitecture.jpa.lecture.LectureJpaRepository
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class LectureListUseCaseTest(
    private val lectureListUseCase: LectureListUseCase,
    private val lectureJpaRepository: LectureJpaRepository,
) : IntegrationTestSupport() {

    @Test
    fun `강의 목록을 조회 할 수 있다`() {
        // given
        val startAt = LocalDateTime.of(2024, 3, 25, 12, 10, 0)
        lectureJpaRepository.save(LectureEntity("강의", 5, startAt))

        // when
        val result = lectureListUseCase()

        // then
        assertThat(result).hasSize(1)
    }

    @Test
    fun `강의 목록이 없으면 빈 리스트를 응답한다`() {
        // given

        // when
        val result = lectureListUseCase()

        // then
        assertThat(result).isNotNull().hasSize(0)
    }
}