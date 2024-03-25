package io.hhplus.cleanarchitecture.domain.lecture

import io.hhplus.cleanarchitecture.domain.common.ErrorCode

enum class LectureErrorCode(
    override val code: Int,
    override val msg: String,
) : ErrorCode {

    NOT_FOUNT_LECTURE(1404, "해당 강의가 없습니다."),
    LECTURE_CLOSED(1000, "강의 신청 마감되었습니다."),
    NOT_APPLY_PERIOD(1001, "강의 신청 기간이 아닙니다."),
    ALREADY_APPLIED(1002, "이미 신청 되었습니다."),

    ;
}