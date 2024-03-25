package io.hhplus.cleanarchitecture.domain.lecture

interface LectureRepository {

    fun applyLecture(lectureApplicant: LectureApplicant)

    fun getLectureWithPessimisticLock(lectureId: Long) : Lecture

    fun getLectureApplicantCount(lectureId: Long): Int

    fun existApply(lectureId: Long, userId: Long): Boolean
}