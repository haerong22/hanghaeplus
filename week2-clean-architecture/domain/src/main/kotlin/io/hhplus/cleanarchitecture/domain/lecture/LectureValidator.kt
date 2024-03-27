package io.hhplus.cleanarchitecture.domain.lecture

import io.hhplus.cleanarchitecture.domain.user.User
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class LectureValidator(
    private val lectureReader: LectureReader,
) {
    fun validate(lecture: Lecture, user: User) {
        lecture.checkPeriod(LocalDateTime.now())
        lecture.checkAlreadyApplied(lectureReader.existApply(lecture.id, user.id))
        lecture.checkQuota(lectureReader.getLectureApplicantCount(lecture.id))
    }
}