package com.hhplus.lecture.domain.lecture

import com.hhplus.lecture.domain.user.UserInfo
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class LectureService(
    private val lectureRepository: LectureRepository
) {
    fun getAvailableLectures(userInfo: UserInfo, date: LocalDate): List<LectureInfo> {
        return lectureRepository.getAvailableLectures(userInfo, date).map {
            LectureInfo.from(it)
        }
    }
}