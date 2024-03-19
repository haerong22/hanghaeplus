package io.hhplus.tdd.point.infrastructure

import io.hhplus.tdd.database.UserPointTable
import io.hhplus.tdd.point.domain.UserPointWriter
import io.hhplus.tdd.point.domain.entity.UserPoint
import org.springframework.stereotype.Repository

@Repository
class UserPointWriterImpl(
    private val userPointTable: UserPointTable,
) : UserPointWriter {

    override fun insertOrUpdate(id: Long, amount: Long) : UserPoint {
        return userPointTable.insertOrUpdate(id, amount)
    }
}