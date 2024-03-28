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
    fun apply(command: LectureApplyCommand) {
        val user = User(command.userid)
        val lecture = lectureReader.getLectureWithLock(command.lectureId)

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