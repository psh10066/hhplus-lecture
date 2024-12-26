package com.hhplus.lecture.domain.lecture

import com.hhplus.lecture.domain.user.UserInfo
import java.time.LocalDateTime

data class LectureWithSubscriptionStatus(
    val id: Long,
    val name: String,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val subscriptionCount: Int,
    val lecturerId: Long,
    val lecturerName: String,
    val subscriptionStatus: LectureSubscriptionStatus,
) {
    companion object {
        fun from(userInfo: UserInfo, lecture: Lecture): LectureWithSubscriptionStatus {
            return LectureWithSubscriptionStatus(
                id = lecture.id,
                name = lecture.name,
                startTime = lecture.startTime,
                endTime = lecture.endTime,
                subscriptionCount = lecture.subscriptionCount,
                lecturerId = lecture.lecturer.id,
                lecturerName = lecture.lecturer.name,
                subscriptionStatus = LectureSubscriptionStatus.from(userInfo, lecture)
            )
        }
    }
}
