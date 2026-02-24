package com.intellijentes.sys.colasAlegres.dto.request

data class CreateUserRequest (
    val name: String,
    val email: String,
    val hashPassword: String,
    val zipCode: Int,
    )