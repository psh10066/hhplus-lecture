package com.hhplus.lecture.domain.lecture

import com.hhplus.lecture.domain.user.UserInfo
import com.hhplus.lecture.infrastructure.BaseEntity
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "lecture")
class Lecture(
    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val startTime: LocalDateTime,

    @Column(nullable = false)
    val endTime: LocalDateTime,

    @Column(nullable = false)
    var subscriptionCount: Int = 0,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lecturer_id", nullable = false, foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT))
    val lecturer: Lecturer,

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "lecture", cascade = [CascadeType.PERSIST])
    val subscriptions: MutableList<LectureSubscription> = mutableListOf(),
) : BaseEntity() {

    fun subscribe(userInfo: UserInfo) {
        check(subscriptionCount < MAX_SUBSCRIPTION_COUNT) { "특강 신청 인원이 초과되었습니다." }
        check(subscriptions.none { it.userId == userInfo.id }) { "이미 신청한 특강입니다." }

        subscriptions.add(LectureSubscription(this, userInfo.id))
        subscriptionCount++
    }
}