package com.intellijentes.sys.colasAlegres.controllers

import com.intellijentes.sys.colasAlegres.models.entities.domain.User
import com.intellijentes.sys.colasAlegres.models.entities.domain.toUsuario
import com.intellijentes.sys.colasAlegres.models.entities.dto.request.CreateUserRequest
import com.intellijentes.sys.colasAlegres.models.entities.dto.request.LoginUserRequest
import com.intellijentes.sys.colasAlegres.models.entities.dto.request.LogoutUserRequest
import com.intellijentes.sys.colasAlegres.models.entities.dto.request.UpdateUserRequest
import com.intellijentes.sys.colasAlegres.models.entities.dto.response.LogoutUser
import com.intellijentes.sys.colasAlegres.services.UserService
import org.springframework.http.HttpStatus
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.time.Instant
import java.util.Date
import java.util.UUID

/**
 * Controlador encargado de responder a los endpoints REST relacionados
 * con la gestión de usuarios.
 *
 */
@RestController
@RequestMapping("/users")
class UserController(
    /* Integra y comunica la capa de servicio relacionada con el usuario */
    private val userService: UserService
) {

    /**
     * Logger encargado de registrar eventos importantes en el flujo de ejecución.
     */
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
    fun retrieveUser() : ResponseEntity<User> {
        val fakeUser = User(
            "id-random" + UUID.randomUUID().toString(),
            "Intellij-entes",
            "intellij-entes@example.com",
            "123456",
            "04510"
        )

        logger.info("User found in system: $fakeUser")

        return ResponseEntity.ok(fakeUser)
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
    fun addUser(
        @RequestBody createUserRequest: CreateUserRequest
    ): ResponseEntity<User> {

        if (!userService.isEmailValid(createUserRequest.email)) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Correo electrónico inválido")
        }

        val newUser = createUserRequest.toUsuario()
        userService.create(newUser)
        logger.info("New user to register : $newUser")

        return ResponseEntity.ok(newUser)
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
     *      HTTP 400 si fue incorrecta
     */
    @PostMapping("/login")
    fun loginUser(
        @RequestBody loginUserRequest: LoginUserRequest
    ): ResponseEntity<Any> {

        logger.info("Try to login with the email: ${loginUserRequest.email}")

        val token = userService.login(loginUserRequest.email, loginUserRequest.hashPassword)

        return if (token != null) {
            ResponseEntity.ok(token)
        } else {
            ResponseEntity.status(401).build()
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
     *      HTTP 401 si el token no es válido
     */
    @PostMapping("/logout")
    fun logoutUser(@RequestBody logoutUserRequest: LogoutUserRequest): ResponseEntity<Any> {
        logger.info("Try to logout with token: ${logoutUserRequest.token}")
        val userName = userService.logout(logoutUserRequest.token)
        return if (userName != null) {
            val logoutResponse = LogoutUser(userName, Date.from(Instant.now()))
            ResponseEntity.ok(logoutResponse)
        } else {
            ResponseEntity.status(401).build()
        }
    }

    /**
     * Endpoint encargado de actualizar la información del usuario
     *
     * URL: http//localhost:8080/users
     *
     * Método: PUT
     *
     * @return ResponseEntity con la información del usuario actualizada
     */
    @PutMapping
    fun updateInfoUser(
        @RequestBody updateUserRequest: UpdateUserRequest
    ): ResponseEntity<Any> {

        if (!userService.isEmailValid(updateUserRequest.email)) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Correo electrónico inválido")
        }

        val fakeUser = User(
            "id-random" + UUID.randomUUID().toString(),
            "Intellij-entes",
            "intellijentes@example.com",
            "123456",
            "04510"
        )

        logger.info("User found: $fakeUser")
        logger.info("Info to update: $updateUserRequest")

        fakeUser.hashPassword = updateUserRequest.hashPassword
        fakeUser.email =  updateUserRequest.email

        return  ResponseEntity.ok(fakeUser)
    }



}
