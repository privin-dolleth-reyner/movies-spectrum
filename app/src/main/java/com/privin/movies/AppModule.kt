package com.privin.movies

import com.privin.movies.data.Repo
import com.privin.movies.data.RepoImpl
import com.privin.movies.data.remote.ApiClient
import com.privin.movies.data.remote.Server
import com.privin.movies.data.remote.ServerImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    companion object {

        private val authInterceptor = Interceptor{
            val req = it.request()
            val url = req.url
            val newUrl = url.newBuilder()
                .addQueryParameter("api_key", BuildConfig.API_KEY)
                .build()
            val newRequest = req.newBuilder()
                .url(newUrl)
                .build()
            it.proceed(newRequest)
        }

        @Provides
        @Singleton
        fun provideOkHttpClient(): OkHttpClient {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            return OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .addNetworkInterceptor(loggingInterceptor)
                .build()
        }

        @Provides
        @Singleton
        fun provideRetrofit(client: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
        }

        @Provides
        @Singleton
        fun provideApiClient(retrofit: Retrofit): ApiClient{
            return retrofit.create(ApiClient::class.java)
        }
    }


    @Binds
    abstract fun bindServer(serverImpl: ServerImpl): Server

    @Binds
    abstract fun bindRepo(repoImpl: RepoImpl): Repo
}