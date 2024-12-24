package com.hhplus.lecture.infrastructure.lecture

import com.hhplus.lecture.domain.lecture.Lecturer
import org.springframework.data.jpa.repository.JpaRepository

interface LecturerJpaRepository : JpaRepository<Lecturer, Long>