package com.hhplus.lecture.api.controller

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class LectureControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    // 특강 신청 API E2E 테스트
    @Test
    fun `특강 신청 API`() {
        mockMvc.perform(post("/api/lectures/{lectureId}/subscribe", 1L)
            .header("userId", 1L))
            .andExpect(status().isOk)
            .andExpect(jsonPath("result").value("SUCCESS"))
            .andExpect(jsonPath("data").value(null))
            .andExpect(jsonPath("error").value(null))
    }
}