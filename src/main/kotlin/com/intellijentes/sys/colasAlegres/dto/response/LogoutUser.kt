package com.intellijentes.sys.colasAlegres.dto.response

import java.util.Date

/**
 * DTO mediante el cial enviamos una respuesta por el controlador  sobre el nombre
 * y la fecha actual de salida de sesion.
 * */
data class LogoutUser(
    val name: String,
    val time: Date
)