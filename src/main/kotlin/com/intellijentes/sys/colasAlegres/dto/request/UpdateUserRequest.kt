package com.intellijentes.sys.colasAlegres.dto.request
/**
 *  DTO mediante el cual recibimos un la información que queremos actualizar
 *  por parte del usuario en sesión.
 * */
data class UpdateUserRequest (
    var email: String,
    var hashPassword: String
)