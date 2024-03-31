package io.hhplus.cleanarchitecture.jpa.lecture

import io.hhplus.cleanarchitecture.domain.lecture.Lecture
import io.hhplus.cleanarchitecture.jpa.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.LocalDateTime

@Table(name = "lecture")
@Entity
class LectureEntity(

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val quota: Int = 30,

    @Column(nullable = false)
    val startAt: LocalDateTime,

    ) : BaseEntity() {

    fun toDomain(): Lecture {
        return Lecture(id!!, name, quota, startAt)
    }
}
