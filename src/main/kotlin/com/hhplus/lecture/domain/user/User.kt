package com.hhplus.lecture.domain.user

import com.hhplus.lecture.infrastructure.BaseEntity
import jakarta.persistence.*

@Entity
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val name: String
) : BaseEntity()