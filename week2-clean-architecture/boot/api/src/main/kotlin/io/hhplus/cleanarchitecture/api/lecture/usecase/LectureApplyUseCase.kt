package io.hhplus.cleanarchitecture.api.lecture.usecase

import io.hhplus.cleanarchitecture.domain.lecture.*
import io.hhplus.cleanarchitecture.domain.user.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class LectureApplyUseCase(
    private val lectureReader: LectureReader,
    private val lectureManager: LectureManager,
    private val lectureValidator: LectureValidator,
) {

    operator fun invoke(command: LectureApplyCommand) {
        val user = User(command.userid)
        val lecture = lectureReader.getLectureWithLock(command.lectureId)

        lectureValidator.validate(lecture, user)

        val lectureApplicant = LectureApplicant.create(user, lecture)

        lectureManager.applyLecture(lectureApplicant)
    }
}