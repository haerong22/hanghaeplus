package io.hhplus.cleanarchitecture.api.lecture.usecase.unit

import io.hhplus.cleanarchitecture.api.lecture.stub.LectureRepositoryStub
import io.hhplus.cleanarchitecture.api.lecture.usecase.LectureAppliedStatusUseCase
import io.hhplus.cleanarchitecture.domain.lecture.LectureAppliedStatusCommand
import io.hhplus.cleanarchitecture.domain.lecture.LectureReader
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class LectureAppliedStatusUseCaseTest {

    @Test
    fun `내 강의 신청 상태를 조회 할 수 있다`() {
        // given
        val lectureRepository = LectureRepositoryStub(isApply = true)
        val lectureReader = LectureReader(lectureRepository)

        val useCase = LectureAppliedStatusUseCase(lectureReader)

        val lectureId = 1L
        val userId = 1L

        // when
        val result = useCase(LectureAppliedStatusCommand(lectureId, userId))

        // then
        assertThat(result).isTrue()
    }
}