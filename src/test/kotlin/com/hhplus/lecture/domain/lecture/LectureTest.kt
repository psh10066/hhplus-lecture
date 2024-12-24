package com.hhplus.lecture.domain.lecture

import com.hhplus.lecture.helper.KSelect.Companion.field
import com.hhplus.lecture.domain.user.UserInfo
import org.assertj.core.api.Assertions.assertThat
import org.instancio.Instancio
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock

class LectureTest {

    @Test
    fun `특강 신청 시 신청자 수가 증가한다`() {
        // given
        val lecture = Instancio.of(Lecture::class.java)
            .set(field(Lecture::subscriptionCount), 0)
            .create()

        // when
        lecture.subscribe(mock())

        // then
        assertThat(lecture.subscriptionCount).isEqualTo(1)
    }

    @Test
    fun `특강 신청 시 신청자 목록에 추가된다`() {
        // given
        val lecture = Instancio.of(Lecture::class.java)
            .set(field(Lecture::subscriptions), mutableListOf<LectureSubscription>())
            .create()

        // when
        lecture.subscribe(UserInfo(123L, "홍길동"))

        // then
        assertThat(lecture.subscriptions[0]).isEqualTo(LectureSubscription(lecture, 123L))
    }
}