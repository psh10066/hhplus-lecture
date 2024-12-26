package com.hhplus.lecture.domain.lecture

import com.hhplus.lecture.domain.user.UserInfo
import com.hhplus.lecture.helper.KSelect.Companion.field
import org.assertj.core.api.Assertions.assertThat
import org.instancio.Instancio
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock

class LectureSubscriptionStatusTest {

    @Test
    fun `이미 신청한 강의에 대한 상태값을 전달할 수 있다`() {
        // given
        val lecture = Instancio.of(Lecture::class.java)
            .set(field(Lecture::subscriptions), mutableListOf(LectureSubscription(lecture = mock(), userId = 1L)))
            .create()

        // when
        val status = LectureSubscriptionStatus.from(UserInfo(id = 1L, name = "사용자"), lecture)

        // then
        assertThat(status).isEqualTo(LectureSubscriptionStatus.SUBSCRIBED)
    }

    @Test
    fun `신청 인원수가 꽉찬 경우에 대한 상태값을 전달할 수 있다`() {
        // given
        val lecture = Instancio.of(Lecture::class.java)
            .set(field(Lecture::subscriptionCount), 30)
            .set(field(Lecture::subscriptions), mutableListOf<LectureSubscription>())
            .create()

        // when
        val status = LectureSubscriptionStatus.from(UserInfo(id = 1L, name = "사용자"), lecture)

        // then
        assertThat(status).isEqualTo(LectureSubscriptionStatus.FULL_OF_SUBSCRIPTION)
    }

    @Test
    fun `신청 가능한 상태값을 전달할 수 있다`() {
        // given
        val lecture = Instancio.of(Lecture::class.java)
            .set(field(Lecture::subscriptionCount), 29)
            .set(field(Lecture::subscriptions), mutableListOf<LectureSubscription>())
            .create()

        // when
        val status = LectureSubscriptionStatus.from(UserInfo(id = 1L, name = "사용자"), lecture)

        // then
        assertThat(status).isEqualTo(LectureSubscriptionStatus.AVAILABLE)
    }
}