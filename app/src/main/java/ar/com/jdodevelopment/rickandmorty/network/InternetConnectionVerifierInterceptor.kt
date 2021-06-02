package ar.com.jdodevelopment.rickandmorty.network

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import ar.com.jdodevelopment.rickandmorty.R
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


class InternetConnectionVerifierInterceptor constructor(
    private val application: Application
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!hasInternetConnection()) {
            throw IOException(application.getString(R.string.no_internet_conection))
        }
        val request = chain.request()
        return chain.proceed(request)
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = application.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when(type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }

}