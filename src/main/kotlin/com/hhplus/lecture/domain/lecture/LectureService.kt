package com.hhplus.lecture.domain.lecture

import com.hhplus.lecture.domain.user.UserInfo
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class LectureService(
    private val lectureRepository: LectureRepository
) {
    @Transactional
    fun subscribe(userInfo: UserInfo, lectureId: Long) {
        val lecture = lectureRepository.findById(lectureId)
        lecture.subscribe(userInfo)
    }

    fun getAvailableLectures(userInfo: UserInfo, date: LocalDate): List<LectureInfo> {
        return lectureRepository.getAvailableLectures(userInfo, date).map {
            LectureInfo.from(it)
        }
    }
}