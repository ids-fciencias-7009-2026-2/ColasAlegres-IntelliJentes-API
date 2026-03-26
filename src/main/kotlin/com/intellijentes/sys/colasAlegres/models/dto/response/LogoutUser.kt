package com.intellijentes.sys.colasAlegres.models.dto.response

import java.util.Date

/**
 * DTO mediante el cual enviamos una respuesta por el controlador sobre el nombre
 * y la fecha actual de salida de sesión.
 * */
data class LogoutUser(
    val name: String,
    val time: Date
)