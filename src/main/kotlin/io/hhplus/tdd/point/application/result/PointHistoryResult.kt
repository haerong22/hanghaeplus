package io.hhplus.tdd.point.application.result

import io.hhplus.tdd.point.domain.entity.PointHistory

data class PointHistoryResult(
    val id: Long,
    val userId: Long,
    val type: String,
    val amount: Long,
    val timeMillis: Long,
) {

    companion object {

        fun of(pointHistory: PointHistory): PointHistoryResult {
            return PointHistoryResult(
                id = pointHistory.id,
                userId = pointHistory.userId,
                type = pointHistory.type.name,
                amount = pointHistory.amount,
                timeMillis = pointHistory.timeMillis,
            )
        }

    }

}

/**
 * 포인트 트랜잭션 종류
 * - CHARGE : 충전
 * - USE : 사용
 */
enum class TransactionType {
    CHARGE, USE
}