package com.hhplus.lecture.infrastructure.lecture

import com.hhplus.lecture.domain.lecture.Lecture
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface LectureJpaRepository : JpaRepository<Lecture, Long> {

    @EntityGraph(attributePaths = ["lecturer", "subscriptions"])
    fun findAllByStartTimeGreaterThanEqualAndStartTimeLessThan(
        startTimeIsGreaterThanEqual: LocalDateTime,
        startTimeIsLessThan: LocalDateTime
    ): List<Lecture>

    @EntityGraph(attributePaths = ["lecturer"])
    fun findAllBySubscriptions_userId(userId: Long): List<Lecture>
}