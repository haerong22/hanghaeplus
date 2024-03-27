package io.hhplus.cleanarchitecture.domain.lecture

import io.hhplus.cleanarchitecture.domain.stub.LectureRepositoryStub
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class LectureServiceTest {

    @Test
    fun `강의를 신청 할 수 있다`() {
        // given
        val lectureRepository = LectureRepositoryStub(isApply = false)
        val lectureReader = LectureReader(lectureRepository)
        val lectureManager = LectureManager(lectureRepository)
        val lectureValidator = LectureValidator(lectureReader)
        val lectureService = LectureService(lectureReader, lectureManager, lectureValidator)

        val userId = 1L

        // when
        lectureService.apply(userId)

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
        val lectureService = LectureService(lectureReader, lectureManager, lectureValidator)

        val userId = 1L

        // when, then
        assertThatThrownBy { lectureService.apply(userId) }
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
        val lectureService = LectureService(lectureReader, lectureManager, lectureValidator)

        val userId = 1L

        // when, then
        assertThatThrownBy { lectureService.apply(userId) }
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
        val lectureService = LectureService(lectureReader, lectureManager, lectureValidator)

        val userId = 1L

        // when, then
        assertThatThrownBy { lectureService.apply(userId) }
            .isInstanceOf(LectureException::class.java)
            .hasMessage("강의 신청 마감되었습니다.")
    }

    @Test
    fun `강의 리스트를 조회한다`() {
        // given
        val lectureRepository = LectureRepositoryStub()
        val lectureReader = LectureReader(lectureRepository)
        val lectureManager = LectureManager(lectureRepository)
        val lectureValidator = LectureValidator(lectureReader)
        val lectureService = LectureService(lectureReader, lectureManager, lectureValidator)

        // when
        val result = lectureService.getLectureList()

        // then
        assertThat(result).isNotNull
    }
}