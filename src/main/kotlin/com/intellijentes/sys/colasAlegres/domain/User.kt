package com.intellijentes.sys.colasAlegres.domain

/**
 * Modelo de dominio que representa a un Usuario en el sistema.
 * Este modelo contiene la estructura completa de la entidad,
 * a diferencia de los DTOs que solo transportan datos específicos.
 */
data class User(
    val id: String,
    val name: String,
    var email: String,
    var hashPassword : String? = null ,
    var zipCode: String,
    var isActive: Boolean = true
)
