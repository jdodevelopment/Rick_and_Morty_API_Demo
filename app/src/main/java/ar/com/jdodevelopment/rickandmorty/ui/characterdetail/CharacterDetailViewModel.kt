package ar.com.jdodevelopment.rickandmorty.ui.characterdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import ar.com.jdodevelopment.rickandmorty.data.api.CharactersApi
import ar.com.jdodevelopment.rickandmorty.data.consts.BundleConsts
import ar.com.jdodevelopment.rickandmorty.data.consts.CharacterConsts
import ar.com.jdodevelopment.rickandmorty.data.model.Character
import ar.com.jdodevelopment.rickandmorty.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
     savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _character = MutableLiveData<Character>(savedStateHandle.get(BundleConsts.CHARACTER))
    val character: LiveData<Character> get() = _character


}