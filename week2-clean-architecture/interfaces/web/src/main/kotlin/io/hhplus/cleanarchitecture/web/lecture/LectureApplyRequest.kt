package io.hhplus.cleanarchitecture.web.lecture

import io.hhplus.cleanarchitecture.domain.common.BadRequestException
import io.hhplus.cleanarchitecture.domain.common.CommonErrorCode

data class LectureApplyRequest(
    val userId: Long
) {

    fun validate() {
        if (userId <= 0) throw BadRequestException(CommonErrorCode.BAD_REQUEST, "userId 는 양수 값 입니다.")
    }
}
