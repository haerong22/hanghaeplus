package io.hhplus.cleanarchitecture.api.lecture.usecase.unit

import io.hhplus.cleanarchitecture.api.lecture.stub.LectureRepositoryStub
import io.hhplus.cleanarchitecture.api.lecture.usecase.LectureApplyUseCase
import io.hhplus.cleanarchitecture.domain.lecture.*
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class LectureApplyUseCaseTest {

    @Test
    fun `강의를 신청 할 수 있다`() {
        // given
        val lectureRepository = LectureRepositoryStub(isApply = false)
        val lectureReader = LectureReader(lectureRepository)
        val lectureManager = LectureManager(lectureRepository)
        val lectureValidator = LectureValidator(lectureReader)

        val useCase = LectureApplyUseCase(lectureReader, lectureManager, lectureValidator)

        val lectureId = 1L
        val userId = 1L

        // when
        useCase(LectureApplyCommand(lectureId, userId))

        // then
        assert(true)
    }

    @Test
    fun `강의 신청 시작일 이전이면 예외가 발생한다`() {
        // given
        val lectureRepository = LectureRepositoryStub(startDate = LocalDateTime.now().plusDays(1))
        val lectureReader = LectureReader(lectureRepository)
        val lectureManager = LectureManager(lectureRepository)
        val lectureValidator = LectureValidator(lectureReader)

        val useCase = LectureApplyUseCase(lectureReader, lectureManager, lectureValidator)

        val lectureId = 1L
        val userId = 1L

        // when, then
        assertThatThrownBy { useCase(LectureApplyCommand(lectureId, userId)) }
            .isInstanceOf(LectureException::class.java)
            .hasMessage("강의 신청 기간이 아닙니다.")
    }

    @Test
    fun `이미 강의 신청을 했다면 예외가 발생한다`() {
        // given
        val lectureRepository = LectureRepositoryStub()
        val lectureReader = LectureReader(lectureRepository)
        val lectureManager = LectureManager(lectureRepository)
        val lectureValidator = LectureValidator(lectureReader)

        val useCase = LectureApplyUseCase(lectureReader, lectureManager, lectureValidator)

        val lectureId = 1L
        val userId = 1L

        // when, then
        assertThatThrownBy { useCase(LectureApplyCommand(lectureId, userId)) }
            .isInstanceOf(LectureException::class.java)
            .hasMessage("이미 신청 되었습니다.")
    }

    @Test
    fun `강의 신청 정원이 다 찼으면 예외가 발생한다`() {
        // given
        val lectureRepository = LectureRepositoryStub(applicantCount = 30, isApply = false)
        val lectureReader = LectureReader(lectureRepository)
        val lectureManager = LectureManager(lectureRepository)
        val lectureValidator = LectureValidator(lectureReader)

        val useCase = LectureApplyUseCase(lectureReader, lectureManager, lectureValidator)

        val lectureId = 1L
        val userId = 1L

        // when, then
        assertThatThrownBy { useCase(LectureApplyCommand(lectureId, userId)) }
            .isInstanceOf(LectureException::class.java)
            .hasMessage("강의 신청 마감되었습니다.")
    }
}