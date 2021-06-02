package ar.com.jdodevelopment.rickandmorty.data.api

import ar.com.jdodevelopment.rickandmorty.data.model.Character
import ar.com.jdodevelopment.rickandmorty.data.model.CharactersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharactersApi {

    @GET("character")
    suspend fun getList(
        @Query("page") page: Int
    ): Response<CharactersResponse>

    @GET("character/{id}")
    suspend fun getCharacter(
        @Path("id") page: Int
    ): Character
}