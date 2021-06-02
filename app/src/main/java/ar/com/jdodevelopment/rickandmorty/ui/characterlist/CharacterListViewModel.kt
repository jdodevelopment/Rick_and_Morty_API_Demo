package ar.com.jdodevelopment.rickandmorty.ui.characterlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import ar.com.jdodevelopment.rickandmorty.data.api.CharactersApi
import ar.com.jdodevelopment.rickandmorty.data.model.Character
import ar.com.jdodevelopment.rickandmorty.data.pagingsource.CharactersPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val api: CharactersApi
) : ViewModel() {

    private val pager: Pager<Int, Character>

    init {
        val pageSize = 20
        pager = Pager(
            PagingConfig(pageSize = pageSize, initialLoadSize = pageSize, enablePlaceholders = true),
            pagingSourceFactory = { CharactersPagingSource(api) }
        )
    }

    fun getPagingData(): LiveData<PagingData<Character>> {
        return pager.liveData
    }

}