package io.hhplus.cleanarchitecture.domain.lecture

import org.springframework.stereotype.Component

@Component
class LectureManager(
    private val lectureRepository: LectureRepository,
) {

    fun applyLecture(lectureApplicant: LectureApplicant) {
        lectureRepository.applyLecture(lectureApplicant)
    }
}