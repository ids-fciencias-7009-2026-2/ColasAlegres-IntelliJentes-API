package com.intellijentes.sys.colasAlegres.domain

import com.intellijentes.sys.colasAlegres.dto.request.CreateAnimalRequest
import java.util.UUID

/**
 * Extension Function que convierte un DTO de tipo CreateAnimalRequest
 * en un objeto de dominio Animal
 */

fun CreateAnimalRequest.toAnimal(): Animal {

    // Generamos un identificador único de forma simulada
    val id = "id-random-" + UUID.randomUUID().toString()

    // Creamos el objeto de dominio usando los datos del DTO
    return Animal(
        id = id,
        name = this.name,
        description = this.description,
        typeAnimal = this.typeAnimal,
        animalBreed = this.animalBreed,
        zipCodeFounded = this.zipCodeFounded
    )
}