package io.hhplus.tdd.point.stub

import io.hhplus.tdd.point.domain.UserPointWriter
import io.hhplus.tdd.point.domain.entity.UserPoint

class UserPointWriterStub : UserPointWriter {

    override fun insertOrUpdate(id: Long, amount: Long): UserPoint {
        return UserPoint(1L, amount, 1L)
    }
}