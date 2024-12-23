package com.hhplus.lecture.infrastructure.user

import com.hhplus.lecture.domain.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepository : JpaRepository<User, Long>