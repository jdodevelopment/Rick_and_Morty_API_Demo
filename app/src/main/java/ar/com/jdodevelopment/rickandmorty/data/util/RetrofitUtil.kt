package ar.com.jdodevelopment.rickandmorty.data.util

import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.Response
import java.io.IOException

object RetrofitUtil {


    fun getErrorMessage(response: Response<*>): String {
        val responseBody = response.errorBody() ?: return "No hay información disponible."
        return try {
            val gson = Gson()
            val json = gson.fromJson(responseBody.string(), JsonObject::class.java)
            json["error"].asString
        } catch (e: IOException) {
            e.printStackTrace()
            "No hay información disponible."
        }
    }

}