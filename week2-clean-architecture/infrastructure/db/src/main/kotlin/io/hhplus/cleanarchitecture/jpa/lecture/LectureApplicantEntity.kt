package io.hhplus.cleanarchitecture.jpa.lecture

import io.hhplus.cleanarchitecture.jpa.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Table(name = "lecture_applicant")
@Entity
internal data class LectureApplicantEntity(

    @Column(nullable = false)
    val userId: Long,

    @Column(nullable = false)
    val lectureId: Long,

) : BaseEntity()