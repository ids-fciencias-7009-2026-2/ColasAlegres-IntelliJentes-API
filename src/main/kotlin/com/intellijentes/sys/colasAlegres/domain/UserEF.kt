package com.intellijentes.sys.colasAlegres.domain

import  com.intellijentes.sys.colasAlegres.dto.request.CreateUserRequest
import java.util.UUID

/**
 * Extension Fuction que convierte un DTO de tipo CreateUserRequest
 * en un objeto de dominio Usuario
 */

fun CreateUserRequest.toUsuario(): User{

    // Generamos un identificador único de forma simulada
    val id = "id-random-" + UUID.randomUUID().toString()

    // Creamos el objeto de dominio usando los datos del DTO
    return User(
        id = id,
        name = this.name,
        email = this.email,
        hashPassword = this.hashPassword,
        zipCode = this.zipCode,
        isActive = true
    )
}