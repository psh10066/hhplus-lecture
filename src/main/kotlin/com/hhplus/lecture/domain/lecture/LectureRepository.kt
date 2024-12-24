package com.hhplus.lecture.domain.lecture

import com.hhplus.lecture.domain.user.UserInfo
import java.time.LocalDate

interface LectureRepository {

    fun findById(lectureId: Long): Lecture

    fun getAvailableLectures(userInfo: UserInfo, date: LocalDate): List<Lecture>
}