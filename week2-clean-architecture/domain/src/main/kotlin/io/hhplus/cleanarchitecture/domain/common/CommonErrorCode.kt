package io.hhplus.cleanarchitecture.domain.common

enum class CommonErrorCode(
    override val code: Int,
    override val msg: String,
) : ErrorCode {

    BAD_REQUEST(400, "잘못 된 요청 데이터 입니다."),

    ;
}