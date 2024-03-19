package io.hhplus.tdd.point.exception

import org.springframework.http.HttpStatus

enum class PointErrorCode(val status: HttpStatus, val message: String) {
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    NOT_ENOUGH_BALANCE(HttpStatus.INTERNAL_SERVER_ERROR, "잔액이 부족합니다."),
}

class PointException : RuntimeException {

    var status: HttpStatus
    var msg: String

    constructor(pointErrorCode: PointErrorCode) : super(pointErrorCode.message) {
        status = pointErrorCode.status
        msg = pointErrorCode.message
    }

    constructor(pointErrorCode: PointErrorCode, message: String) : super(message) {
        status = pointErrorCode.status
        msg = message
    }
}