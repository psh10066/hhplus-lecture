package com.hhplus.lecture.api.controller

import com.hhplus.lecture.domain.user.User
import com.hhplus.lecture.helper.testcontainers.BaseIntegrationTest
import com.hhplus.lecture.infrastructure.user.UserJpaRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class LectureControllerTest(
    @Autowired private val mockMvc: MockMvc,
    @Autowired private val userJpaRepository: UserJpaRepository,
    @Autowired private val jdbcTemplate: JdbcTemplate,
) : BaseIntegrationTest() {

    @BeforeEach
    fun setUp() {
        jdbcTemplate.execute("TRUNCATE TABLE user")
        userJpaRepository.save(User(name = "홍길동"))
    }

    // 특강 신청 API E2E 테스트
    @Test
    fun `특강 신청 API`() {
        mockMvc.perform(
            post("/api/lectures/{lectureId}/subscribe", 1L)
                .header("userId", 1L)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("result").value("SUCCESS"))
            .andExpect(jsonPath("data").value(null))
            .andExpect(jsonPath("error").value(null))
    }

    // 특강 신청 가능 목록 API E2E 테스트
    @Test
    fun `특강 신청 가능 목록 API`() {
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
            .andExpect(jsonPath("data.lectures[0].speakerId").value(1))
            .andExpect(jsonPath("data.lectures[0].speakerName").value("이호준"))
            .andExpect(jsonPath("error").value(null))
    }

    // 특강 신청 완료 목록 조회 API E2E 테스트
    @Test
    fun `특강 신청 완료 목록 조회 API`() {
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
            .andExpect(jsonPath("data.lectures[0].speakerId").value(1))
            .andExpect(jsonPath("data.lectures[0].speakerName").value("이호준"))
            .andExpect(jsonPath("error").value(null))
    }
}