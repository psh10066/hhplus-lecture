package com.hhplus.helper

import com.hhplus.lecture.infrastructure.BaseEntity

fun <T : BaseEntity> T.withId(id: Long): T {
    val field = this.javaClass.superclass.getDeclaredField("id")
    field.isAccessible = true
    field.set(this, id)
    return this
}
