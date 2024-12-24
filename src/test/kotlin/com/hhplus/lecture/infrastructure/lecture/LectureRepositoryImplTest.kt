package com.hhplus.lecture.infrastructure.lecture

import com.hhplus.lecture.domain.lecture.Lecture
import com.hhplus.lecture.domain.lecture.LectureSubscription
import com.hhplus.lecture.domain.lecture.Lecturer
import com.hhplus.lecture.domain.user.UserInfo
import com.hhplus.lecture.helper.KSelect.Companion.field
import com.hhplus.lecture.helper.testcontainers.BaseIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.instancio.Instancio
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import java.time.LocalDate
import java.time.LocalDateTime

@SpringBootTest
class LectureRepositoryImplTest(
    @Autowired private val lectureRepositoryImpl: LectureRepositoryImpl,
    @Autowired private val lectureJpaRepository: LectureJpaRepository,
    @Autowired private val lecturerJpaRepository: LecturerJpaRepository,
    @Autowired private val lectureSubscriptionJpaRepository: LectureSubscriptionJpaRepository,
    @Autowired private val jdbcTemplate: JdbcTemplate
) : BaseIntegrationTest() {
    private lateinit var lecturer: Lecturer

    @BeforeEach
    fun setUp() {
        lecturer = Instancio.of(Lecturer::class.java)
            .set(field(Lecturer::id), 0)
            .create()
        lecturerJpaRepository.save(lecturer)
    }

    @AfterEach
    fun tearDown() {
        jdbcTemplate.execute("TRUNCATE TABLE lecture")
        jdbcTemplate.execute("TRUNCATE TABLE lecturer")
        jdbcTemplate.execute("TRUNCATE TABLE lecture_subscription")
    }

    @Test
    fun `특강 정보를 가져올 수 있다`() {
        // given
        saveLecture(LocalDateTime.of(2023, 12, 23, 10, 0))

        // when
        val result = lectureRepositoryImpl.getById(1L)

        // then
        assertThat(result.id).isEqualTo(1L)
    }

    @Test
    fun `특강 목록 조회 시 해당 날짜의 특강만 조회할 수 있다`() {
        // given
        saveLecture(LocalDateTime.of(2023, 12, 22, 23, 59, 59))
        saveLecture(LocalDateTime.of(2023, 12, 23, 0, 0, 0))
        saveLecture(LocalDateTime.of(2023, 12, 23, 23, 59, 59))
        saveLecture(LocalDateTime.of(2023, 12, 24, 0, 0, 0))

        // when
        val date = LocalDate.of(2023, 12, 23)
        val result = lectureRepositoryImpl.getAvailableLectures(UserInfo(123L, "홍길동"), date)

        // then
        assertThat(result).hasSize(2)
        assertThat(result[0].startTime.toLocalDate()).isEqualTo(date)
        assertThat(result[1].startTime.toLocalDate()).isEqualTo(date)
    }

    @Test
    fun `특강 신청 완료 목록 조회 시 해당 사용자의 특강만 조회할 수 있다`() {
        // given
        val lecture1 = saveLecture(LocalDateTime.of(2023, 12, 23, 12, 0, 0))
        val lecture2 = saveLecture(LocalDateTime.of(2023, 12, 23, 13, 0, 0))
        val lecture3 = saveLecture(LocalDateTime.of(2023, 12, 23, 14, 0, 0))
        lectureSubscriptionJpaRepository.save(LectureSubscription(lecture1, 1))
        lectureSubscriptionJpaRepository.save(LectureSubscription(lecture2, 2))
        lectureSubscriptionJpaRepository.save(LectureSubscription(lecture3, 3))

        // when
        val result = lectureRepositoryImpl.getSubscriptions(UserInfo(2L, "홍길동"))

        // then
        assertThat(result).hasSize(1)
        assertThat(result[0].id).isEqualTo(2L)
    }

    private fun saveLecture(startTime: LocalDateTime): Lecture {
        return Instancio.of(Lecture::class.java)
            .set(field(Lecture::id), 0)
            .set(field(Lecture::lecturer), lecturer)
            .set(field(Lecture::startTime), startTime)
            .create()
            .also { lectureJpaRepository.save(it) }
    }
}