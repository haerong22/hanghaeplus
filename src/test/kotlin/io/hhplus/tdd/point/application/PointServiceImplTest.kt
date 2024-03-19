package io.hhplus.tdd.point.application

import io.hhplus.tdd.database.PointHistoryTable
import io.hhplus.tdd.database.UserPointTable
import io.hhplus.tdd.point.application.command.PointChargeCommand
import io.hhplus.tdd.point.domain.entity.TransactionType
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.tuple
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.concurrent.CountDownLatch

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
}