package io.hhplus.tdd.point.domain.entity

import io.hhplus.tdd.point.exception.PointErrorCode.INVALID_PARAMETER
import io.hhplus.tdd.point.exception.PointErrorCode.NOT_ENOUGH_BALANCE
import io.hhplus.tdd.point.exception.PointException

data class UserPoint(
    val id: Long,
    val point: Long,
    val updateMillis: Long,
) {

    fun charge(amount: Long) : UserPoint {
        validate(amount)
        return UserPoint(id, point + amount, updateMillis)
    }

    fun use(amount: Long) : UserPoint {
        validate(amount)
        checkBalance(amount)

        return UserPoint(id, point - amount, updateMillis)
    }

    private fun checkBalance(amount: Long) {
        if (point - amount < 0) throw PointException(NOT_ENOUGH_BALANCE)
    }

    private fun validate(amount: Long) {
        if (amount <= 0) throw PointException(INVALID_PARAMETER, "포인트 수량은 양수만 가능합니다.")
    }
}