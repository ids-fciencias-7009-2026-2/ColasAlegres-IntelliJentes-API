package com.intellijentes.sys.colasAlegres.models.entities

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.Instant

/** Crea el modelo relacional de la tabla para usuarios. */
@Entity
@Table(name = "users")
data class UserEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null,
    val name: String = "",
    var email: String = "",
    var password: String = "",
    val zipCode: String = "",
    val isActive: Boolean = true,
    var token: String? = null,
    var tokenExpiresAt: Instant? = null
)