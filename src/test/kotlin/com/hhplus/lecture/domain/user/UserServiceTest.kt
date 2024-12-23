package com.hhplus.lecture.domain.user

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock

class UserServiceTest {

    private lateinit var userRepository: UserRepository
    private lateinit var userService: UserService

    @BeforeEach
    fun setUp() {
        userRepository = mock(UserRepository::class.java)
        userService = UserService(userRepository)
    }

    @Test
    fun `사용자 정보를 가져올 수 있다`() {
        // given
        given(userRepository.getById(1L)).willReturn(User(id = 1L, name = "홍길동"))

        // when
        val userInfo = userService.getUserInfo(1L)

        // then
        assert(userInfo.id == 1L)
        assert(userInfo.name == "홍길동")
    }
}