package com.hhplus.lecture.domain.user

interface UserRepository {
    fun getById(id: Long): User
}