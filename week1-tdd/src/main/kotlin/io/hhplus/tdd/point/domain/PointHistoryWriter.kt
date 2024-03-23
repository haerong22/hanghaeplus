package io.hhplus.tdd.point.domain

import io.hhplus.tdd.point.domain.entity.PointHistory
import io.hhplus.tdd.point.domain.entity.TransactionType

interface PointHistoryWriter {

    fun insert(
        id: Long,
        amount: Long,
        transactionType: TransactionType,
        updateMillis: Long,
    ) : PointHistory
}