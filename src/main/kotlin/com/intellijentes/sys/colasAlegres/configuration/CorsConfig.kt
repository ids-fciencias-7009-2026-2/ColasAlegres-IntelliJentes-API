package com.intellijentes.sys.colasAlegres.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * Configuracion global de CORS de la aplicacion.
 *
 * Permite que el frontend pueda realizar peticiones HTTP al backend desde un
 * origen diferente. La falta de esta configuracion bloquearia todas las peticiones provenientes
 * del frontend.
 */
@Configuration
class CorsConfig : WebMvcConfigurer {

    /**
     * Define las reglas de CORS para todas las endpoints de la aplicacion.
     *
     * @param registry Registro de configuraciones CORS
     */
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
        .allowedOrigins("http://localhost:5173")
        .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
        .allowedHeaders("*")
    }
}