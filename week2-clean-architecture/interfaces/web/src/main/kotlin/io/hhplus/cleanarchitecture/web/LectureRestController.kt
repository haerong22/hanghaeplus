package io.hhplus.cleanarchitecture.web

import io.hhplus.cleanarchitecture.domain.lecture.LectureService
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
    ) : CommonResponse<Void> {
        request.validate()

        lectureService.apply(request.userId)

        return CommonResponse.ok()
    }
}