package com.intellijentes.sys.colasAlegres.services

import org.springframework.stereotype.Service
import com.intellijentes.sys.colasAlegres.repositories.UserRepository
import com.intellijentes.sys.colasAlegres.models.entities.domain.User
import com.intellijentes.sys.colasAlegres.models.entities.toUserEntity
import java.security.MessageDigest

/**
 *  Capa de servicio: En esta clase se concentra la lógica del negocio relacionada
 *  con el usuario. Dentro del patron diseño repository, esta clase es se encarga de
 *  crear, actualizar y proveer de información de usuario, de tal forma que
 *  es intermediaria entre UserController y el UserRepository.
 *
 * @param userRepository: variable de clase de tipo UserRepository para interactuar con
 * nuestro repositorio de usuario.
 * */
@Service
class UserService(private val userRepository: UserRepository)  {

    /**
     *  Método que nos permite implementar una función hash para cifrar las
     *  credenciales sensibles del sistema, como son contraseñas o tokens
     *  @param userPassword: recibe la contraseña sin cifrar.
     *  @return encodedHash: la contraseña con hash aplicado.
     * */
    private fun hashPassword(userPassword: String): String {
        val digest  = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(userPassword.toByteArray())
        return hashBytes.joinToString("") { "%02x".format(it) }
    }

    /**
     * Método que crea una nueva instancia UserEntity para el userRepository.
     * Recibe el usuario que se intenta registrar, antes de guardarlo, asegurate
     * que la contraseña esté cifrada.
     *
     * @param user: Recibe un usuario.
     * @return user Regresa el mismo usuario con los campos que fueron
     * creados con la contraseña cifrada.
     * */
    fun create(user: User) : User {
        val currentHash = user.hashPassword
        user.hashPassword = hashPassword(currentHash)
        val userEntity = user.toUserEntity()
        userRepository.save(userEntity)
        return user
    }
}