package com.intellijentes.sys.colasAlegres.models.dto.request
/**
 * DTO mediante el cual recibimos un la información que queremos actualizar por parte del usuario en
 * sesión.
 */
data class UpdateUserRequest(var email: String? = null, var hashPassword: String? = null)
