package io.hhplus.tdd.point.domain

import io.hhplus.tdd.point.domain.entity.PointHistory

interface PointHistoryReader {

    fun findAllByUserId(userId: Long) : List<PointHistory>
}