package io.hhplus.cleanarchitecture.domain.lecture

import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.time.LocalDateTime

class LectureTest {

    @Test
    fun `강의 신청 시작일 전 이면 예외가 발생한다`() {
        // given
        val date = LocalDateTime.now()
        val lecture = Lecture(1L, "강의", 30, date.plusDays(1))

        // when, then
        assertThatThrownBy { lecture.checkPeriod(date) }
            .isInstanceOf(LectureException::class.java)
            .hasMessage("강의 신청 기간이 아닙니다.")
    }

    @ParameterizedTest
    @ValueSource(ints = [30, 31])
    fun `수강 신청한 인원이 강의 정원보다 크거나 같으면 예외가 발생한다`(quota: Int) {
        // given
        val lecture = Lecture(1L, "강의", 30, LocalDateTime.now())

        // when, then
        assertThatThrownBy { lecture.checkQuota(quota) }
            .isInstanceOf(LectureException::class.java)
            .hasMessage("강의 신청 마감되었습니다.")
    }

    @Test
    fun `이미 신청 했다면 예외가 발생한다`() {
        // given
        val lecture = Lecture(1L, "강의", 30, LocalDateTime.now())

        // when, then
        assertThatThrownBy { lecture.checkAlreadyApplied(true) }
            .isInstanceOf(LectureException::class.java)
            .hasMessage("이미 신청 되었습니다.")
    }
}