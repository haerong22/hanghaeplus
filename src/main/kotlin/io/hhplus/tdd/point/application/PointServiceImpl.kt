package io.hhplus.tdd.point.application

import io.hhplus.tdd.point.application.command.GetPointHistoryCommand
import io.hhplus.tdd.point.application.command.GetUserPointCommand
import io.hhplus.tdd.point.application.command.PointChargeCommand
import io.hhplus.tdd.point.application.command.PointUseCommand
import io.hhplus.tdd.point.application.result.PointHistoryResult
import io.hhplus.tdd.point.application.result.UserPointResult
import io.hhplus.tdd.point.domain.PointHistoryReader
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
    private val pointHistoryReader: PointHistoryReader,
) : PointService {
    private val lock: ReentrantLock = ReentrantLock()

    override fun getUserPointHistory(command: GetPointHistoryCommand): List<PointHistoryResult> {
        return pointHistoryReader.findAllByUserId(command.userId)
            .map { pointHistory -> PointHistoryResult.of(pointHistory) }
    }

    override fun getUserPoint(command: GetUserPointCommand): UserPointResult {
        return UserPointResult.of(userPointReader.findById(command.id))
    }

    override fun charge(command: PointChargeCommand): UserPointResult {
        lock.withLock {
            return userPointReader.findById(command.id)
                .run { charge(command.point) }
                .run {
                    createPointHistory(command.point, TransactionType.CHARGE)
                    updateUserPoint()
                }
                .run { UserPointResult.of(this) }
        }
    }

    override fun use(command: PointUseCommand): UserPointResult {
        lock.withLock {
            return userPointReader.findById(command.id)
                .run { use(command.point) }
                .run {
                    createPointHistory(command.point, TransactionType.USE)
                    updateUserPoint()
                }
                .run { UserPointResult.of(this) }
        }
    }

    private fun UserPoint.updateUserPoint() : UserPoint{
        return userPointWriter.insertOrUpdate(this.id, this.point)
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