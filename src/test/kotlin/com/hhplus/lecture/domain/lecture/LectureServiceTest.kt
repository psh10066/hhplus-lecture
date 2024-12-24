package com.hhplus.lecture.domain.lecture

import com.hhplus.helper.withId
import com.hhplus.lecture.domain.user.UserInfo
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock
import java.time.LocalDate
import java.time.LocalDateTime

class LectureServiceTest {
    private lateinit var lectureRepository: LectureRepository
    private lateinit var lectureService: LectureService

    @BeforeEach
    fun setUp() {
        lectureRepository = mock()
        lectureService = LectureService(lectureRepository)
    }

    @Test
    fun `사용자 별로 특강 신청 가능 목록을 조회할 수 있다`() {
        // given
        val lectures = listOf(
            Lecture(
                name = "Kotlin Basic",
                startTime = LocalDateTime.of(2023, 12, 23, 12, 0, 0),
                endTime = LocalDateTime.of(2023, 12, 23, 13, 0, 0),
                subscriptionCount = 12,
                lecturer = Lecturer(name = "이호준").withId(10L)
            ).withId(1L)
        )
        given(
            lectureRepository.getAvailableLectures(UserInfo(123L, "홍길동"), LocalDate.of(2023, 12, 23))
        ).willReturn(lectures)

        // when
        val result = lectureService.getAvailableLectures(UserInfo(123L, "홍길동"), LocalDate.of(2023, 12, 23))

        // then
        assertThat(result).hasSize(1)
        assertThat(result[0].id).isEqualTo(1L)
        assertThat(result[0].name).isEqualTo("Kotlin Basic")
        assertThat(result[0].startTime).isEqualTo(LocalDateTime.of(2023, 12, 23, 12, 0, 0))
        assertThat(result[0].endTime).isEqualTo(LocalDateTime.of(2023, 12, 23, 13, 0, 0))
        assertThat(result[0].subscriptionCount).isEqualTo(12)
        assertThat(result[0].lecturerId).isEqualTo(10L)
        assertThat(result[0].lecturerName).isEqualTo("이호준")
    }
}
