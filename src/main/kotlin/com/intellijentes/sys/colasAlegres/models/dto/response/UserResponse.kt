package com.intellijentes.sys.colasAlegres.models.dto.response

import com.intellijentes.sys.colasAlegres.models.domain.User

data class UserResponse(
        val id: Long?,
        val name: String,
        val email: String,
        val zipCode: String,
        val isActive: Boolean
)

fun User.toUserResponse(): UserResponse {
        return UserResponse(
                id = this.id,
                name = this.name,
                email = this.email,
                zipCode = this.zipCode,
                isActive = this.isActive
        )
}
