package io.hhplus.cleanarchitecture.jpa.lecture

import org.springframework.data.jpa.repository.JpaRepository

interface LectureApplicantJpaRepository : JpaRepository<LectureApplicantEntity, Long> {

    fun countByLectureId(lectureId: Long) : Int

    fun existsByLectureIdAndUserId(lectureId: Long, userId: Long) : Boolean
}