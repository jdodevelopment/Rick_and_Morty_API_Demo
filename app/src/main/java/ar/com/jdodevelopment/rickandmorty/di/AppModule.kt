package ar.com.jdodevelopment.rickandmorty.di

import android.app.Application
import ar.com.jdodevelopment.rickandmorty.data.api.CharactersApi
import ar.com.jdodevelopment.rickandmorty.network.InternetConnectionVerifierInterceptor
import ar.com.jdodevelopment.rickandmorty.repository.CharacterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "https://rickandmortyapi.com/api/"
    private const val TIMEOUT_SECONDS = 25L

    @Singleton
    @Provides
    fun providePokemonRepository(
        api: CharactersApi
    ) = CharacterRepository(api)

    @Singleton
    @Provides
    fun provideApi(
        okHttpClient: OkHttpClient,
    ): CharactersApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()
            .create(CharactersApi::class.java)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        connectVerifierInterceptor: InternetConnectionVerifierInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(connectVerifierInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    @Provides
    @Singleton
    fun provideInternetConnectionVerifierInterceptor(application: Application): InternetConnectionVerifierInterceptor {
        return InternetConnectionVerifierInterceptor(application)
    }
}