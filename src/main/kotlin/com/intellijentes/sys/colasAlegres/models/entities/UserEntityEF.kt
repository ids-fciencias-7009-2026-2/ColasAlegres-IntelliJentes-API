package com.intellijentes.sys.colasAlegres.models.entities

import com.intellijentes.sys.colasAlegres.models.entities.domain.User



/* Regresa una instancia de EntidadUsuario a apartir del Usuario actual */
fun User.toUserEntity(): UserEntity {
    return UserEntity(
        id = id,
        name = this.name,
        email = this.email,
        password = hashPassword,
        zipCode = this.zipCode
    )

}
/* Regresa una instancia de Usuario apartir de la EntidadUsuario actual (vemos aun) */