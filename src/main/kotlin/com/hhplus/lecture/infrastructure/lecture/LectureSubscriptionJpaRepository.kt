package com.hhplus.lecture.infrastructure.lecture

import com.hhplus.lecture.domain.lecture.LectureSubscription
import org.springframework.data.jpa.repository.JpaRepository

interface LectureSubscriptionJpaRepository : JpaRepository<LectureSubscription, Long>