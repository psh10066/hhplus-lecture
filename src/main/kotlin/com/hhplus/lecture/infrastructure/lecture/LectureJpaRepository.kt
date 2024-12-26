package com.hhplus.lecture.infrastructure.lecture

import com.hhplus.lecture.domain.lecture.Lecture
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

interface LectureJpaRepository : JpaRepository<Lecture, Long> {

    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun findForUpdateById(id: Long): Lecture?

    @EntityGraph(attributePaths = ["lecturer", "subscriptions"])
    fun findAllByStartTimeGreaterThanEqualAndStartTimeLessThan(
        startTimeIsGreaterThanEqual: LocalDateTime,
        startTimeIsLessThan: LocalDateTime
    ): List<Lecture>

    @EntityGraph(attributePaths = ["lecturer"])
    fun findAllBySubscriptions_userId(userId: Long): List<Lecture>
}