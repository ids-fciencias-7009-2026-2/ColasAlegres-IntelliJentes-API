package com.intellijentes.sys.colasAlegres.dto.request
/**
 *  DTO mediante el cual recibimos un la informacion que queremos actualizar
 *  por parte del usuario en sesion.
 * */
data class UpdateUserRequest (
    var email: String,
    var hashPassword: String
)