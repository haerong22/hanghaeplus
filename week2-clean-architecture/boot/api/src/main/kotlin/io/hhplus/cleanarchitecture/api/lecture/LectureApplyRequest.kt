package io.hhplus.cleanarchitecture.api.lecture

import io.hhplus.cleanarchitecture.domain.common.BadRequestException
import io.hhplus.cleanarchitecture.domain.common.CommonErrorCode
import io.hhplus.cleanarchitecture.domain.lecture.LectureApplyCommand

data class LectureApplyRequest(
    var lectureId: Long,
    val userId: Long,
) {

    fun validate() : LectureApplyRequest {
        if (lectureId <= 0) throw BadRequestException(CommonErrorCode.BAD_REQUEST, "lectureId 는 양수 값 입니다.")
        if (userId <= 0) throw BadRequestException(CommonErrorCode.BAD_REQUEST, "userId 는 양수 값 입니다.")
        return this
    }

    fun toCommand() : LectureApplyCommand {
        return LectureApplyCommand(lectureId, userId)
    }
}
