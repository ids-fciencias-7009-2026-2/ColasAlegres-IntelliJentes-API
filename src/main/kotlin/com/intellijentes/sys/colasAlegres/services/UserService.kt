package com.intellijentes.sys.colasAlegres.services

import org.springframework.stereotype.Service
import com.intellijentes.sys.colasAlegres.repositories.UserRepository
import com.intellijentes.sys.colasAlegres.models.entities.UserEntity
import com.intellijentes.sys.colasAlegres.models.entities.domain.User
import com.intellijentes.sys.colasAlegres.models.entities.toUserEntity

@Service
class UserService(private val userRepository: UserRepository)  {

    fun create(user: User) : User {
        val userEntity = user.toUserEntity()
        userRepository.save(userEntity)
        return user
    }
}