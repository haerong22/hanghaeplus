package io.hhplus.tdd.point.application.result

import io.hhplus.tdd.point.domain.entity.UserPoint

data class UserPointResult(
    val id: Long,
    val point: Long,
    val updateMillis: Long,
) {

    companion object {

        fun of(userPoint: UserPoint): UserPointResult {
            return UserPointResult(
                id = userPoint.id,
                point = userPoint.point,
                updateMillis = userPoint.updateMillis,
            )
        }

    }

}
