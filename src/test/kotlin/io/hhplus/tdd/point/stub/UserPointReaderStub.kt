package io.hhplus.tdd.point.stub

import io.hhplus.tdd.point.domain.UserPointReader
import io.hhplus.tdd.point.domain.entity.UserPoint

class UserPointReaderStub(
    private val initialAmount: Long = 0L
) : UserPointReader {

    override fun findById(id: Long): UserPoint {
        return UserPoint(id, initialAmount, 1L)
    }
}