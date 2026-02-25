package com.intellijentes.sys.colasAlegres.dto.request

/**
 * DTO mediante el cual recibimos de la petición los parámetros necesarios
 * para crear un animal en adopción.
 * */
data class CreateAnimalRequest (
    val name: String,
    val description: String,
    val typeAnimal: String,
    val animalBreed: String,
    val zipCodeFounded: Int
)