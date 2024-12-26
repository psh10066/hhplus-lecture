package com.hhplus.lecture.domain.lecture

import com.hhplus.lecture.infrastructure.BaseEntity
import jakarta.persistence.*

@Entity
@Table(
    name = "lecture_subscription",
    uniqueConstraints = [UniqueConstraint(columnNames = ["lecture_id", "userId"])]
)
class LectureSubscription(
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lecture_id", nullable = false, foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT))
    val lecture: Lecture,

    @Column(nullable = false)
    val userId: Long,
) : BaseEntity()