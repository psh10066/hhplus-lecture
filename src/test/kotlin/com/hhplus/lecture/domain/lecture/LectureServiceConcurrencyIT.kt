package com.hhplus.lecture.domain.lecture

import com.hhplus.lecture.domain.user.UserInfo
import com.hhplus.lecture.helper.CleanUp
import com.hhplus.lecture.helper.KSelect.Companion.field
import com.hhplus.lecture.helper.testcontainers.BaseIntegrationTest
import com.hhplus.lecture.infrastructure.lecture.LectureJpaRepository
import com.hhplus.lecture.infrastructure.lecture.LectureSubscriptionJpaRepository
import com.hhplus.lecture.infrastructure.lecture.LecturerJpaRepository
import org.assertj.core.api.Assertions.assertThat
import org.instancio.Instancio
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.annotation.DirtiesContext
import java.util.concurrent.Executors
import java.util.concurrent.Future

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class LectureServiceConcurrencyIT(
    @Autowired private val lectureService: LectureService,
    @Autowired private val lecturerJpaRepository: LecturerJpaRepository,
    @Autowired private val lectureJpaRepository: LectureJpaRepository,
    @Autowired private val lectureSubscriptionJpaRepository: LectureSubscriptionJpaRepository,
    @Autowired private val cleanUp: CleanUp,
) : BaseIntegrationTest() {

    @BeforeEach
    fun setUp() {
        cleanUp.all()
        val lecturer = Instancio.of(Lecturer::class.java)
            .set(field(Lecturer::id), 0)
            .create()
        lecturerJpaRepository.save(lecturer)
        val lecture = Instancio.of(Lecture::class.java)
            .set(field(Lecture::id), 0)
            .set(field(Lecture::subscriptionCount), 0)
            .set(field(Lecture::subscriptions), mutableListOf<LectureSubscription>())
            .set(field(Lecture::lecturer), lecturer)
            .create()
        lectureJpaRepository.save(lecture)
    }

    @Test
    fun `동시에 동일한 특강에 대해 40명이 신청했을 때, 30명만 성공한다`() {
        // given
        val tasks = (1L..40L).map { id ->
            Runnable { lectureService.subscribe(UserInfo(id, "사용자$id"), 1L) }
        }.toTypedArray()

        // when
        concurrencyTestHelper(1, *tasks)

        // then
        val lecture = lectureJpaRepository.findByIdOrNull(1L)!!
        val lectureSubscriptions = lectureSubscriptionJpaRepository.findAll().filter { it.lecture == lecture }
        assertAll(
            { assertThat(lecture.subscriptionCount).isEqualTo(30) },
            { assertThat(lectureSubscriptions).hasSize(30) }
        )
    }

    @Test
    fun `동일한 유저 정보로 같은 특강을 5번 신청했을 때, 1번만 성공한다`() {
        // when
        val userInfo = UserInfo(id = 123L, "사용자")
        concurrencyTestHelper(5,
            Runnable { lectureService.subscribe(userInfo, 1L) }
        )

        // then
        val lecture = lectureJpaRepository.findByIdOrNull(1L)!!
        val lectureSubscriptions = lectureSubscriptionJpaRepository.findAll().filter { it.lecture == lecture && it.userId == 123L }
        assertAll(
            { assertThat(lecture.subscriptionCount).isEqualTo(1) },
            { assertThat(lectureSubscriptions).hasSize(1) }
        )
    }

    private fun concurrencyTestHelper(times: Int, vararg tasks: Runnable) {
        val executorService = Executors.newFixedThreadPool(times * tasks.size)
        try {
            val futures = mutableListOf<Future<*>>()
            repeat(times) {
                for (task in tasks) {
                    val future = executorService.submit(task)
                    futures.add(future)
                }
            }
            futures.forEach {
                try {
                    it.get()
                } catch (_: Exception) {}
            }
        } finally {
            executorService.shutdown()
        }
    }
}