package com.hhplus.lecture.domain.user

import com.hhplus.lecture.infrastructure.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "user")
class User(
    @Column(nullable = false)
    val name: String
) : BaseEntity()