package com.hhplus.lecture.domain.lecture

import com.hhplus.lecture.infrastructure.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity

@Entity
class Lecturer(
    @Column(nullable = false)
    val name: String,
) : BaseEntity()
