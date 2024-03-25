package io.hhplus.cleanarchitecture.domain.lecture

import io.hhplus.cleanarchitecture.domain.common.CustomException
import io.hhplus.cleanarchitecture.domain.common.ErrorCode

class LectureException : RuntimeException, CustomException {

    override var errorCode: ErrorCode
    override var msg: String

    constructor(lectureErrorCode: LectureErrorCode) : super(lectureErrorCode.msg) {
        this.errorCode = lectureErrorCode
        this.msg = lectureErrorCode.msg
    }

    constructor(lectureErrorCode: LectureErrorCode, message: String) : super(message) {
        this.errorCode = lectureErrorCode
        this.msg = message
    }
}