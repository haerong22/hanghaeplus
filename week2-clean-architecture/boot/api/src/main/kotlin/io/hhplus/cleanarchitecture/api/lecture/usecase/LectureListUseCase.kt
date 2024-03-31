package io.hhplus.cleanarchitecture.api.lecture.usecase

import io.hhplus.cleanarchitecture.domain.lecture.Lecture
import io.hhplus.cleanarchitecture.domain.lecture.LectureReader
import org.springframework.stereotype.Service

@Service
class LectureListUseCase(
    private val lectureReader: LectureReader,
) {

    operator fun invoke(): List<Lecture> {
        return lectureReader.getLectureList()
    }
}