package com.intellijentes.sys.colasAlegres.models.entities.domain

/**
 * Modelo de dominio que representa a un Animal en el sistema.
 * Este modelo contiene la estructura completa de la entidad,
 * a diferencia de los DTOs que solo transportan datos específicos.
 */

 data class Animal(
    val id: String,
    val name: String,
    val description: String,
    val typeAnimal: String,
    val animalBreed: String,
    val zipCodeFounded: Int
)