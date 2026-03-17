package com.intellijentes.sys.colasAlegres.models.entities.dto.request

/**
 * DTO mediante el cual recibimos el correo y la contraseña como
 * cuerpo de petición para iniciar sesión a usuarios.
 * */
data class LoginUserRequest (
    val email: String,
    val hashPassword: String
)