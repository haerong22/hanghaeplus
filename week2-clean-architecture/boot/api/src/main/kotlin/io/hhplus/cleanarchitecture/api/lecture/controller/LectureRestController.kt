package io.hhplus.cleanarchitecture.api.lecture.controller

import io.hhplus.cleanarchitecture.api.CommonResponse
import io.hhplus.cleanarchitecture.api.lecture.dto.LectureAppliedStatusRequest
import io.hhplus.cleanarchitecture.api.lecture.dto.LectureApplyRequest
import io.hhplus.cleanarchitecture.api.lecture.usecase.LectureAppliedStatusUseCase
import io.hhplus.cleanarchitecture.api.lecture.usecase.LectureApplyUseCase
import io.hhplus.cleanarchitecture.api.lecture.usecase.LectureListUseCase
import io.hhplus.cleanarchitecture.domain.lecture.Lecture
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/lectures")
class LectureRestController(
    private val lectureApplyUseCase: LectureApplyUseCase,
    private val lectureAppliedStatusUseCase: LectureAppliedStatusUseCase,
    private val lectureListUseCase: LectureListUseCase,
) {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{lectureId}/apply")
    fun applyLecture(
        @PathVariable lectureId: Long,
        @RequestBody request: LectureApplyRequest,
    ): CommonResponse<Void> {
        val command = request
            .apply { this.lectureId = lectureId }
            .validate()
            .toCommand()

        lectureApplyUseCase(command)

        return CommonResponse.ok()
    }

    @GetMapping("/{lectureId}/apply")
    fun getMyLectureAppliedStatus(
        @PathVariable lectureId: Long,
        request: LectureAppliedStatusRequest,
    ): CommonResponse<Boolean> {
        val command = request
            .apply { this.lectureId = lectureId }
            .validate()
            .toCommand()

        return CommonResponse.ok(lectureAppliedStatusUseCase(command))
    }

    @GetMapping
    fun getLectureList(): CommonResponse<List<Lecture>> {
        return CommonResponse.ok(lectureListUseCase())
    }
}