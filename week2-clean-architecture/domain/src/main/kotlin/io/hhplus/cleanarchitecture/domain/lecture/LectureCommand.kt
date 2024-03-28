package io.hhplus.cleanarchitecture.domain.lecture

class LectureAppliedStatusCommand(
    val lectureId: Long,
    val userid: Long,
)

class LectureApplyCommand(
    val lectureId: Long,
    val userid: Long,
)