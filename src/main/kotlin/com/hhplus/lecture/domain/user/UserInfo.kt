package com.hhplus.lecture.domain.user

data class UserInfo(
    val id: Long,
    val name: String
) {
    companion object {
        fun of(user: User): UserInfo {
            return UserInfo(user.id, user.name)
        }
    }
}
