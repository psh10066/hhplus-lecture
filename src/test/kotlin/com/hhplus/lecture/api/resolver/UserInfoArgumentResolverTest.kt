package com.hhplus.lecture.api.resolver

import com.hhplus.lecture.domain.user.UserInfo
import com.hhplus.lecture.domain.user.UserService
import jakarta.servlet.http.HttpServletRequest
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.web.context.request.NativeWebRequest

class UserInfoArgumentResolverTest {

    private lateinit var webRequest: NativeWebRequest
    private lateinit var userService: UserService
    private lateinit var userInfoArgumentResolver: UserInfoArgumentResolver

    @BeforeEach
    fun setUp() {
        webRequest = mock(NativeWebRequest::class.java)
        userService = mock(UserService::class.java)
        userInfoArgumentResolver = UserInfoArgumentResolver(userService)
    }

    @Test
    fun `헤더에 userId가 없으면 예외가 발생한다`() {
        // given
        val httpServletRequest = MockHttpServletRequest()
        given(webRequest.getNativeRequest(HttpServletRequest::class.java)).willReturn(httpServletRequest)

        // when then
        assertThatThrownBy {
            userInfoArgumentResolver.resolveArgument(mock(), mock(), webRequest, mock())
        }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("접근이 거부되었습니다.")
    }

    @Test
    fun `헤더의 userId에 해당하는 사용자가 없으면 예외가 발생한다`() {
        // given
        val httpServletRequest = MockHttpServletRequest()
        httpServletRequest.addHeader("userId", "1")
        given(webRequest.getNativeRequest(HttpServletRequest::class.java)).willReturn(httpServletRequest)
        given(userService.getUserInfo(1L)).willThrow(IllegalArgumentException::class.java)

        // when then
        assertThatThrownBy {
            userInfoArgumentResolver.resolveArgument(mock(), mock(), webRequest, mock())
        }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("접근이 거부되었습니다.")
    }

    @Test
    fun `헤더의 userId에 해당하는 사용자가 있으면 정상적으로 반환한다`() {
        // given
        val httpServletRequest = MockHttpServletRequest()
        httpServletRequest.addHeader("userId", "1")
        given(webRequest.getNativeRequest(HttpServletRequest::class.java)).willReturn(httpServletRequest)
        given(userService.getUserInfo(1L)).willReturn(UserInfo(1L, "홍길동"))

        // when
        val result = userInfoArgumentResolver.resolveArgument(mock(), mock(), webRequest, mock())

        // then
        val userInfo = result as UserInfo
        assert(userInfo.id == 1L)
        assert(userInfo.name == "홍길동")
    }
}