package com.intellijentes.sys.colasAlegres.models

import com.intellijentes.sys.colasAlegres.models.domain.User

/* Regresa una instancia de EntidadUsuario a apartir del Usuario actual */
fun User.toUserEntity(): UserEntity {
    return UserEntity(
            id = this.id,
            name = this.name,
            email = this.email,
            password = hashPassword,
            zipCode = this.zipCode
    )
}

/* Regresa una instancia de Usuario apartir de la EntidadUsuario actual. */
fun UserEntity.toUser(): User {
    return User(
            id = this.id,
            name = this.name,
            email = this.email,
            hashPassword = this.password,
            zipCode = this.zipCode,
            isActive = this.isActive
    )
}
