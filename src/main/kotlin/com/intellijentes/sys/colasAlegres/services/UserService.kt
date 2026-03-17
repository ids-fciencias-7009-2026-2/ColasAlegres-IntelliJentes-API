package com.intellijentes.sys.colasAlegres.services

import org.springframework.stereotype.Service
import com.intellijentes.sys.colasAlegres.repositories.UserRepository
import com.intellijentes.sys.colasAlegres.models.entities.UserEntity

@Service
class UserService(private val userRepository: UserRepository)  {

    fun create(user: UserEntity) : UserEntity {
        return userRepository.save(user)
    }
}