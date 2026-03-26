package com.intellijentes.sys.colasAlegres.controllers

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.server.ResponseStatusException

data class ApiErrorResponse(val status: Int, val error: String, val message: String)

@RestControllerAdvice
class ApiExceptionHandler {

        @ExceptionHandler(ResponseStatusException::class)
        fun handleResponseStatus(ex: ResponseStatusException): ResponseEntity<ApiErrorResponse> {
                val status = HttpStatus.valueOf(ex.statusCode.value())
                val body =
                        ApiErrorResponse(
                                status = status.value(),
                                error = status.reasonPhrase,
                                message = ex.reason ?: "La solicitud no pudo procesarse"
                        )
                return ResponseEntity.status(status).body(body)
        }

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

        @ExceptionHandler(HttpMessageNotReadableException::class)
        fun handleMalformedBody(
                ex: HttpMessageNotReadableException
        ): ResponseEntity<ApiErrorResponse> {
                val body =
                        ApiErrorResponse(
                                status = HttpStatus.BAD_REQUEST.value(),
                                error = HttpStatus.BAD_REQUEST.reasonPhrase,
                                message =
                                        "Body inválido: revisa la estructura JSON y los tipos de datos"
                        )
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body)
        }

        @ExceptionHandler(HttpMediaTypeNotSupportedException::class)
        fun handleUnsupportedMediaType(
                ex: HttpMediaTypeNotSupportedException
        ): ResponseEntity<ApiErrorResponse> {
                val body =
                        ApiErrorResponse(
                                status = HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(),
                                error = HttpStatus.UNSUPPORTED_MEDIA_TYPE.reasonPhrase,
                                message = "Content-Type no soportado. Usa application/json"
                        )
                return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(body)
        }
}
