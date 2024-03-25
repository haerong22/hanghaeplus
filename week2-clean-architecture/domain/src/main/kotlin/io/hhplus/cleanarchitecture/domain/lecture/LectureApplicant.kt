package io.hhplus.cleanarchitecture.domain.lecture

import io.hhplus.cleanarchitecture.domain.user.User

data class LectureApplicant(
    val user: User,
    val lecture: Lecture,
) {

    companion object {

        fun create(user: User, lecture: Lecture) : LectureApplicant {
            return LectureApplicant(user, lecture)
        }
    }

}
