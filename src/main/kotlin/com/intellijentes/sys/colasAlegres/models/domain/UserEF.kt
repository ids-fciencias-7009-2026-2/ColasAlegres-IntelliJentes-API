package com.intellijentes.sys.colasAlegres.models.domain

import com.intellijentes.sys.colasAlegres.models.dto.request.CreateUserRequest

/**
 * Extension Function que convierte un DTO de tipo CreateUserRequest en un objeto de dominio Usuario
 */
fun CreateUserRequest.toUsuario(): User {
    // Creamos el objeto de dominio usando los datos del DTO
    return User(
            name = this.name,
            email = this.email,
            hashPassword = this.hashPassword,
            zipCode = this.zipCode,
            isActive = true
    )
}
