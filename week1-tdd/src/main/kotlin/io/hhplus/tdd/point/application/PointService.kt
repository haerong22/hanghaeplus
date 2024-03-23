package io.hhplus.tdd.point.application

import io.hhplus.tdd.point.application.command.GetUserPointCommand
import io.hhplus.tdd.point.application.command.GetPointHistoryCommand
import io.hhplus.tdd.point.application.command.PointChargeCommand
import io.hhplus.tdd.point.application.command.PointUseCommand
import io.hhplus.tdd.point.application.result.PointHistoryResult
import io.hhplus.tdd.point.application.result.UserPointResult

interface PointService {

    fun charge(command: PointChargeCommand): UserPointResult
    fun use(command: PointUseCommand) : UserPointResult
    fun getUserPoint(command: GetUserPointCommand) : UserPointResult
    fun getUserPointHistory(command: GetPointHistoryCommand) : List<PointHistoryResult>
}