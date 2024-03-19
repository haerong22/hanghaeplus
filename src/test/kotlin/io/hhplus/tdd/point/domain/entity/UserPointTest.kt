package io.hhplus.tdd.point.domain.entity

import io.hhplus.tdd.point.exception.PointException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class UserPointTest {

    @Test
    fun `포인트를 충전한다`() {
        // given
        val userPoint = UserPoint(1L, 5_000L, System.currentTimeMillis())

        // when
        val result = userPoint.charge(10_000L)

        // then
        assertThat(result.point).isEqualTo(15_000L)
    }

    @ParameterizedTest
    @ValueSource(longs = [-1, 0])
    fun `포인트 충전 시 수량은 양수이다`(amount: Long) {
        // given
        val userPoint = UserPoint(1L, 0L, System.currentTimeMillis())

        // when then
        assertThatThrownBy { userPoint.charge(amount) }
            .isInstanceOf(PointException::class.java)
            .hasMessage("포인트 수량은 양수만 가능합니다.")
    }

    @Test
    fun `포인트를 사용한다`() {
        // given
        val userPoint = UserPoint(1L, 5_000L, System.currentTimeMillis())

        // when
        val result = userPoint.use(5_000L)

        // then
        assertThat(result.point).isEqualTo(0L)
    }

    @ParameterizedTest
    @ValueSource(longs = [-1, 0])
    fun `포인트 사용시 수량은 양수이다`(amount: Long) {
        // given
        val userPoint = UserPoint(1L, 0L, System.currentTimeMillis())

        // when then
        assertThatThrownBy { userPoint.use(amount) }
            .isInstanceOf(PointException::class.java)
            .hasMessage("포인트 수량은 양수만 가능합니다.")
    }

    @Test
    fun `포인트를 사용시 잔액보다 많은 양을 사용하면 에러가 발생한다`() {
        // given
        val userPoint = UserPoint(1L, 5_000L, System.currentTimeMillis())

        // when then
        assertThatThrownBy { userPoint.use(10_000L) }
            .isInstanceOf(PointException::class.java)
            .hasMessage("잔액이 부족합니다.")
    }
}