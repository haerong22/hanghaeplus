package io.hhplus.cleanarchitecture.domain.stub

import io.hhplus.cleanarchitecture.domain.lecture.Lecture
import io.hhplus.cleanarchitecture.domain.lecture.LectureApplicant
import io.hhplus.cleanarchitecture.domain.lecture.LectureRepository
import java.time.LocalDateTime

class LectureRepositoryStub(
    private val applicantCount: Int = 0,
    private val isApply: Boolean = true,
    private val startDate: LocalDateTime = LocalDateTime.of(2024, 3, 25, 12, 0, 0)
) : LectureRepository {

    var callCount: MutableMap<String, Int> = mutableMapOf()

    override fun applyLecture(lectureApplicant: LectureApplicant) {
        count(object{}.javaClass.enclosingMethod.name)
    }

    override fun getLectureWithPessimisticLock(lectureId: Long): Lecture {
        count(object{}.javaClass.enclosingMethod.name)
        return Lecture(lectureId, "강의", 30, startDate)
    }

    override fun getLectureApplicantCount(lectureId: Long): Int {
        count(object{}.javaClass.enclosingMethod.name)
        return applicantCount
    }

    override fun existApply(lectureId: Long, userId: Long): Boolean {
        count(object{}.javaClass.enclosingMethod.name)
        return isApply
    }

    override fun getLectureList(): List<Lecture> {
        count(object{}.javaClass.enclosingMethod.name)
        return emptyList()
    }

    private fun count(methodName: String) {
        callCount[methodName] = callCount.getOrDefault(methodName, 0) + 1
    }
}