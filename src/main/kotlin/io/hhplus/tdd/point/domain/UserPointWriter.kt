package io.hhplus.tdd.point.domain

import io.hhplus.tdd.point.domain.entity.UserPoint

interface UserPointWriter {

    fun insertOrUpdate(id: Long, amount: Long) : UserPoint
}