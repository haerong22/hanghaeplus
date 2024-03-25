package io.hhplus.cleanarchitecture.domain.lecture

import io.hhplus.cleanarchitecture.domain.user.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class LectureService(
    private val lectureReader: LectureReader,
    private val lectureManager: LectureManager,
) {

    @Transactional
    fun apply(userId: Long) {
        val user = User(userId)

        val lecture = lectureReader.getLectureWithLock(1L)
            .also {
                it.checkPeriod(LocalDateTime.now())
                it.checkAlreadyApplied(lectureReader.existApply(it.id, user.id))
                it.checkQuota(lectureReader.getLectureApplicantCount(it.id))
            }

        val lectureApplicant = LectureApplicant.create(user, lecture)

        lectureManager.applyLecture(lectureApplicant)
    }

    fun getMyLectureAppliedStatus(command: LectureAppliedStatusCommand) : Boolean {
        return lectureReader.existApply(command.lectureId, command.userid)
    }
}