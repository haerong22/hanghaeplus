package io.hhplus.cleanarchitecture.domain.common

interface CustomException {
    val errorCode: ErrorCode
    val msg: String
}