package com.intellijentes.sys.colasAlegres.services

import com.intellijentes.sys.colasAlegres.models.entities.UserEntity
import com.intellijentes.sys.colasAlegres.models.domain.User
import com.intellijentes.sys.colasAlegres.models.entities.toUser
import com.intellijentes.sys.colasAlegres.models.entities.toUserEntity
import com.intellijentes.sys.colasAlegres.repositories.UserRepository
import java.security.MessageDigest
import java.time.Duration
import java.time.Instant
import java.util.Locale
import java.util.UUID
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
        private val TOKEN_TTL: Duration = Duration.ofMinutes(30)
    }

    private fun normalizeEmail(email: String): String {
        return email.trim().lowercase(Locale.ROOT)
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
        val normalizedEmail = normalizeEmail(email)
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
        require(user.name.isNotBlank()) { "El nombre es obligatorio" }
        require(user.hashPassword.isNotBlank()) { "La contraseña es obligatoria" }
        require(isEmailValid(user.email)) { "El correo electrónico no tiene un formato válido" }

        val normalizedEmail = normalizeEmail(user.email)
        check(userRepository.findByEmailIgnoreCase(normalizedEmail) == null) {
            "Ya existe un usuario con ese correo electrónico"
        }

        val currentHash = user.hashPassword
        user.email = normalizedEmail
        user.hashPassword = hashPassword(currentHash)
        val userEntity = user.toUserEntity()
        userRepository.save(userEntity)
        return user
    }

    /**
     * Metodo que realiza el inicio de sesion de un usuario.
     *
     * Un usuario solo tiene permitido iniciar sesion si sus credenciales son correctas y si ya
     * tiene una cuenta registrada en la base de datos, de lo contrario no podra acceder.
     *
     * @param email el correo electronico del usuario.
     * @param password la contrasenia sin hasheo del usuario.
     * @return String el token generado para su inicio de sesion.
     */
    fun login(email: String, password: String): String? {
        if (email.isBlank() || password.isBlank()) {
            return null
        }

        val normalizedEmail = normalizeEmail(email)
        val searchedUser = userRepository.findByEmailIgnoreCase(normalizedEmail) ?: return null
        val hashedPassword = hashPassword(password)
        if (searchedUser.password != hashedPassword) {
            return null
        }

        val token = UUID.randomUUID().toString()
        searchedUser.token = token
        searchedUser.tokenExpiresAt = Instant.now().plus(TOKEN_TTL)
        userRepository.save(searchedUser)
        return token
    }

    fun getCurrentUserByAuthorizationHeader(authorizationHeader: String?): User? {
        val token = extractBearerToken(authorizationHeader) ?: return null
        val searchedUser = userRepository.findByToken(token) ?: return null
        if (!isTokenValid(searchedUser)) {
            invalidateToken(searchedUser)
            return null
        }

        return searchedUser.toUser()
    }

    fun updateCurrentUser(
            authorizationHeader: String?,
            newEmail: String?,
            newPassword: String?
    ): User? {
        if (newEmail == null && newPassword == null) {
            throw IllegalArgumentException("Debes enviar al menos un campo para actualizar")
        }

        val token = extractBearerToken(authorizationHeader) ?: return null
        val searchedUser = userRepository.findByToken(token) ?: return null
        if (!isTokenValid(searchedUser)) {
            invalidateToken(searchedUser)
            return null
        }

        if (newEmail != null) {
            if (!isEmailValid(newEmail)) {
                throw IllegalArgumentException("El correo electrónico no tiene un formato válido")
            }

            val normalizedEmail = normalizeEmail(newEmail)
            val userWithSameEmail = userRepository.findByEmailIgnoreCase(normalizedEmail)
            if (userWithSameEmail != null && userWithSameEmail.id != searchedUser.id) {
                throw IllegalStateException("Ya existe un usuario con ese correo electrónico")
            }
            searchedUser.email = normalizedEmail
        }

        if (newPassword != null) {
            if (newPassword.isBlank()) {
                throw IllegalArgumentException("La contraseña no puede estar vacía")
            }
            searchedUser.password = hashPassword(newPassword)
        }

        userRepository.save(searchedUser)
        return searchedUser.toUser()
    }

    /**
     * Metodo que realiza el cierre de sesion de un usuario.
     *
     * Busca al usuario por su token activo y lo invalida, de lo contrario retorna null si el token
     * no existe.
     *
     * @param token el token activo de la sesion del usuario.
     * @return String el nombre del usuario que cerro sesion, o null si el token es invalido.
     */
    fun logout(token: String): String? {
        if (token.isBlank()) {
            return null
        }

        val searchedUser = userRepository.findByToken(token) ?: return null
        if (!isTokenValid(searchedUser)) {
            invalidateToken(searchedUser)
            return null
        }

        invalidateToken(searchedUser)
        return searchedUser.name
    }

    private fun extractBearerToken(authorizationHeader: String?): String? {
        if (authorizationHeader.isNullOrBlank()) {
            return null
        }

        val prefix = "Bearer "
        if (!authorizationHeader.startsWith(prefix, ignoreCase = true)) {
            return null
        }

        val token = authorizationHeader.substring(prefix.length).trim()
        return token.ifBlank { null }
    }

    private fun isTokenValid(user: UserEntity): Boolean {
        val expiresAt = user.tokenExpiresAt ?: return false
        return expiresAt.isAfter(Instant.now())
    }

    private fun invalidateToken(user: UserEntity) {
        user.token = null
        user.tokenExpiresAt = null
        userRepository.save(user)
    }
}
