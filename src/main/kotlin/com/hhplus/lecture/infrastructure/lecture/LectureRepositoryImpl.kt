package com.hhplus.lecture.infrastructure.lecture

import com.hhplus.lecture.domain.lecture.Lecture
import com.hhplus.lecture.domain.lecture.LectureRepository
import com.hhplus.lecture.domain.user.UserInfo
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class LectureRepositoryImpl(
    private val lectureJpaRepository: LectureJpaRepository,
) : LectureRepository {
    override fun getById(lectureId: Long): Lecture {
        return lectureJpaRepository.findById(lectureId)
            .orElseThrow { IllegalArgumentException("존재하지 않는 강의입니다.") }
    }

    override fun getAvailableLectures(userInfo: UserInfo, date: LocalDate): List<Lecture> {
        return lectureJpaRepository.findAllByStartTimeGreaterThanEqualAndStartTimeLessThan(
            date.atStartOfDay(),
            date.plusDays(1).atStartOfDay()
        )
    }

    override fun getSubscriptions(userInfo: UserInfo): List<Lecture> {
        return lectureJpaRepository.findAllBySubscriptions_userId(userInfo.id)
    }
}