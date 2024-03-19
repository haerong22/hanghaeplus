package io.hhplus.tdd.point.application

import io.hhplus.tdd.database.PointHistoryTable
import io.hhplus.tdd.database.UserPointTable
import io.hhplus.tdd.point.application.command.GetPointHistoryCommand
import io.hhplus.tdd.point.application.command.GetUserPointCommand
import io.hhplus.tdd.point.application.command.PointChargeCommand
import io.hhplus.tdd.point.application.command.PointUseCommand
import io.hhplus.tdd.point.domain.entity.TransactionType
import io.hhplus.tdd.point.exception.PointException
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.concurrent.CountDownLatch
import java.util.concurrent.atomic.AtomicInteger

@SpringBootTest
class PointServiceImplTest(
    @Autowired private val pointService: PointService,
    @Autowired private val userPointTable: UserPointTable,
    @Autowired private val pointHistoryTable: PointHistoryTable,
) {

    @BeforeEach
    fun setup() {
        pointHistoryTable.clear()
        userPointTable.clear()
    }

    @Test
    fun `포인트를 충전한다`() {
        // given
        val command1 = PointChargeCommand(1L, 5_000L)
        val command2 = PointChargeCommand(1L, 10_000L)

        // when
        pointService.charge(command1)
        pointService.charge(command2)

        // then
        val userPoint = userPointTable.selectById(1L)
        val pointHistory = pointHistoryTable.selectAllByUserId(1L)

        assertThat(userPoint)
            .extracting("id", "point")
            .containsExactly(1L, 15_000L)

        assertThat(pointHistory).hasSize(2)
            .extracting("id", "userId", "amount", "type")
            .containsExactlyInAnyOrder(
                tuple(1L, 1L, 5_000L, TransactionType.CHARGE),
                tuple(2L, 1L, 10_000L, TransactionType.CHARGE)
            )
    }

    @Test
    fun `포인트를 충전 동시성 테스트`() {
        // given
        val command = PointChargeCommand(1L, 10_000L)

        // when
        val count = 100
        val latch = CountDownLatch(count)
        repeat(count) {
            Thread {
                pointService.charge(command)
                latch.countDown()
            }.start()
        }

        latch.await()

        // then
        val userPoint = userPointTable.selectById(1L)
        val pointHistory = pointHistoryTable.selectAllByUserId(1L)

        assertThat(userPoint)
            .extracting("id", "point")
            .containsExactly(1L, 1_000_000L)

        assertThat(pointHistory).hasSize(100)
    }

    @Test
    fun `포인트를 사용한다`() {
        // given
        userPointTable.insertOrUpdate(1L, 10_000L)
        val command = PointUseCommand(1L, 5_000L)

        // when
        pointService.use(command)

        // then
        val userPoint = userPointTable.selectById(1L)
        val pointHistory = pointHistoryTable.selectAllByUserId(1L)

        assertThat(userPoint)
            .extracting("id", "point")
            .containsExactly(1L, 5_000L)

        assertThat(pointHistory).hasSize(1)
            .extracting("id", "userId", "amount", "type")
            .containsExactlyInAnyOrder(
                tuple(1L, 1L, 5_000L, TransactionType.USE),
            )
    }

    @Test
    fun `포인트 사용시 잔액이 부족하면 에러가 발생한다`() {
        // given
        val command = PointUseCommand(1L, 5_000L)

        // when then
        assertThatThrownBy { pointService.use(command) }
            .isInstanceOf(PointException::class.java)
            .hasMessage("잔액이 부족합니다.")
    }

    @Test
    fun `포인트를 사용 동시성 테스트`() {
        // given
        userPointTable.insertOrUpdate(1L, 1_000_000)
        val command = PointUseCommand(1L, 10_000L)

        // when
        val count = 100
        val latch = CountDownLatch(count)
        repeat(count) {
            Thread {
                pointService.use(command)
                latch.countDown()
            }.start()
        }

        latch.await()

        // then
        val userPoint = userPointTable.selectById(1L)
        val pointHistory = pointHistoryTable.selectAllByUserId(1L)

        assertThat(userPoint)
            .extracting("id", "point")
            .containsExactly(1L, 0L)

        assertThat(pointHistory).hasSize(100)
    }

    @Test
    fun `포인트 동시성 테스트`() {
        // given
        val chargeCommand = PointChargeCommand(1L, 10_000L)
        val useCommand = PointUseCommand(1L, 10_000L)

        // when
        val count = 100
        val latch = CountDownLatch(count)
        val failCount = AtomicInteger(0)

        repeat(count) { index ->

            if (index % 2 == 0) {
                Thread {
                    pointService.charge(chargeCommand)
                        .also { println(it.point) }
                    latch.countDown()
                }.start()
            } else {
                Thread {
                    try {
                        pointService.use(useCommand)
                            .also { println(it.point) }
                    } catch (e: Exception) {
                        println("잔액부족")
                        failCount.addAndGet(1)
                    } finally {
                        latch.countDown()
                    }
                }.start()
            }
        }

        latch.await()

        // then
        val userPoint = userPointTable.selectById(1L)
        val pointHistory = pointHistoryTable.selectAllByUserId(1L)

        assertThat(userPoint)
            .extracting("id", "point")
            .containsExactly(1L, failCount.get() * 10_000L)

        assertThat(pointHistory).hasSize(100 - failCount.get())
    }

    @Test
    fun `포인트를 조회한다`() {
        // given
        userPointTable.insertOrUpdate(1L, 10_000L)
        val command = GetUserPointCommand(1L)

        // when
        pointService.getUserPoint(command)

        // then
        val userPoint = userPointTable.selectById(1L)

        assertThat(userPoint)
            .extracting("id", "point")
            .containsExactly(1L, 10_000L)
    }

    @Test
    fun `포인트 내역을 조회한다`() {
        // given
        pointHistoryTable.insert(1L, 5_000L, TransactionType.CHARGE, 1L)
        pointHistoryTable.insert(1L, 5_000L, TransactionType.USE, 1L)
        pointHistoryTable.insert(1L, 10_000L, TransactionType.CHARGE, 1L)

        val command = GetPointHistoryCommand(1L)

        // when
        pointService.getUserPointHistory(command)

        // then
        val pointHistoryList = pointHistoryTable.selectAllByUserId(1L)

        assertThat(pointHistoryList).hasSize(3)
            .extracting("id", "userId", "type", "amount")
            .containsExactlyInAnyOrder(
                tuple(1L, 1L, TransactionType.CHARGE, 5_000L),
                tuple(2L, 1L, TransactionType.USE, 5_000L),
                tuple(3L, 1L, TransactionType.CHARGE, 10_000L)
            )
    }
}