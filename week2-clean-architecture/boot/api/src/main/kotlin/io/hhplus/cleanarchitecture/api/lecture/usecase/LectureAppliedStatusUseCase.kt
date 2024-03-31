package io.hhplus.cleanarchitecture.api.lecture.usecase

import io.hhplus.cleanarchitecture.domain.lecture.LectureAppliedStatusCommand
import io.hhplus.cleanarchitecture.domain.lecture.LectureReader
import org.springframework.stereotype.Service

@Service
class LectureAppliedStatusUseCase(
    private val lectureReader: LectureReader,
) {

    operator fun invoke(command: LectureAppliedStatusCommand): Boolean {
        return lectureReader.existApply(command.lectureId, command.userid)
    }
}