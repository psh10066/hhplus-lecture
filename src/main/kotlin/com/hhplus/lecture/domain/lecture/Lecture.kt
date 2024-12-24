package com.hhplus.lecture.domain.lecture

import com.hhplus.lecture.domain.user.UserInfo
import com.hhplus.lecture.infrastructure.BaseEntity
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Lecture(
    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val startTime: LocalDateTime,

    @Column(nullable = false)
    val endTime: LocalDateTime,

    @Column(nullable = false)
    var subscriptionCount: Int = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecturer_id")
    val lecturer: Lecturer,

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "lecture")
    val subscriptions: MutableList<LectureSubscription>,
) : BaseEntity() {

    fun subscribe(userInfo: UserInfo) {
        subscriptions.add(LectureSubscription(this, userInfo.id))
        subscriptionCount++
    }
}