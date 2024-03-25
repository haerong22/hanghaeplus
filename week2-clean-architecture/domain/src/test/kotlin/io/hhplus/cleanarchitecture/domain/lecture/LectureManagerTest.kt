package io.hhplus.cleanarchitecture.domain.lecture

import io.hhplus.cleanarchitecture.domain.stub.LectureRepositoryStub
import io.hhplus.cleanarchitecture.domain.user.User
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class LectureManagerTest {

    @Test
    fun `강의 신청을 한다`() {
        // given
        val lectureRepository = LectureRepositoryStub()
        val lectureManager = LectureManager(lectureRepository)
        val lectureApplicant = LectureApplicant(User(1L), Lecture(1L, "", 1, LocalDateTime.now()))

        // when
        lectureManager.applyLecture(lectureApplicant)

        // then
        assertThat(lectureRepository.callCount["applyLecture"]).isEqualTo(1)
    }

}