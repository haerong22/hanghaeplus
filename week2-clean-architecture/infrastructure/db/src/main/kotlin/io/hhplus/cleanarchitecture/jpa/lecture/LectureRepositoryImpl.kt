package io.hhplus.cleanarchitecture.jpa.lecture

import io.hhplus.cleanarchitecture.domain.lecture.Lecture
import io.hhplus.cleanarchitecture.domain.lecture.LectureApplicant
import io.hhplus.cleanarchitecture.domain.lecture.LectureErrorCode.NOT_FOUNT_LECTURE
import io.hhplus.cleanarchitecture.domain.lecture.LectureException
import io.hhplus.cleanarchitecture.domain.lecture.LectureRepository
import org.springframework.stereotype.Repository

@Repository
internal class LectureRepositoryImpl(
    private val lectureJpaRepository: LectureJpaRepository,
    private val lectureApplicantJpaRepository: LectureApplicantJpaRepository,
) : LectureRepository {

    override fun applyLecture(lectureApplicant: LectureApplicant) {
        lectureApplicantJpaRepository.save(
            LectureApplicantEntity(
                userId = lectureApplicant.user.id,
                lectureId = lectureApplicant.lecture.id,
            )
        )
    }

    override fun getLectureWithPessimisticLock(lectureId: Long): Lecture {
        return lectureJpaRepository.findByIdForUpdate(lectureId)
            .orElseThrow { LectureException(NOT_FOUNT_LECTURE) }
            .toDomain()
    }

    override fun getLectureApplicantCount(lectureId: Long): Int {
        return lectureApplicantJpaRepository.countByLectureId(lectureId)
    }

    override fun existApply(lectureId: Long, userId: Long): Boolean {
        return lectureApplicantJpaRepository.existsByLectureIdAndUserId(lectureId, userId)
    }

    override fun getLectureList(): List<Lecture> {
        return lectureJpaRepository.findAll()
            .map { it.toDomain() }
    }
}