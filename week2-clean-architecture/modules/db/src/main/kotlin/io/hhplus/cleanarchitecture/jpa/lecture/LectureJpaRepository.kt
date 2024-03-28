package io.hhplus.cleanarchitecture.jpa.lecture

import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import java.util.*

internal interface LectureJpaRepository : JpaRepository<LectureEntity, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select l from LectureEntity l where l.id=:id")
    fun findByIdForUpdate(id: Long): Optional<LectureEntity>
}