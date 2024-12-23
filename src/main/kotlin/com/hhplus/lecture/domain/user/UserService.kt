package com.hhplus.lecture.domain.user

import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {
    fun getUserInfo(userId: Long): UserInfo {
        val user = userRepository.getById(userId)
        return UserInfo.of(user)
    }
}