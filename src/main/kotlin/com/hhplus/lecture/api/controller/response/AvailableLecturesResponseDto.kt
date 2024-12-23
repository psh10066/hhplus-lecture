package com.hhplus.lecture.api.controller.response

import java.time.LocalDateTime

data class AvailableLecturesResponseDto(
    val lectures: List<LecturesDto>
) {
    data class LecturesDto(
        val id: Long,
        val name: String,
        val startTime: LocalDateTime,
        val endTime: LocalDateTime,
        val speakerId: Long,
        val speakerName: String
    )
}