package com.hhplus.lecture.api.controller

import com.hhplus.lecture.api.controller.response.AvailableLecturesResponseDto
import com.hhplus.lecture.api.controller.response.SubscriptionsResponseDto
import com.hhplus.lecture.domain.user.UserInfo
import com.hhplus.lecture.support.response.ApiResponse
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/lectures")
class LectureController {

    @PostMapping("/{lectureId}/subscribe")
    fun subscribeLecture(
        @PathVariable lectureId: Long,
        userInfo: UserInfo
    ): ApiResponse<Unit> {
        return ApiResponse.success()
    }

    @GetMapping("/{date}/available")
    fun getAvailableLectures(
        @PathVariable date: LocalDate,
        userInfo: UserInfo
    ): ApiResponse<AvailableLecturesResponseDto> {
        val lectures = listOf(
            AvailableLecturesResponseDto.LecturesDto(
                id = 1,
                name = "테스트 강연",
                startTime = LocalDateTime.of(2024, 12, 23, 12, 0, 0),
                endTime = LocalDateTime.of(2024, 12, 23, 13, 0, 0),
                speakerId = 1L,
                speakerName = "이호준"
            )
        )
        return ApiResponse.success(AvailableLecturesResponseDto(lectures))
    }

    @GetMapping("/subscriptions")
    fun getSubscriptions(
        userInfo: UserInfo
    ): ApiResponse<SubscriptionsResponseDto> {
        val lectures = listOf(
            SubscriptionsResponseDto.LecturesDto(
                id = 1,
                name = "테스트 강연",
                startTime = LocalDateTime.of(2024, 12, 23, 12, 0, 0),
                endTime = LocalDateTime.of(2024, 12, 23, 13, 0, 0),
                speakerId = 1L,
                speakerName = "이호준"
            )
        )
        return ApiResponse.success(SubscriptionsResponseDto(lectures))
    }
}