package io.hhplus.cleanarchitecture.jpa.lecture

import io.hhplus.cleanarchitecture.domain.lecture.LectureException
import io.hhplus.cleanarchitecture.jpa.DbTestSupport
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

internal class LectureRepositoryImplTest(
    private val lectureJpaRepository: LectureJpaRepository,
    private val lectureApplicantJpaRepository: LectureApplicantJpaRepository,
) : DbTestSupport() {

    private val lectureRepositoryImpl = LectureRepositoryImpl(
        lectureJpaRepository, lectureApplicantJpaRepository
    )

    @Test
    fun `강의를 조회 할 수 있다`() {
        // given
        val startAt = LocalDateTime.of(2024, 3, 25, 12, 10, 0)
        lectureJpaRepository.save(LectureEntity("강의", 30, startAt))

        // when
        val result = lectureRepositoryImpl.getLectureWithPessimisticLock(1L)

        // then
        assertThat(lectureJpaRepository.count()).isEqualTo(1)
        assertThat(result.id).isEqualTo(1L)
        assertThat(result.name).isEqualTo("강의")
        assertThat(result.quota).isEqualTo(30)
        assertThat(result.startAt).isEqualTo(startAt)
    }

    @Test
    fun `강의를 조회 시 해당 강의가 없으면 예외가 발생한다`() {
        // given

        // when, then
        assertThatThrownBy { lectureRepositoryImpl.getLectureWithPessimisticLock(1L) }
            .isInstanceOf(LectureException::class.java)
            .hasMessage("해당 강의가 없습니다.")
    }

    @Test
    fun `강의 신청자 수를 조회 할 수 있다`() {
        // given
        lectureJpaRepository.save(LectureEntity("강의", 30, LocalDateTime.now()))
        lectureApplicantJpaRepository.save(LectureApplicantEntity(1L, 1L))
        lectureApplicantJpaRepository.save(LectureApplicantEntity(2L, 1L))

        // when
        val result = lectureRepositoryImpl.getLectureApplicantCount(1L)

        // then
        assertThat(result).isEqualTo(2)
    }

    @Test
    fun `강의 신청여부를 조회 할 수 있다`() {
        // given
        lectureJpaRepository.save(LectureEntity("강의", 30, LocalDateTime.now()))
        lectureApplicantJpaRepository.save(LectureApplicantEntity(1L, 1L))

        // when
        val result1 = lectureRepositoryImpl.existApply(1L, 1L)
        val result2 = lectureRepositoryImpl.existApply(1L, 2L)

        // then
        assertThat(result1).isTrue()
        assertThat(result2).isFalse()
    }
}