package com.hhplus.lecture.domain.user

import com.hhplus.lecture.infrastructure.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity

@Entity
class User(
    @Column(nullable = false)
    val name: String
) : BaseEntity()