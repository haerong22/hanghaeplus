package io.hhplus.tdd.point.stub

import io.hhplus.tdd.point.domain.PointHistoryWriter
import io.hhplus.tdd.point.domain.entity.PointHistory
import io.hhplus.tdd.point.domain.entity.TransactionType

class PointHistoryWriterStub : PointHistoryWriter {

    lateinit var pointHistory: PointHistory

    override fun insert(id: Long, amount: Long, transactionType: TransactionType, updateMillis: Long): PointHistory {
        return PointHistory(1L, id, transactionType, amount, updateMillis)
            .also { this.pointHistory = it }
    }
}