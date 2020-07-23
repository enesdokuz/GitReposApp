package com.enesdokuz.gitrepoapp.ui.home.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.enesdokuz.gitrepoapp.model.Owner
import com.enesdokuz.gitrepoapp.model.Repo
import com.enesdokuz.gitrepoapp.repository.GitService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlin.random.Random

class HomeViewModel : ViewModel() {

    val repos = MutableLiveData<List<Repo>>()
    val isLoading = MutableLiveData<Boolean>()
    val hasError = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    private val gitService = GitService()
    private val disposable = CompositeDisposable()

    fun getUserRepos(username: String?) {
        if (username.isNullOrEmpty()) {
            showError("Empty Query")
        } else {
            showLoading()
        }
        username?.let {
            getUserReposFromGitHub(it)
            //getDummy()
        }
    }

    @SuppressLint("CheckResult")
    private fun getUserReposFromGitHub(username: String) {
        disposable.add(
            gitService.getUserRepos(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    result?.let {
                        hideLoading()
                        repos.value = it
                    }
                }, { error ->
                    error?.let {
                        error.localizedMessage?.let {
                            showError(it)
                        }
                        println("getUserReposFromGitHub error. $error")
                    }
                })
        )

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

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}
