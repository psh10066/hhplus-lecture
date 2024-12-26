package com.hhplus.lecture.api.controller

import com.hhplus.lecture.domain.lecture.Lecture
import com.hhplus.lecture.domain.lecture.LectureSubscription
import com.hhplus.lecture.domain.lecture.Lecturer
import com.hhplus.lecture.domain.user.User
import com.hhplus.lecture.helper.CleanUp
import com.hhplus.lecture.helper.testcontainers.BaseIntegrationTest
import com.hhplus.lecture.infrastructure.lecture.LectureJpaRepository
import com.hhplus.lecture.infrastructure.lecture.LectureSubscriptionJpaRepository
import com.hhplus.lecture.infrastructure.lecture.LecturerJpaRepository
import com.hhplus.lecture.infrastructure.user.UserJpaRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDateTime

@SpringBootTest
@AutoConfigureMockMvc
class LectureControllerTest(
    @Autowired private val mockMvc: MockMvc,
    @Autowired private val lectureJpaRepository: LectureJpaRepository,
    @Autowired private val lecturerJpaRepository: LecturerJpaRepository,
    @Autowired private val lectureSubscriptionJpaRepository: LectureSubscriptionJpaRepository,
    @Autowired private val userJpaRepository: UserJpaRepository,
    @Autowired private val cleanUp: CleanUp,
) : BaseIntegrationTest() {
    private lateinit var lecture: Lecture

    @BeforeEach
    fun setUp() {
        cleanUp.all()
        userJpaRepository.save(User(name = "홍길동"))

        val lecturer = lecturerJpaRepository.save(Lecturer(name = "이호준"))
        lecture = lectureJpaRepository.save(
            Lecture(
                name = "테스트 강연",
                startTime = LocalDateTime.of(2024, 12, 23, 12, 0, 0),
                endTime = LocalDateTime.of(2024, 12, 23, 13, 0, 0),
                lecturer = lecturer,
            )
        )
    }

    // 특강 신청 API E2E 테스트
    @Test
    fun `특강 신청 API`() {
        // when
        mockMvc.perform(
            post("/api/lectures/{lectureId}/subscribe", 1L)
                .header("userId", 1L)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("result").value("SUCCESS"))
            .andExpect(jsonPath("data").value(null))
            .andExpect(jsonPath("error").value(null))

        // then
        val lectureSubscription = lectureSubscriptionJpaRepository.findByIdOrNull(1L)
        assertThat(lectureSubscription).isNotNull
    }

    // 특강 신청 가능 목록 API E2E 테스트
    @Test
    fun `특강 신청 가능 목록 API`() {
        // when then
        mockMvc.perform(
            get("/api/lectures/{date}/available", "2024-12-23")
                .header("userId", 1L)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("result").value("SUCCESS"))
            .andExpect(jsonPath("data.lectures[0].id").value(1))
            .andExpect(jsonPath("data.lectures[0].name").value("테스트 강연"))
            .andExpect(jsonPath("data.lectures[0].startTime").value("2024-12-23T12:00:00"))
            .andExpect(jsonPath("data.lectures[0].endTime").value("2024-12-23T13:00:00"))
            .andExpect(jsonPath("data.lectures[0].lecturerId").value(1))
            .andExpect(jsonPath("data.lectures[0].lecturerName").value("이호준"))
            .andExpect(jsonPath("data.lectures[0].lectureSubscriptionStatus").value("AVAILABLE"))
            .andExpect(jsonPath("error").value(null))
    }

    // 특강 신청 완료 목록 조회 API E2E 테스트
    @Test
    fun `특강 신청 완료 목록 조회 API`() {
        // given
        lectureSubscriptionJpaRepository.save(LectureSubscription(lecture = lecture, userId = 1L))

        // when then
        mockMvc.perform(
            get("/api/lectures/subscriptions")
                .header("userId", 1L)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("result").value("SUCCESS"))
            .andExpect(jsonPath("data.lectures[0].id").value(1))
            .andExpect(jsonPath("data.lectures[0].name").value("테스트 강연"))
            .andExpect(jsonPath("data.lectures[0].startTime").value("2024-12-23T12:00:00"))
            .andExpect(jsonPath("data.lectures[0].endTime").value("2024-12-23T13:00:00"))
            .andExpect(jsonPath("data.lectures[0].lecturerId").value(1))
            .andExpect(jsonPath("data.lectures[0].lecturerName").value("이호준"))
            .andExpect(jsonPath("error").value(null))
    }
}