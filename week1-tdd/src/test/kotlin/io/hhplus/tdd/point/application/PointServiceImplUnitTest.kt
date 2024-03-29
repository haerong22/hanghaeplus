package io.hhplus.tdd.point.application

import io.hhplus.tdd.point.application.command.GetPointHistoryCommand
import io.hhplus.tdd.point.application.command.GetUserPointCommand
import io.hhplus.tdd.point.application.command.PointChargeCommand
import io.hhplus.tdd.point.application.command.PointUseCommand
import io.hhplus.tdd.point.domain.entity.TransactionType
import io.hhplus.tdd.point.stub.PointHistoryReaderStub
import io.hhplus.tdd.point.stub.PointHistoryWriterStub
import io.hhplus.tdd.point.stub.UserPointReaderStub
import io.hhplus.tdd.point.stub.UserPointWriterStub
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test

class PointServiceImplUnitTest {

    @Test
    fun `포인트를 충전한다`() {
        // given
        val userPointWriter = UserPointWriterStub()
        val userPointReader = UserPointReaderStub(5_000)
        val pointHistoryWriter = PointHistoryWriterStub()
        val pointHistoryReader = PointHistoryReaderStub()
        val pointService = PointServiceImpl(userPointWriter, userPointReader, pointHistoryWriter, pointHistoryReader)

        val command = PointChargeCommand(1L, 10_000L)

        // when
        val result = pointService.charge(command)

        // then
        assertThat(result)
            .extracting("id", "point", "updateMillis")
            .containsExactly(1L, 15_000L, 1L)

        assertThat(pointHistoryWriter.pointHistory)
            .extracting("id", "userId", "type", "amount", "timeMillis")
            .containsExactly(1L, 1L, TransactionType.CHARGE, 10_000L, 1L)
    }

    @Test
    fun `포인트를 사용한다`() {
        // given
        val userPointWriter = UserPointWriterStub()
        val userPointReader = UserPointReaderStub(5_000)
        val pointHistoryWriter = PointHistoryWriterStub()
        val pointHistoryReader = PointHistoryReaderStub()
        val pointService = PointServiceImpl(userPointWriter, userPointReader, pointHistoryWriter, pointHistoryReader)

        val command = PointUseCommand(1L, 4_000L)

        // when
        val result = pointService.use(command)

        // then
        assertThat(result)
            .extracting("id", "point", "updateMillis")
            .containsExactly(1L, 1_000L, 1L)

        assertThat(pointHistoryWriter.pointHistory)
            .extracting("id", "userId", "type", "amount", "timeMillis")
            .containsExactly(1L, 1L, TransactionType.USE, 4_000L, 1L)
    }

    @Test
    fun `포인트를 조회한다`() {
        // given
        val userPointWriter = UserPointWriterStub()
        val userPointReader = UserPointReaderStub()
        val pointHistoryWriter = PointHistoryWriterStub()
        val pointHistoryReader = PointHistoryReaderStub()
        val pointService = PointServiceImpl(userPointWriter, userPointReader, pointHistoryWriter, pointHistoryReader)

        val command = GetUserPointCommand(1L)

        // when
        val result = pointService.getUserPoint(command)

        // then
        assertThat(result)
            .extracting("id", "point", "updateMillis")
            .containsExactly(1L, 0L, 1L)
    }

    @Test
    fun `포인트 내역을 조회한다`() {
        // given
        val userPointWriter = UserPointWriterStub()
        val userPointReader = UserPointReaderStub()
        val pointHistoryWriter = PointHistoryWriterStub()
        val pointHistoryReader = PointHistoryReaderStub()
        val pointService = PointServiceImpl(userPointWriter, userPointReader, pointHistoryWriter, pointHistoryReader)

        val command = GetPointHistoryCommand(1L)

        // when
        val result = pointService.getUserPointHistory(command)

        // then
        assertThat(result).hasSize(3)
            .extracting("id", "userId", "type", "amount")
            .containsExactlyInAnyOrder(
                tuple(1L, 1L, "CHARGE", 5_000L),
                tuple(2L, 1L, "USE", 5_000L),
                tuple(3L, 1L, "CHARGE", 10_000L)
            )
    }

}