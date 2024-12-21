package com.duyvv.iddoc.data.model

data class User(
    val name: String = "",
    val userName: String = "",
    val password: String = "",
    val role: UserRole
)

enum class UserRole {
    STUDENT, ADMIN
}
