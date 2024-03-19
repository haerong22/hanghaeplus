package io.hhplus.tdd.point.application

import io.hhplus.tdd.point.application.command.PointChargeCommand
import io.hhplus.tdd.point.application.result.UserPointResult
import io.hhplus.tdd.point.domain.PointHistoryWriter
import io.hhplus.tdd.point.domain.UserPointReader
import io.hhplus.tdd.point.domain.UserPointWriter
import io.hhplus.tdd.point.domain.entity.TransactionType
import io.hhplus.tdd.point.domain.entity.UserPoint
import org.springframework.stereotype.Service
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

@Service
class PointServiceImpl(
    private val userPointWriter: UserPointWriter,
    private val userPointReader: UserPointReader,
    private val pointHistoryWriter: PointHistoryWriter,
) : PointService {

    private val lock: ReentrantLock = ReentrantLock()

    override fun charge(command: PointChargeCommand): UserPointResult {
        lock.withLock {
            return userPointReader.findById(command.id)
                .run {
                    createPointHistory(command.point, TransactionType.CHARGE)
                    this
                }
                .run {
                    val updatedUserPoint = this.charge(command.point)
                    userPointWriter.insertOrUpdate(updatedUserPoint.id, updatedUserPoint.point)
                }
                .run { UserPointResult.of(this) }
        }
    }

    private fun UserPoint.createPointHistory(amount: Long, transactionType: TransactionType) {
        pointHistoryWriter.insert(
            id = this.id,
            amount = amount,
            transactionType = transactionType,
            updateMillis = this.updateMillis
        )
    }
}