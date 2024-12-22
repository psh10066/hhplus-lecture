package com.hhplus.lecture.api.controller

import com.hhplus.lecture.support.response.ApiResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/lectures")
class LectureController {

    @PostMapping("/{lectureId}/subscribe")
    fun subscribeLecture(
        @PathVariable lectureId: Long,
        @RequestHeader userId: Long
    ): ApiResponse<Unit> {
        return ApiResponse.success()
    }
}