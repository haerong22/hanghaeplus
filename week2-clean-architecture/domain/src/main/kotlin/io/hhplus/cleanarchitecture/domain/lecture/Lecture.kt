package io.hhplus.cleanarchitecture.domain.lecture

import io.hhplus.cleanarchitecture.domain.lecture.LectureErrorCode.*
import java.time.LocalDateTime

data class Lecture(
    val id: Long,
    val name: String,
    val quota: Int,
    val startAt: LocalDateTime,
) {

    fun checkPeriod(date: LocalDateTime) {
        if (date.isBefore(startAt)) throw LectureException(NOT_APPLY_PERIOD)
    }

    fun checkQuota(quota: Int) {
        if (this.quota <= quota) throw LectureException(LECTURE_CLOSED)
    }

    fun checkAlreadyApplied(apply: Boolean) {
        if (apply) throw LectureException(ALREADY_APPLIED)
    }
}
