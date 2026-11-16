
package com.example.habitatguardx.data.remote

import retrofit2.http.GET

interface HabitatGuardApi {

    @GET("posts")
    suspend fun obtenerDatosPrueba(): List<PostDto>
}

data class PostDto(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)
