package com.hhplus.lecture.domain.lecture

import com.hhplus.lecture.infrastructure.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne

@Entity
class LectureSubscription(
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    val lecture: Lecture,

    @Column(nullable = false)
    val userId: Long,
) : BaseEntity()