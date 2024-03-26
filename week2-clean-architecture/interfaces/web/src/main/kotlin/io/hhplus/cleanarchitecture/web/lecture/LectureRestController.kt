package io.hhplus.cleanarchitecture.web.lecture

import io.hhplus.cleanarchitecture.domain.lecture.Lecture
import io.hhplus.cleanarchitecture.domain.lecture.LectureService
import io.hhplus.cleanarchitecture.web.CommonResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/lectures")
class LectureRestController(
    private val lectureService: LectureService
) {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/apply")
    fun applyLecture(
        @RequestBody request: LectureApplyRequest
    ): CommonResponse<Void> {
        request.validate()

        lectureService.apply(request.userId)

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
    fun getLectureList() : CommonResponse<List<Lecture>> {
        return CommonResponse.ok(lectureService.getLectureList())
    }
}