package com.intellijentes.sys.colasAlegres.services

import com.intellijentes.sys.colasAlegres.models.entities.domain.User
import com.intellijentes.sys.colasAlegres.models.entities.toUserEntity
import com.intellijentes.sys.colasAlegres.repositories.UserRepository
import java.security.MessageDigest
import java.util.Locale
import org.springframework.stereotype.Service

/**
 * Capa de servicio: En esta clase se concentra la logica del negocio relacionada con el usuario.
 * Dentro del patron diseño repository, esta clase es se encarga de crear, actualizar y proveer de
 * informacion de usuario, de tal forma que es intermediaria entre UserController y el
 * UserRepository.
 *
 * @param userRepository: variable de clase de tipo UserRepository para interactuar con nuestro
 * repositorio de usuario.
 */
@Service
class UserService(private val userRepository: UserRepository) {

    companion object {
        private val EMAIL_REGEX = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
    }

    /**
     * Metodo que nos permite implementar una funcion hash para cifrar las credenciales sensibles
     * del sistema, como son contraseñas o tokens
     * @param userPassword: recibe la cotraseña sin cifrar.
     * @return encodedHash: la contraseña con hash aplicado.
     */
    private fun hashPassword(userPassword: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val encodedHash = digest.digest(userPassword.toByteArray())
        return encodedHash.joinToString("") { "%02x".format(it) }
    }

    /** Validación básica de correo electrónico. */
    fun isEmailValid(email: String): Boolean {
        val normalizedEmail = email.trim().lowercase(Locale.ROOT)
        return EMAIL_REGEX.matches(normalizedEmail)
    }

    /**
     * Metodo que crea una nueva instancia UserEntity para el userRepository. Recibe el usuario que
     * se intenta registrar, antes de guardarlo, asegurate que la contraseña este cifrada.
     *
     * @param user: Recibe un usuario.
     * @return user Regresa el mismo usuario con los campos que fueron creados con la contraseña
     * cifrada.
     */
    fun create(user: User): User {
        require(isEmailValid(user.email)) { "El correo electrónico no tiene un formato válido" }

        val currentHash = user.hashPassword
        user.hashPassword = hashPassword(currentHash)
        val userEntity = user.toUserEntity()
        userRepository.save(userEntity)
        return user
    }
}
