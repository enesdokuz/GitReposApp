package com.enesdokuz.gitrepoapp.repository.retrofit

import com.enesdokuz.gitrepoapp.model.Repo
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface GitAPI {

    @GET("users/{username}/repos")
    @Headers("Accept: application/vnd.github.v3+json")
    fun getUserRepo(
        @Path(
            value = "username",
            encoded = true
        ) username: String
    ): Observable<List<Repo>>
}