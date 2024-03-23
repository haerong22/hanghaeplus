package io.hhplus.tdd.point.infrastructure

import io.hhplus.tdd.database.PointHistoryTable
import io.hhplus.tdd.point.domain.PointHistoryWriter
import io.hhplus.tdd.point.domain.entity.PointHistory
import io.hhplus.tdd.point.domain.entity.TransactionType
import org.springframework.stereotype.Repository

@Repository
class PointHistoryWriterImpl(
    private val pointHistoryTable: PointHistoryTable,
) : PointHistoryWriter {

    override fun insert(id: Long, amount: Long, transactionType: TransactionType, updateMillis: Long): PointHistory {
        return pointHistoryTable.insert(id, amount, transactionType, updateMillis)
    }
}