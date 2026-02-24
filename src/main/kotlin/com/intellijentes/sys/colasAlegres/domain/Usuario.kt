package com.intellijentes.sys.colasAlegres.domain

/**
 * Modelo de dominio que representa a un Usuario en el sistema.
 * Este modelo contiene la estructura completa de la entidad,
 * a diferencia de los DTOs que solo transportan datos específicos.
 */
data class Usuario(
    val id: String,
    val name: String,
    val email: String,
    val hashPassword: String,
    val zipCode: Int,
    val isActive: Boolean = true
)
