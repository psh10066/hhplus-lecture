package com.hhplus.lecture.domain.lecture

import com.hhplus.lecture.domain.user.UserInfo
import java.time.LocalDate

interface LectureRepository {

    fun getById(lectureId: Long): Lecture

    fun getAvailableLectures(userInfo: UserInfo, date: LocalDate): List<Lecture>

    fun getSubscriptions(userInfo: UserInfo): List<Lecture>
}