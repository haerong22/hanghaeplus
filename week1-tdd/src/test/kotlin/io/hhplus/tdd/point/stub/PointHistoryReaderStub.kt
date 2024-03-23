package io.hhplus.tdd.point.stub

import io.hhplus.tdd.point.domain.PointHistoryReader
import io.hhplus.tdd.point.domain.entity.PointHistory
import io.hhplus.tdd.point.domain.entity.TransactionType

class PointHistoryReaderStub : PointHistoryReader {

    override fun findAllByUserId(userId: Long): List<PointHistory> {
        return listOf(
            PointHistory(1L, 1L, TransactionType.CHARGE, 5_000L, 1L),
            PointHistory(2L, 1L, TransactionType.USE, 5_000L, 1L),
            PointHistory(3L, 1L, TransactionType.CHARGE, 10_000L, 1L),
        )
    }
}