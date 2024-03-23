package io.hhplus.tdd.point.interfaces.rest

import io.hhplus.tdd.point.application.PointService
import io.hhplus.tdd.point.application.command.GetPointHistoryCommand
import io.hhplus.tdd.point.application.command.GetUserPointCommand
import io.hhplus.tdd.point.application.command.PointChargeCommand
import io.hhplus.tdd.point.application.command.PointUseCommand
import io.hhplus.tdd.point.application.result.PointHistoryResult
import io.hhplus.tdd.point.application.result.UserPointResult
import io.hhplus.tdd.point.domain.entity.PointHistory
import io.hhplus.tdd.point.domain.entity.UserPoint
import io.hhplus.tdd.point.exception.PointErrorCode.INVALID_PARAMETER
import io.hhplus.tdd.point.exception.PointException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/point")
class PointController(
    private val pointService: PointService,
) {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    /**
     * TODO - 특정 유저의 포인트를 조회하는 기능을 작성해주세요.
     */
    @GetMapping("{id}")
    fun point(
        @PathVariable id: Long,
    ): UserPointResult {
        if (id <= 0) throw PointException(INVALID_PARAMETER, "잘못된 userId 입니다.")

        val command = GetUserPointCommand(id)

        return pointService.getUserPoint(command)
    }

    /**
     * TODO - 특정 유저의 포인트 충전/이용 내역을 조회하는 기능을 작성해주세요.
     */
    @GetMapping("{id}/histories")
    fun history(
        @PathVariable id: Long,
    ): List<PointHistoryResult> {
        if (id <= 0) throw PointException(INVALID_PARAMETER, "잘못된 userId 입니다.")

        val command = GetPointHistoryCommand(id)

        return pointService.getUserPointHistory(command)
    }

    /**
     * TODO - 특정 유저의 포인트를 충전하는 기능을 작성해주세요.
     */
    @PatchMapping("{id}/charge")
    fun charge(
        @PathVariable id: Long,
        @RequestBody amount: Long,
    ): UserPointResult {
        if (id <= 0) throw PointException(INVALID_PARAMETER, "잘못된 userId 입니다.")
        if (amount <= 0) throw PointException(INVALID_PARAMETER, "포인트는 1 이상 충전 가능합니다.")

        val command = PointChargeCommand(id, amount)

        return pointService.charge(command)
    }

    /**
     * TODO - 특정 유저의 포인트를 사용하는 기능을 작성해주세요.
     */
    @PatchMapping("{id}/use")
    fun use(
        @PathVariable id: Long,
        @RequestBody amount: Long,
    ): UserPointResult {
        if (id <= 0) throw PointException(INVALID_PARAMETER, "잘못된 userId 입니다.")
        if (amount <= 0) throw PointException(INVALID_PARAMETER, "포인트는 1 이상 충전 가능합니다.")

        val command = PointUseCommand(id, amount)

        return pointService.use(command)
    }
}