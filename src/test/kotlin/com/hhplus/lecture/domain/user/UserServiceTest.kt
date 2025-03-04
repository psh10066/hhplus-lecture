package com.hhplus.lecture.domain.user

import org.instancio.Instancio
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
        val user = Instancio.of(User::class.java).create()
        given(userRepository.getById(1L)).willReturn(user)

        // when
        val userInfo = userService.getUserInfo(1L)

        // then
        assert(userInfo.id == user.id)
        assert(userInfo.name == user.name)
    }
}