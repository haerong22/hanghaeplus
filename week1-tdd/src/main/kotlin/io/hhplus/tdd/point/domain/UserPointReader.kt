package io.hhplus.tdd.point.domain

import io.hhplus.tdd.point.domain.entity.UserPoint

interface UserPointReader {

    fun findById(id: Long) : UserPoint
}