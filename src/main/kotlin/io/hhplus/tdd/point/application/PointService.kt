package io.hhplus.tdd.point.application

import io.hhplus.tdd.point.application.command.PointChargeCommand
import io.hhplus.tdd.point.application.command.PointUseCommand
import io.hhplus.tdd.point.application.result.UserPointResult

interface PointService {

    fun charge(command: PointChargeCommand): UserPointResult
    fun use(command: PointUseCommand) : UserPointResult
}