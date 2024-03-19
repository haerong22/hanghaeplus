package io.hhplus.tdd.point.infrastructure

import io.hhplus.tdd.database.UserPointTable
import io.hhplus.tdd.point.domain.UserPointReader
import io.hhplus.tdd.point.domain.entity.UserPoint
import org.springframework.stereotype.Repository

@Repository
class UserPointReaderImpl(
    private val userPointTable: UserPointTable,
) : UserPointReader {

    override fun findById(id: Long): UserPoint {
        return userPointTable.selectById(id)
    }
}