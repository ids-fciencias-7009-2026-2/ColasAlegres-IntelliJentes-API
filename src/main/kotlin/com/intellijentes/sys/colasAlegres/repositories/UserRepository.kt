package com.intellijentes.sys.colasAlegres.repositories

import com.intellijentes.sys.colasAlegres.models.entities.UserEntity
import org.springframework.data.repository.CrudRepository

/**
 * Crea una interfaz UserRepository, aquella clase que la implemente tendrá definido el
 * comportamiento de un repositorio, es decir, podrá acceder a los métodos CRUD del mismo.
 */
interface UserRepository : CrudRepository<UserEntity, Long> {
    fun findByEmail(email: String): UserEntity?
    fun findByEmailIgnoreCase(email: String): UserEntity?
    fun findByToken(token: String): UserEntity?
}
