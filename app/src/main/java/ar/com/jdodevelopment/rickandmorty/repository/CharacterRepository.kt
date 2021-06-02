package ar.com.jdodevelopment.rickandmorty.repository

import ar.com.jdodevelopment.rickandmorty.data.api.CharactersApi
import ar.com.jdodevelopment.rickandmorty.data.model.Character
import ar.com.jdodevelopment.rickandmorty.data.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class CharacterRepository @Inject constructor(
    private val api : CharactersApi
) {

    suspend fun getCharacter(id: Int): Resource<Character> {
        val response = try {
            api.getCharacter(id)
        } catch(e: Exception) {
            return Resource.Error("An unknown error occured.")
        }
        return Resource.Success(response)
    }

}
