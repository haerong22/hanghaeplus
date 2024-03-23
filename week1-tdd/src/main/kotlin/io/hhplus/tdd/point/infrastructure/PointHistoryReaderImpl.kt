package io.hhplus.tdd.point.infrastructure

import io.hhplus.tdd.database.PointHistoryTable
import io.hhplus.tdd.point.domain.PointHistoryReader
import io.hhplus.tdd.point.domain.entity.PointHistory
import org.springframework.stereotype.Repository

@Repository
class PointHistoryReaderImpl(
    private val pointHistoryTable: PointHistoryTable,
) : PointHistoryReader {

    override fun findAllByUserId(userId: Long): List<PointHistory> {
        return pointHistoryTable.selectAllByUserId(userId)
    }
}