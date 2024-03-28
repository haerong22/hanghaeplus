package io.hhplus.cleanarchitecture.domain.lecture

import io.hhplus.cleanarchitecture.domain.user.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class LectureService(
    private val lectureReader: LectureReader,
    private val lectureManager: LectureManager,
    private val lectureValidator: LectureValidator,
) {

    @Transactional
    fun apply(userId: Long) {
        val user = User(userId)
        val lecture = lectureReader.getLectureWithLock(1L)

        lectureValidator.validate(lecture, user)

        val lectureApplicant = LectureApplicant.create(user, lecture)

        lectureManager.applyLecture(lectureApplicant)
    }

    fun getMyLectureAppliedStatus(command: LectureAppliedStatusCommand) : Boolean {
        return lectureReader.existApply(command.lectureId, command.userid)
    }

    fun getLectureList() : List<Lecture> {
        return lectureReader.getLectureList()
    }
}