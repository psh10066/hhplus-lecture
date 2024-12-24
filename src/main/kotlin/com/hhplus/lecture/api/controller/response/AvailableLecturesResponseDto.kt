package com.hhplus.lecture.api.controller.response

import com.hhplus.lecture.domain.lecture.LectureInfo
import java.time.LocalDateTime

data class AvailableLecturesResponseDto(
    val lectures: List<LecturesDto>
) {
    companion object {
        fun from(lectures: List<LectureInfo>): AvailableLecturesResponseDto {
            return AvailableLecturesResponseDto(
                lectures = lectures.map { LecturesDto.from(it) }
            )
        }
    }

    data class LecturesDto(
        val id: Long,
        val name: String,
        val startTime: LocalDateTime,
        val endTime: LocalDateTime,
        val lecturerId: Long,
        val lecturerName: String
    ) {
        companion object {
            fun from(lecture: LectureInfo): LecturesDto {
                return LecturesDto(
                    id = lecture.id,
                    name = lecture.name,
                    startTime = lecture.startTime,
                    endTime = lecture.endTime,
                    lecturerId = lecture.lecturerId,
                    lecturerName = lecture.lecturerName
                )
            }
        }
    }
}