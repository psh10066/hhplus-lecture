package com.hhplus.lecture.domain.lecture

import com.hhplus.lecture.domain.user.UserInfo
import com.hhplus.lecture.helper.KSelect.Companion.field
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.instancio.Instancio
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock

class LectureTest {

    @Test
    fun `특강 신청 시 신청자 수가 증가한다`() {
        // given
        val lecture = Instancio.of(Lecture::class.java)
            .set(field(Lecture::subscriptionCount), 0)
            .set(field(Lecture::subscriptions), mutableListOf<LectureSubscription>())
            .create()

        // when
        lecture.subscribe(mock())

        // then
        assertThat(lecture.subscriptionCount).isEqualTo(1)
    }

    @Test
    fun `특강 신청 인원의 최대 인원인 30명을 초과하면 예외가 발생한다`() {
        // given
        val lecture = Instancio.of(Lecture::class.java)
            .set(field(Lecture::subscriptionCount), 0)
            .set(field(Lecture::subscriptions), mutableListOf<LectureSubscription>())
            .create()
        for (i in 1L..30L) {
            val userInfo = Instancio.of(UserInfo::class.java)
                .set(field(UserInfo::id), i)
                .create()
            lecture.subscribe(userInfo)
        }

        val userInfo = Instancio.of(UserInfo::class.java)
            .set(field(UserInfo::id), 123L)
            .create()

        // when then
        assertThatThrownBy {
            lecture.subscribe(userInfo)
        }
            .isInstanceOf(IllegalStateException::class.java)
            .hasMessage("특강 신청 인원이 초과되었습니다.")
    }

    @Test
    fun `같은 사용자가 이미 신청한 특강에 재신청할 수 없다`() {
        // given
        val lecture = Instancio.of(Lecture::class.java)
            .set(field(Lecture::subscriptionCount), 0)
            .set(field(Lecture::subscriptions), mutableListOf<LectureSubscription>())
            .create()

        val userInfo = Instancio.of(UserInfo::class.java)
            .set(field(UserInfo::id), 123L)
            .create()
        lecture.subscribe(userInfo)

        // when then
        assertThatThrownBy {
            lecture.subscribe(userInfo)
        }
            .isInstanceOf(IllegalStateException::class.java)
            .hasMessage("이미 신청한 특강입니다.")
    }

    @Test
    fun `특강 신청 시 신청자 목록에 추가된다`() {
        // given
        val lecture = Instancio.of(Lecture::class.java)
            .set(field(Lecture::subscriptionCount), 0)
            .set(field(Lecture::subscriptions), mutableListOf<LectureSubscription>())
            .create()

        // when
        lecture.subscribe(UserInfo(123L, "홍길동"))

        // then
        assertThat(lecture.subscriptions[0]).isEqualTo(LectureSubscription(lecture, 123L))
    }
}