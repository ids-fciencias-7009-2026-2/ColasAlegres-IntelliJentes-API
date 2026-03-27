package com.intellijentes.sys.colasAlegres.controllers

import com.intellijentes.sys.colasAlegres.models.domain.toUsuario
import com.intellijentes.sys.colasAlegres.models.dto.request.CreateUserRequest
import com.intellijentes.sys.colasAlegres.models.dto.request.LoginUserRequest
import com.intellijentes.sys.colasAlegres.models.dto.request.LogoutUserRequest
import com.intellijentes.sys.colasAlegres.models.dto.request.UpdateUserRequest
import com.intellijentes.sys.colasAlegres.models.dto.response.LogoutUser
import com.intellijentes.sys.colasAlegres.models.dto.response.UserResponse
import com.intellijentes.sys.colasAlegres.models.dto.response.toUserResponse
import com.intellijentes.sys.colasAlegres.services.UserService
import java.time.Instant
import java.util.Date
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

/**
 * Controlador encargado de responder a los endpoints REST relacionados con la gestión de usuarios.
 */
@RestController
@RequestMapping("/users")
class UserController(
        /* Integra y comunica la capa de servicio relacionada con el usuario */
        private val userService: UserService
) {

    /** Logger encargado de registrar eventos importantes en el flujo de ejecución. */
    val logger: Logger = LoggerFactory.getLogger(UserController::class.java)

    /**
     * Endpoint encargado de recuperar la información de un usuario autenticado.
     *
     * URL: http://localhost:8080/users/me
     *
     * Método: GET
     *
     * @return ResponseEntity con un objeto User y código HTTP 200 (ok).
     */
    @GetMapping("/me")
    fun retrieveUser(
            @RequestHeader(name = "Authorization", required = false) authorizationHeader: String?
    ): ResponseEntity<UserResponse> {
        val currentUser =
                userService.getCurrentUserByAuthorizationHeader(authorizationHeader)
                        ?: throw ResponseStatusException(
                                HttpStatus.UNAUTHORIZED,
                                "Token inválido o caducado"
                        )

        logger.info("Current user from session: {}", currentUser.email)

        return ResponseEntity.ok(currentUser.toUserResponse())
    }

    /**
     * Endpoint encargado de registrar a un usuario nuevo en la plataforma.
     *
     * URL: http://localhost:8080/users/register
     *
     * Método: POST
     *
     * @param createUserRequest DTO que representa el body del request.
     * @return ResponseEntity con el nuevo usuario creado y un código HTTP 200 (ok).
     */
    @PostMapping("/register")
    fun addUser(@RequestBody createUserRequest: CreateUserRequest): ResponseEntity<UserResponse> {

        if (!userService.isEmailValid(createUserRequest.email)) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Correo electrónico inválido")
        }

        val newUser = createUserRequest.toUsuario()
        val createdUser = userService.create(newUser)
        logger.info("New user to register : $newUser")

        return ResponseEntity.ok(createdUser.toUserResponse())
    }

    /**
     * Endpoint encargado de la autenticación de un usuario
     *
     * URL: http://localhost:8080/users/login
     *
     * Método: POST
     *
     * @param loginUserRequest DTO con las credenciales de acceso del usuario.
     * @return Código HTTP 200 si la autenticación con las credenciales fue correcta,
     * ```
     *      HTTP 400 si fue incorrecta
     * ```
     */
    @PostMapping("/login")
    fun loginUser(@RequestBody loginUserRequest: LoginUserRequest): ResponseEntity<Any> {

        logger.info("Try to login with the email: ${loginUserRequest.email}")

        val token = userService.login(loginUserRequest.email, loginUserRequest.hashPassword)

        return if (token != null) {
            ResponseEntity.ok(token)
        } else {
            throw ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Credenciales inválidas o usuario no registrado"
            )
        }
    }

    /**
     * Endpoint encargado de cerrar la sesión de un usuario
     *
     * URL: http://localhost:8080/users/logout
     *
     * Método: POST
     *
     * @param logoutUserRequest DTO con el token activo del usuario.
     * @return ResponseEntity con información de logout HTTP 200 si fue un éxito,
     * ```
     *      HTTP 401 si el token no es válido
     * ```
     */
    @PostMapping("/logout")
    fun logoutUser(
        @RequestHeader(name = "Authorization", required = false) authorizationHeader: String
    ): ResponseEntity<Any> {
        logger.info("Try to logout with token: $authorizationHeader}")
        val userName = userService.logout(authorizationHeader)
        return if (userName != null) {
            val logoutResponse = LogoutUser(userName, Date.from(Instant.now()))
            ResponseEntity.ok(logoutResponse)
        } else {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token inválido o caducado")
        }
    }

    /**
     * Endpoint encargado de actualizar la información del usuario
     *
     * URL: http//localhost:8080/users
     *
     * Método: PATCH
     *
     * @return ResponseEntity con la información del usuario actualizada
     */
    @PatchMapping
    fun updateInfoUser(
            @RequestHeader(name = "Authorization", required = false) authorizationHeader: String?,
            @RequestBody updateUserRequest: UpdateUserRequest
    ): ResponseEntity<UserResponse> {
        val updatedUser =
                userService.updateCurrentUser(
                        authorizationHeader = authorizationHeader,
                        newEmail = updateUserRequest.email,
                        newPassword = updateUserRequest.hashPassword
                )
                        ?: throw ResponseStatusException(
                                HttpStatus.UNAUTHORIZED,
                                "Token inválido o caducado"
                        )

        logger.info("Updated user for session: {}", updatedUser.email)

        return ResponseEntity.ok(updatedUser.toUserResponse())
    }
}
