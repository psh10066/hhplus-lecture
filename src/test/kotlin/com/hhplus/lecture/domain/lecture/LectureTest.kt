package com.hhplus.lecture.domain.lecture

import com.hhplus.lecture.domain.user.UserInfo
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import java.time.LocalDateTime

class LectureTest {

    @Test
    fun `특강 신청 시 신청자 수가 증가한다`() {
        // given
        val lecture = Lecture(
            name = "특강",
            startTime = LocalDateTime.of(2024, 12, 23, 13, 0),
            endTime = LocalDateTime.of(2024, 12, 23, 14, 0),
            lecturer = Lecturer(name = "홍길동"),
            subscriptionCount = 0,
            subscriptions = mutableListOf()
        )

        // when
        lecture.subscribe(mock())

        // then
        assertThat(lecture.subscriptionCount).isEqualTo(1)
    }

    @Test
    fun `특강 신청 시 신청자 목록에 추가된다`() {
        // given
        val lecture = Lecture(
            name = "특강",
            startTime = LocalDateTime.of(2024, 12, 23, 13, 0),
            endTime = LocalDateTime.of(2024, 12, 23, 14, 0),
            lecturer = Lecturer(name = "홍길동"),
            subscriptionCount = 0,
            subscriptions = mutableListOf()
        )

        // when
        lecture.subscribe(UserInfo(123L, "홍길동"))

        // then
        assertThat(lecture.subscriptions[0]).isEqualTo(LectureSubscription(lecture, 123L))
    }
}