package com.hhplus.lecture.infrastructure.user

import com.hhplus.lecture.domain.user.User
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate

@SpringBootTest
class UserRepositoryImplTest {

    @Autowired
    private lateinit var userRepositoryImpl: UserRepositoryImpl

    @Autowired
    private lateinit var userJpaRepository: UserJpaRepository

    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate

    @BeforeEach
    fun setUp() {
        jdbcTemplate.execute("TRUNCATE TABLE user")
        userJpaRepository.save(User(name = "홍길동"))
    }

    @Test
    fun `사용자가 존재하면 정보를 가져올 수 있다`() {
        // when
        val result = userRepositoryImpl.getById(1L)

        // then
        assert(result.id == 1L)
        assert(result.name == "홍길동")
    }

    @Test
    fun `사용자가 없으면 예외가 발생한다`() {
        // when then
        assertThatThrownBy {
            userRepositoryImpl.getById(2L)
        }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("존재하지 않는 사용자입니다.")
    }
}