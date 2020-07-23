package com.enesdokuz.gitrepoapp.ui.home.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.enesdokuz.gitrepoapp.model.Owner
import com.enesdokuz.gitrepoapp.model.Repo
import com.enesdokuz.gitrepoapp.model.RepoDTO
import com.enesdokuz.gitrepoapp.repository.retrofit.GitService
import com.enesdokuz.gitrepoapp.repository.room.RepoDatabase
import com.enesdokuz.gitrepoapp.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import kotlin.random.Random

class HomeViewModel(application: Application) : BaseViewModel(application) {

    val repos = MutableLiveData<List<Repo>>()
    val isLoading = MutableLiveData<Boolean>()
    val hasError = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    private val gitService =
        GitService()
    private val disposable = CompositeDisposable()
    private val repoDao = RepoDatabase(context = getApplication()).repoDao()

    fun getUserRepos(username: String?) {
        if (username.isNullOrEmpty()) {
            showError("Empty Query")
        } else {
            getUserReposFromGitHub(username)
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
                        saveInSqLite(it)
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

    private fun saveInSqLite(list: List<Repo>) {
        launch {
            repoDao.deleteAllRepos()
        }
        launch {
            val reposDTO: ArrayList<RepoDTO> = arrayListOf()
            list.forEach { item ->
                reposDTO.add(
                    RepoDTO(
                        id = item.id,
                        name = item.name,
                        openIssueCount = item.openIssueCount,
                        starCount = item.starCount,
                        isFavorite = item.isFavorite,
                        ownerAvatarUrl = item.owner.avatarUrl,
                        ownerUsername = item.owner.username
                    )
                )
            }
            repoDao.insertAll(*reposDTO.toTypedArray())
        }
    }

    fun getReposFromSqLite() {
        showLoading()
        launch {
            val reposDTO: List<RepoDTO> = repoDao.getAllRepos()
            val reposTemp: ArrayList<Repo> = arrayListOf()
            reposDTO.forEach { item ->
                reposTemp.add(
                    Repo(
                        id = item.id,
                        name = item.name,
                        openIssueCount = item.openIssueCount,
                        starCount = item.starCount,
                        isFavorite = item.isFavorite,
                        owner = Owner(
                            username = item.ownerUsername,
                            avatarUrl = item.ownerAvatarUrl
                        )
                    )
                )
            }
            repos.value = reposTemp
            hideLoading()
        }
    }

    fun setFavoriteItem(isFavorite: Boolean, itemId: String) {
        launch {
            repoDao.updateRepo(isFavorite = isFavorite, id = itemId)
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

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}
