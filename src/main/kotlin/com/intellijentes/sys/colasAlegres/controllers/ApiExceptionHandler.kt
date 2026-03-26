package com.intellijentes.sys.colasAlegres.controllers

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

data class ApiErrorResponse(val status: Int, val error: String, val message: String)

@RestControllerAdvice
class ApiExceptionHandler {

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleBadRequest(ex: IllegalArgumentException): ResponseEntity<ApiErrorResponse> {
        val body =
                ApiErrorResponse(
                        status = HttpStatus.BAD_REQUEST.value(),
                        error = HttpStatus.BAD_REQUEST.reasonPhrase,
                        message = ex.message ?: "Solicitud inválida"
                )
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body)
    }

    @ExceptionHandler(IllegalStateException::class)
    fun handleConflict(ex: IllegalStateException): ResponseEntity<ApiErrorResponse> {
        val body =
                ApiErrorResponse(
                        status = HttpStatus.CONFLICT.value(),
                        error = HttpStatus.CONFLICT.reasonPhrase,
                        message = ex.message ?: "Conflicto de datos"
                )
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body)
    }
}
