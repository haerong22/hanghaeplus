package io.hhplus.cleanarchitecture.domain.lecture

import io.hhplus.cleanarchitecture.domain.user.User
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class LectureApplicantTest {

    @Test
    fun `User 와 Lecture 로 LectureApplicant를 생성 할 수 있다`() {
        // given
        val user = User(1L)
        val lecture = Lecture(1L, "강의", 30, LocalDateTime.of(2024, 3, 25, 12, 0, 0))

        // when
        val result = LectureApplicant.create(user, lecture)

        // then
        assertThat(result.user.id).isEqualTo(1L)
        assertThat(result.lecture.id).isEqualTo(1L)
        assertThat(result.lecture.name).isEqualTo("강의")
        assertThat(result.lecture.quota).isEqualTo(30)
        assertThat(result.lecture.startAt).isEqualTo(LocalDateTime.of(2024, 3, 25, 12, 0, 0))
    }
}