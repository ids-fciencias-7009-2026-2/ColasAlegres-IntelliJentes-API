package com.intellijentes.sys.colasAlegres.controllers

import com.intellijentes.sys.colasAlegres.domain.User
import com.intellijentes.sys.colasAlegres.domain.toUsuario
import com.intellijentes.sys.colasAlegres.dto.request.CreateUserRequest
import com.intellijentes.sys.colasAlegres.dto.request.LoginUserRequest
import com.intellijentes.sys.colasAlegres.dto.request.UpdateUserRequest
import com.intellijentes.sys.colasAlegres.dto.response.LogoutUser
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import java.time.Instant
import java.util.Date
import java.util.UUID

/**
 * Controlador encargado de responder a los endpoints REST relacionados
 * con la gestión de usuarios.
 *
 */
@Controller
@RequestMapping("/users")
class UserController {

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

        val newUser = createUserRequest.toUsuario()

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

        val fakeUser = User(
            "id-random" + UUID.randomUUID().toString(),
            "Intellij-entes",
            "intellijentes@example.com",
            "123456",
            "04510"
        )

        logger.info("Try to make login with: $loginUserRequest")
        return if (fakeUser.hashPassword == loginUserRequest.hashPassword) {
            logger.info("Successful login")
            ResponseEntity.ok(Any())
        } else {
            logger.error("Login failed")
            ResponseEntity.status(401).build()
        }
    }

    /**
     * Endpoint encargado de cerrar la sesión de un usuario
     *
     * URL: http//localhost:8080/users/logout
     *
     * Método: POST
     *
     * @return ResponseEntity con información de logout HTTP 200 si fue un éxito
     */
    @PostMapping("/logout")
    fun logoutUser() : ResponseEntity<Any> {

        val fakeUser = User(
            "id-random" + UUID.randomUUID().toString(),
            "ColasAlegres",
            "colasalegres@example.com",
            "af2ak12",
            "90841"
        )

        val logoutResponse = LogoutUser(
            fakeUser.name,
            Date.from(Instant.now())
        )

        return ResponseEntity.ok(logoutResponse)
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