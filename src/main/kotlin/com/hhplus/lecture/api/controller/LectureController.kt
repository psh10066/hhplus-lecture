package com.hhplus.lecture.api.controller

import com.hhplus.lecture.api.controller.response.AvailableLecturesResponseDto
import com.hhplus.lecture.api.controller.response.SubscriptionsResponseDto
import com.hhplus.lecture.domain.lecture.LectureService
import com.hhplus.lecture.domain.user.UserInfo
import com.hhplus.lecture.support.response.ApiResponse
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/api/lectures")
class LectureController(
    private val lectureService: LectureService
) {

    @PostMapping("/{lectureId}/subscribe")
    fun subscribeLecture(
        @PathVariable lectureId: Long,
        userInfo: UserInfo
    ): ApiResponse<Unit> {
        lectureService.subscribe(userInfo, lectureId)
        return ApiResponse.success()
    }

    @GetMapping("/{date}/available")
    fun getAvailableLectures(
        @PathVariable date: LocalDate,
        userInfo: UserInfo
    ): ApiResponse<AvailableLecturesResponseDto> {
        val lectures = lectureService.getAvailableLectures(userInfo, date)
        return ApiResponse.success(AvailableLecturesResponseDto.from(lectures))
    }

    @GetMapping("/subscriptions")
    fun getSubscriptions(
        userInfo: UserInfo
    ): ApiResponse<SubscriptionsResponseDto> {
        val lectures = lectureService.getSubscriptions(userInfo)
        return ApiResponse.success(SubscriptionsResponseDto.from(lectures))
    }
}