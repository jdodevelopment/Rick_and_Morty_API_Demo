package ar.com.jdodevelopment.rickandmorty.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ar.com.jdodevelopment.rickandmorty.data.api.CharactersApi
import ar.com.jdodevelopment.rickandmorty.data.model.Character
import ar.com.jdodevelopment.rickandmorty.data.model.CharactersResponse
import ar.com.jdodevelopment.rickandmorty.data.util.RetrofitUtil
import retrofit2.Response
import java.io.IOException


class CharactersPagingSource(
    private val api: CharactersApi,
) : PagingSource<Int, Character>() {


    companion object {
        const val INITIAL_PAGE = 1
    }


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        try {
            val page = params.key ?: INITIAL_PAGE
            val response: Response<CharactersResponse> = api.getList(page)
            if (response.isSuccessful) {
                response.body()!!.let { data ->
                    val results = data.results
                    val prevKey = if (page > 1) page - 1 else null
                    val nextKey = if (results.isNotEmpty()) page + 1 else null
                    return LoadResult.Page(
                        data = results,
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                }
            }
            val message = RetrofitUtil.getErrorMessage(response)
            return LoadResult.Error(Exception(message))
        } catch (e: IOException) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition
    }
}
