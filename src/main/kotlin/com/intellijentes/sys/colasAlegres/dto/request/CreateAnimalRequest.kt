package com.intellijentes.sys.colasAlegres.dto.request

/**
 * DTO mediante el cual recbimos de la peticion los parametros necesarios
 * para crear un animal en adopcion.
 * */
data class CreateAnimalRequest (
    val name: String,
    val description: String,
    val typeAnimal: String,
    val animalBreed: String,
    val zipCodeFounded: Int
)