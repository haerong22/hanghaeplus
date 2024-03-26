package io.hhplus.cleanarchitecture.jpa.lecture

import io.hhplus.cleanarchitecture.jpa.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint

@Table(
    name = "lecture_applicant",
    uniqueConstraints = [
        UniqueConstraint(
            name = "unique_key_with_user_id_and_lecture_id",
            columnNames = ["lecture_id", "user_id"]
        )
    ]
)
@Entity
internal class LectureApplicantEntity(

    @Column(nullable = false)
    val userId: Long,

    @Column(nullable = false)
    val lectureId: Long,

) : BaseEntity()