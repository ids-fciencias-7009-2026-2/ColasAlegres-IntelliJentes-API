package com.intellijentes.sys.colasAlegres.models.domain

/**
 * Modelo de dominio que representa a un Usuario en el sistema. Este modelo contiene la estructura
 * completa de la entidad, a diferencia de los DTOs que solo transportan datos específicos.
 */
data class User(
        val id: Long? = null,
        val name: String,
        var email: String,
        var hashPassword: String,
        var zipCode: String,
        var isActive: Boolean = true
) {
    /*public fun getHashPassword(): String? {return this.hashPassword}
    public fun setHashPassword(hashPass: String?){this.hashPassword = hashPass}*/
}
