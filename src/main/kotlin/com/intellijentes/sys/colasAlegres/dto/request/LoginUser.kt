package com.intellijentes.sys.colasAlegres.dto.request

/**
 * DTO mediante el cual recibimos el correo y la contraseña como
 * cuerpo de peticion para iniciar sesion a usuarios.
 * */
data class LoginUser (
    val email: String,
    val hashPassword: String
)