package com.hhplus.lecture.domain.lecture

import java.time.LocalDateTime

data class LectureInfo(
    val id: Long,
    val name: String,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val subscriptionCount: Int,
    val lecturerId: Long,
    val lecturerName: String,
) {
    companion object {
        fun from(lecture: Lecture): LectureInfo {
            return LectureInfo(
                id = lecture.id,
                name = lecture.name,
                startTime = lecture.startTime,
                endTime = lecture.endTime,
                subscriptionCount = lecture.subscriptionCount,
                lecturerId = lecture.lecturer.id,
                lecturerName = lecture.lecturer.name,
            )
        }
    }
}
