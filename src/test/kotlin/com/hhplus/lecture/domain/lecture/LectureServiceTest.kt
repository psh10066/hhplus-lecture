package com.hhplus.lecture.domain.lecture

import com.hhplus.lecture.helper.KSelect.Companion.field
import com.hhplus.lecture.domain.user.UserInfo
import org.assertj.core.api.Assertions.assertThat
import org.instancio.Instancio
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock
import java.time.LocalDate

class LectureServiceTest {
    private lateinit var lectureRepository: LectureRepository
    private lateinit var lectureService: LectureService

    @BeforeEach
    fun setUp() {
        lectureRepository = mock()
        lectureService = LectureService(lectureRepository)
    }

    @Test
    fun `특강을 신청할 수 있다`() {
        // given
        val lecture = Instancio.of(Lecture::class.java)
            .set(field(Lecture::subscriptionCount), 0)
            .set(field(Lecture::subscriptions), mutableListOf<LectureSubscription>())
            .create()
        given(lectureRepository.getForUpdateById(1L)).willReturn(lecture)

        // when
        lectureService.subscribe(UserInfo(123L, "홍길동"), 1L)

        // then
        assertThat(lecture.subscriptionCount).isEqualTo(1)
        assertThat(lecture.subscriptions[0]).isEqualTo(LectureSubscription(lecture, 123L))
    }

    @Test
    fun `사용자 별로 특강 신청 가능 목록을 조회할 수 있다`() {
        // given
        val lecture = Instancio.of(Lecture::class.java).create()
        given(
            lectureRepository.getAvailableLectures(UserInfo(123L, "홍길동"), LocalDate.of(2023, 12, 23))
        ).willReturn(listOf(lecture))

        // when
        val result = lectureService.getAvailableLectures(UserInfo(123L, "홍길동"), LocalDate.of(2023, 12, 23))

        // then
        assertThat(result).hasSize(1)
        assertThat(result[0].id).isEqualTo(lecture.id)
        assertThat(result[0].name).isEqualTo(lecture.name)
        assertThat(result[0].startTime).isEqualTo(lecture.startTime)
        assertThat(result[0].endTime).isEqualTo(lecture.endTime)
        assertThat(result[0].subscriptionCount).isEqualTo(lecture.subscriptionCount)
        assertThat(result[0].lecturerId).isEqualTo(lecture.lecturer.id)
        assertThat(result[0].lecturerName).isEqualTo(lecture.lecturer.name)
    }

    @Test
    fun `사용자 별로 특강 신청 목록을 조회할 수 있다`() {
        // given
        val lecture = Instancio.of(Lecture::class.java).create()
        given(
            lectureRepository.getSubscriptions(UserInfo(123L, "홍길동"))
        ).willReturn(listOf(lecture))

        // when
        val result = lectureService.getSubscriptions(UserInfo(123L, "홍길동"))

        // then
        assertThat(result).hasSize(1)
        assertThat(result[0].id).isEqualTo(lecture.id)
        assertThat(result[0].name).isEqualTo(lecture.name)
        assertThat(result[0].startTime).isEqualTo(lecture.startTime)
        assertThat(result[0].endTime).isEqualTo(lecture.endTime)
        assertThat(result[0].subscriptionCount).isEqualTo(lecture.subscriptionCount)
        assertThat(result[0].lecturerId).isEqualTo(lecture.lecturer.id)
        assertThat(result[0].lecturerName).isEqualTo(lecture.lecturer.name)
    }
}
