package io.hhplus.cleanarchitecture.domain.common

class BadRequestException : RuntimeException, CustomException {

    override var errorCode: ErrorCode
    override var msg: String

    constructor(lectureErrorCode: CommonErrorCode) : super(lectureErrorCode.msg) {
        this.errorCode = lectureErrorCode
        this.msg = lectureErrorCode.msg
    }

    constructor(lectureErrorCode: CommonErrorCode, message: String) : super(message) {
        this.errorCode = lectureErrorCode
        this.msg = message
    }
}