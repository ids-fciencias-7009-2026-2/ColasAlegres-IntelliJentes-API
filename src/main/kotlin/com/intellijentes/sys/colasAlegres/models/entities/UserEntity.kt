package com.intellijentes.sys.colasAlegres.models.entities

import com.fasterxml.jackson.annotation.JsonInclude
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

/**
 * Crea el modelo relacional de la tabla para usuarios.
 * */
@Entity
@Table(name = "users")
data class UserEntity(
    @Id
    val id: String = UUID.randomUUID().toString(),
    val name: String = "",
    val email: String = "",
    val password: String,
    val zipCode: String = "",
    var token: String? = null
)
