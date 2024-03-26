package io.hhplus.cleanarchitecture.domain.lecture

import io.hhplus.cleanarchitecture.domain.stub.LectureRepositoryStub
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class LectureReaderTest {

    @Test
    fun `강의를 조회 한다`() {
        // given
        val lectureRepository = LectureRepositoryStub()
        val lectureReader = LectureReader(lectureRepository)

        // when
        val result = lectureReader.getLectureWithLock(1L)

        // then
        assertThat(lectureRepository.callCount["getLectureWithPessimisticLock"]).isEqualTo(1)
        assertThat(result.id).isEqualTo(1L)
        assertThat(result.name).isEqualTo("강의")
        assertThat(result.quota).isEqualTo(30)
        assertThat(result.startAt).isEqualTo(LocalDateTime.of(2024, 3, 25, 12, 0, 0))
    }

    @Test
    fun `강의 신청자 수를 조회 한다`() {
        // given
        val lectureRepository = LectureRepositoryStub(30)
        val lectureReader = LectureReader(lectureRepository)

        // when
        val result = lectureReader.getLectureApplicantCount(1L)

        // then
        assertThat(lectureRepository.callCount["getLectureApplicantCount"]).isEqualTo(1)
        assertThat(result).isEqualTo(30)
    }

    @Test
    fun `강의 신청 여부를 조회 한다`() {
        // given
        val lectureRepository = LectureRepositoryStub()
        val lectureReader = LectureReader(lectureRepository)

        // when
        val result = lectureReader.existApply(1L, 1L)

        // then
        assertThat(lectureRepository.callCount["existApply"]).isEqualTo(1)
        assertThat(result).isTrue()
    }

    @Test
    fun `강의 리스트를 조회 한다`() {
        // given
        val lectureRepository = LectureRepositoryStub()
        val lectureReader = LectureReader(lectureRepository)

        // when
        val result = lectureReader.getLectureList()

        // then
        assertThat(lectureRepository.callCount["getLectureList"]).isEqualTo(1)
        assertThat(result).isNotNull
    }

}