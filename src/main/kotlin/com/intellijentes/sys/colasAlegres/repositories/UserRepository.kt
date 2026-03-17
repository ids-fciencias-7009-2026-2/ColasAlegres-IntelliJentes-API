package com.intellijentes.sys.colasAlegres.repositories

import com.intellijentes.sys.colasAlegres.models.entities.UserEntity
import org.springframework.data.repository.CrudRepository

/**
 *  Crea una interfaz UserRepositry, aquella clase que la implemente
 *  tendra definido el comportamiento de un respositorio, es decir,
 *  podra acceder a los metodos CRUD del mismo.
 *
 * */

interface UserRepository : CrudRepository<UserEntity, String>