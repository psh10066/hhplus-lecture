package com.hhplus.lecture.infrastructure.user

import com.hhplus.lecture.domain.user.User
import com.hhplus.lecture.domain.user.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class UserRepositoryImpl(
    private val userJpaRepository: UserJpaRepository
) : UserRepository {

    override fun getById(id: Long): User {
        return userJpaRepository.findByIdOrNull(id)
            ?: throw IllegalArgumentException("존재하지 않는 사용자입니다.")
    }
}