package io.hhplus.cleanarchitecture.api.lecture

import io.hhplus.cleanarchitecture.domain.lecture.Lecture
import io.hhplus.cleanarchitecture.domain.lecture.LectureService
import io.hhplus.cleanarchitecture.api.CommonResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/lectures")
class LectureRestController(
    private val lectureService: LectureService
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

        lectureService.apply(command)

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

        return CommonResponse.ok(lectureService.getMyLectureAppliedStatus(command))
    }

    @GetMapping
    fun getLectureList(): CommonResponse<List<Lecture>> {
        return CommonResponse.ok(lectureService.getLectureList())
    }
}