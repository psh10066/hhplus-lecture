package com.hhplus.lecture.infrastructure.lecture

import com.hhplus.lecture.domain.lecture.Lecture
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface LectureJpaRepository : JpaRepository<Lecture, Long> {

    fun findAllByStartTimeGreaterThanEqualAndStartTimeLessThan(
        startTimeIsGreaterThanEqual: LocalDateTime,
        startTimeIsLessThan: LocalDateTime
    ): List<Lecture>

    fun findAllBySubscriptions_userId(userId: Long): List<Lecture>
}