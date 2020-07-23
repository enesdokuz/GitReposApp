package com.enesdokuz.gitrepoapp.repository.retrofit

import com.enesdokuz.gitrepoapp.BuildConfig
import com.enesdokuz.gitrepoapp.model.Repo
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class GitService {

    private fun httpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(loggingInterceptor())
        }
        return builder.build()
    }

    private val api = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .client(httpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(GitAPI::class.java)

    private fun loggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    fun getUserRepos(username: String): Observable<List<Repo>> {
        return api.getUserRepo(username)
    }

}