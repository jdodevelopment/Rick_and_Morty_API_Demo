package ar.com.jdodevelopment.rickandmorty.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CharactersResponse(
    val info: Info,
    val results: List<Character>
): Parcelable