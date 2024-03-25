package io.hhplus.cleanarchitecture.domain.lecture

import org.springframework.stereotype.Component

@Component
class LectureReader(
    private val lectureRepository: LectureRepository,
) {

    fun getLectureWithLock(lectureId: Long): Lecture {
        return lectureRepository.getLectureWithPessimisticLock(lectureId)
    }

    fun getLectureApplicantCount(lectureId: Long): Int {
        return lectureRepository.getLectureApplicantCount(lectureId)
    }

    fun existApply(lectureId: Long, userId: Long): Boolean {
        return lectureRepository.existApply(lectureId, userId)
    }
}