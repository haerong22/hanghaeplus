package io.hhplus.tdd.common.lock

import java.util.concurrent.locks.ReentrantLock

enum class CustomLock(
    val lock: ReentrantLock,
) {
    POINT(ReentrantLock()),
}