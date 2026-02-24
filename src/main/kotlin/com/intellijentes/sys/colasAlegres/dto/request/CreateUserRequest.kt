package com.intellijentes.sys.colasAlegres.dto.request

/**
 * DTO mediante el cual recibimos informacion de "creacion de usuario"
 * en la forma adecuada de transferencia.
 * */
data class CreateUserRequest (
    val name: String,
    val email: String,
    val hashPassword: String,
    val zipCode: Int
    )