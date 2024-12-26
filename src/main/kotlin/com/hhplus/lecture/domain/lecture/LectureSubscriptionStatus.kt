package com.hhplus.lecture.domain.lecture

import com.hhplus.lecture.domain.user.UserInfo

enum class LectureSubscriptionStatus(
    private val title: String
) {
    AVAILABLE("신청 가능"),
    FULL_OF_SUBSCRIPTION("신청 정원 가득참"),
    SUBSCRIBED("이미 신청함");

    companion object {
        fun from(userInfo: UserInfo, lecture: Lecture): LectureSubscriptionStatus {
            if (lecture.subscriptions.any { it.userId == userInfo.id }) {
                return SUBSCRIBED
            }
            if (lecture.subscriptionCount >= MAX_SUBSCRIPTION_COUNT) {
                return FULL_OF_SUBSCRIPTION
            }
            return AVAILABLE
        }
    }
}