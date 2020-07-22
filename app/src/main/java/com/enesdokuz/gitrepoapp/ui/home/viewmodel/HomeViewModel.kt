package com.enesdokuz.gitrepoapp.ui.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.enesdokuz.gitrepoapp.model.Owner
import com.enesdokuz.gitrepoapp.model.Repo
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Headers
import com.github.kittinunf.fuel.gson.responseObject
import kotlin.random.Random

class HomeViewModel : ViewModel() {

    val repos = MutableLiveData<List<Repo>>()
    val isLoading = MutableLiveData<Boolean>()
    val hasError = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()

    fun getUserRepos(username: String?) {
        if (username.isNullOrEmpty()) {
            showError("Empty Query")
        } else {
            showLoading()
        }
        username?.let {
            //getUserReposFromGitHub(it)
            getDummy()
        }
    }

    private fun getUserReposFromGitHub(username: String) {

        Fuel.get("https://api.github.com/users/$username/repos")
            .header(Headers.ACCEPT, "application/vnd.github.v3+json")
            .responseObject<List<Repo>> { request, response, result ->
                println(request)
                println(response)
                println(result)
                isLoading.value = false
                repos.value = result.get()
                /*
                println("Status code: $response.statusCode")
                if (response.statusCode.toString().startsWith("2")){
                    //repos.value = response.data
                }else{
                    errorMessage.value = "Error from Fuel"
                    hasError.value = true
                }
                println(response.statusCode)
                */
            }


    }

    private fun getDummy() {
        showLoading()
        val owner = Owner(
            username = "enesdokuz",
            avatarUrl = "https://avatars0.githubusercontent.com/u/13020424?v=4"
        )
        val list: ArrayList<Repo> = arrayListOf()
        for (i in 0..Random.nextInt(10, 20)) {
            list.add(
                Repo(
                    id = Random.nextInt(900000).toString(),
                    name = "test Repo ${Random.nextInt(99)}",
                    openIssueCount = Random.nextLong(900),
                    starCount = Random.nextLong(900),
                    owner = owner,
                    isFavorite = Random.nextBoolean()
                )
            )
        }
        repos.value = list
        hideLoading()

    }

    private fun showError(errorMessage: String) {
        isLoading.value = false
        hasError.value = true
        this.errorMessage.value = errorMessage
    }

    private fun showLoading() {
        isLoading.value = true
        hasError.value = false
    }

    private fun hideLoading() {
        isLoading.value = false
        hasError.value = false
    }
}
