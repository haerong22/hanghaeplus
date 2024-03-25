package io.hhplus.cleanarchitecture.jpa.lecture

import io.hhplus.cleanarchitecture.jpa.DbTestSupport
import jakarta.persistence.EntityManager
import jakarta.persistence.LockModeType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

internal class LectureJpaRepositoryTest(
    private val lectureJpaRepository: LectureJpaRepository,
    private val em: EntityManager,
) : DbTestSupport() {

    @Test
    fun `강의를 조회 시 락을 획득한다`() {
        // given
        val startAt = LocalDateTime.of(2024, 3, 25, 12, 10, 0)
        lectureJpaRepository.save(LectureEntity("강의", 30, startAt))

        // when
        val result = lectureJpaRepository.findByIdForUpdate(1L).get()

        // then
        assertThat(em.getLockMode(result)).isEqualTo(LockModeType.PESSIMISTIC_WRITE)
    }

}